package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

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

public class BlockLaserProjector extends BlockHorizontal {

	public BlockLaserProjector(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world,
			net.minecraft.core.BlockPos pos, Player player, InteractionHand hand, BlockHitResult raytraceResult) {
		
		BlockEntity te = world.getBlockEntity(pos);
		if(te instanceof TileEntityLaserProjector) {
			if(world.isClientSide)
				Minecraft.getInstance().setScreen(new GuiLaserProjector((TileEntityLaserProjector)te));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityLaserProjector(pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return getShape(state, world, pos, context);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return getShape(state, world, pos, context);
	}
	
	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
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
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		List<AABB> aabbs = new ArrayList<AABB>();
		float height = 0.532f;
		aabbs.add(new AABB(0.25, 0, 0, 0.938, height, 1));
		aabbs.add(new AABB(0, 0, 0.062, 1, height, 0.938));
		aabbs.add(new AABB(0.062, 0, 0.938, 0.5, height, 1));
		aabbs.add(new AABB(0.062, 0, 0, 0.312, height, 0.062));
		return Utils.getShapeFromAABB(aabbs);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) -> ((BlockEntityTicker<BlockEntity>)blockEntity).tick(level, pos, state, blockEntity);
	}
	
}
