package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class BlockInfuser extends BlockHorizontal {

	public final VoxelShape SHAPE;
	
	public BlockInfuser(Material materialIn) {
		super(materialIn);
		SHAPE = this.generateShape();
	}
	
	public BlockInfuser(Properties properties) {
		super(properties);
		SHAPE = this.generateShape();
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityInfuser(pos, state);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult raytraceResult) {
		if(!world.isClientSide) {
			BlockEntity te = world.getBlockEntity(pos);
			if(te instanceof TileEntityInfuser) {
				NetworkHooks.openScreen((ServerPlayer)player, (TileEntityInfuser) te, pos);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityInfuser) {
			TileEntityInfuser te = ((TileEntityInfuser)tileentity);
			for (int i = 0; i < te.handler.getSlots(); i++) {
				ItemStack stack = te.handler.getStackInSlot(i);
				if(!stack.isEmpty())
					popResource(world, pos, stack);
			}
		}
		super.playerWillDestroy(world, pos, state, player);
	}
	
	private VoxelShape generateShape()
	{
	    List<VoxelShape> shapes = new ArrayList<>();
	    shapes.add(Shapes.box(0.125, 0, 0.125, 0.875, 0.019, 0.875)); 	 // CUBE
	    shapes.add(Shapes.box(0.062, 0.019, 0.062, 0.938, 0.225, 0.938)); // CUBE
	    shapes.add(Shapes.box(0.656, 0.181, 0.156, 0.906, 0.525, 0.406)); // CUBE

	    VoxelShape result = Shapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
	    }
	    return result.optimize();
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return getShape(state, world, pos, context);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
			CollisionContext context) {
		return Utils.rotateVoxelShape(SHAPE, state.getValue(FACING).getCounterClockWise());
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos,
			CollisionContext context) {
		return getShape(state, reader, pos, context);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state,
	@Nonnull BlockEntityType<T> type) {
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) -> ((BlockEntityTicker<BlockEntity>)blockEntity).tick(level, pos, state, blockEntity);
	}
}
