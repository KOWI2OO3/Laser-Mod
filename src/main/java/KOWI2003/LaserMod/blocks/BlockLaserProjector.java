package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.gui.GuiLaserProjector;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockLaserProjector extends BlockHorizontal {

	public BlockLaserProjector(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(@Nonnull BlockState state, @Nonnull Level world,
			@Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult raytraceResult) {
		
		BlockEntity te = world.getBlockEntity(pos);
		if(te instanceof TileEntityLaserProjector) {
			if(world.isClientSide)
				handleClient(te);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.SUCCESS;
	}
	
	@OnlyIn(Dist.CLIENT)
	private void handleClient(BlockEntity te) {
		Minecraft.getInstance().setScreen(new GuiLaserProjector((TileEntityLaserProjector)te));
	}
	
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new TileEntityLaserProjector(pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getVisualShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return getShape(state, world, pos, context);
	}
	
	@Override
	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return getShape(state, world, pos, context);
	}
	
	@Override
	public void playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityLaserProjector) {
			TileEntityLaserProjector te = ((TileEntityLaserProjector)tileentity);
			ItemStack stack = te.handler.getStackInSlot(0);
			if(!stack.isEmpty())
				popResource(world, pos, stack);
		}
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		List<AABB> aabbs = new ArrayList<AABB>();
		float height = 0.532f;
		aabbs.add(new AABB(0.25, 0, 0, 0.938, height, 1));
		aabbs.add(new AABB(0, 0, 0.062, 1, height, 0.938));
		aabbs.add(new AABB(0.062, 0, 0.938, 0.5, height, 1));
		aabbs.add(new AABB(0.062, 0, 0, 0.312, height, 0.062));
		return Utils.getShapeFromAABB(aabbs);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state,
			@Nonnull BlockEntityType<T> type) {
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) -> ((BlockEntityTicker<BlockEntity>)blockEntity).tick(level, pos, state, blockEntity);
	}
	
}
