package KOWI2003.LaserMod.blocks;

import java.util.List;

import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class BlockLaser extends BlockRotatable {
	
	protected static final AABB NORTH_AABB = new AABB(0.3D, 0.3D, 0.85D, 0.7D, 0.7D, 1.0D);
	protected static final AABB EAST_AABB = new AABB(0.0D, 0.3D, 0.30D, 0.15D, 0.7D, 0.7D);
	protected static final AABB SOUTH_AABB = new AABB(0.3D, 0.3D, 0.0D, 0.7D, 0.7D, 0.15D);
	protected static final AABB WEST_AABB = new AABB(0.85D, 0.3D, 0.30D, 1.0D, 0.7D, 0.7D);

	protected static final AABB UP_AABB = new AABB(0.3D, 0.0D, 0.3D, 0.7D, 0.15D, 0.7D);
	protected static final AABB DOWN_AABB = new AABB(0.3D, 0.85D, 0.3D, 0.7D, 1.0D, 0.7D);
	
	public int tickCounter = 0;
	
	public BlockLaser(Material materialIn) {
		super(materialIn);
		this.setModelPlacement(true);
	}
	
	public BlockLaser(Properties properties) {
		super(properties);
		this.setModelPlacement(true);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult raytraceResult) {
		if(!world.isClientSide) {
			BlockEntity te = world.getBlockEntity(pos);
			if(te instanceof TileEntityLaser) {
				NetworkHooks.openGui((ServerPlayer)player, (TileEntityLaser)te, pos);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.SUCCESS;
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
		switch((Direction)state.getValue(FACING)) {
			case NORTH:
				return Utils.getShapeFromAABB(NORTH_AABB);
			case EAST:
				return Utils.getShapeFromAABB(EAST_AABB);
			case SOUTH:
				return Utils.getShapeFromAABB(SOUTH_AABB);
			case WEST:
				return Utils.getShapeFromAABB(WEST_AABB);
			case UP:
				return Utils.getShapeFromAABB(UP_AABB);
			case DOWN:
				return Utils.getShapeFromAABB(DOWN_AABB);
			default:
				return Shapes.create(UP_AABB);
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		switch((Direction)state.getValue(FACING)) {
			case NORTH:
				return Utils.getShapeFromAABB(NORTH_AABB);
			case EAST:
				return Utils.getShapeFromAABB(EAST_AABB);
			case SOUTH:
				return Utils.getShapeFromAABB(SOUTH_AABB);
			case WEST:
				return Utils.getShapeFromAABB(WEST_AABB);
			case UP:
				return Utils.getShapeFromAABB(UP_AABB);
			case DOWN:
				return Utils.getShapeFromAABB(DOWN_AABB);
			default:
				return Shapes.create(UP_AABB);
		}
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityLaser) {
			TileEntityLaser te = ((TileEntityLaser)tileentity);
			te.handleTurnOffForInteractable();
			List<ItemUpgradeBase> items = te.getProperties().getUpgrades();
			if(items.size() > 0)
				for (ItemUpgradeBase item : items) {
					if(item != null)
						popResource(world, pos, new ItemStack(item));
				}
		}
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityLaser(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) -> ((BlockEntityTicker<BlockEntity>)blockEntity).tick(level, pos, state, blockEntity);
	}

}
