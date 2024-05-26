package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class BlockModificationStation extends BlockHorizontal {
	
	public BlockModificationStation(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult raytraceResult) {
		if(!world.isClientSide) {
			BlockEntity te = world.getBlockEntity(pos);
			if(te instanceof TileEntityModStation) {
				NetworkHooks.openGui((ServerPlayer)player, (TileEntityModStation)te, pos);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityModStation(pos, state);
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
		if(tileentity instanceof TileEntityModStation) {
			TileEntityModStation te = ((TileEntityModStation)tileentity);
			for (int i = 0; i < te.handler.getSlots(); i++) {
				ItemStack stack = te.handler.getStackInSlot(i);
				if(!stack.isEmpty())
					popResource(world, pos, stack);
			}
		}
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		List<AABB> aabbs = new ArrayList<AABB>();
		aabbs.add(new AABB(0.25, 0, 0, 0.938, 0.125, 1));
		aabbs.add(new AABB(0, 0, 0.062, 1, 0.125, 0.938));
		aabbs.add(new AABB(0.281, 0.125, 0.219, 0.719, 0.188, 0.781));
		aabbs.add(new AABB(0.219, 0.125, 0.281, 0.781, 0.188, 0.719));
		aabbs.add(new AABB(0.062, 0, 0.938, 0.5, 0.125, 1));
		aabbs.add(new AABB(0.062, 0, 0, 0.312, 0.125, 0.062));
		return Utils.getShapeFromAABB(aabbs);
	}
}
