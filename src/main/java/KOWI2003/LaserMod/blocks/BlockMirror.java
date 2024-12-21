package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.tileentities.TileEntityMirror;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockMirror extends BlockHorizontal{

	public final VoxelShape V_SHAPE;
	public final VoxelShape C_SHAPE;
	
	public BlockMirror(Properties properties) {
		super(properties);
		V_SHAPE = this.generateVShape();
		C_SHAPE = this.generateCShape();
	}

	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new TileEntityMirror(pos, state);
	}

	@Override
	@SuppressWarnings("null")
	public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
		return context.getPlayer().isShiftKeyDown() ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite().getClockWise()) : super.getStateForPlacement(context);
	}
	
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState p_49232_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public void playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityMirror) {
			TileEntityMirror te = ((TileEntityMirror)tileentity);
			te.handleTurnOffForInteractable();
		}
		super.playerWillDestroy(world, pos, state, player);
	}

	private VoxelShape generateCShape()
	{
	    List<VoxelShape> shapes = new ArrayList<>();
	    shapes.add(Shapes.box(0.25, 0, 0.25, 0.375, 0.312, 0.375)); // STAND
	    shapes.add(Shapes.box(0.266, 0.312, 0.266, 0.359, 0.356, 0.359)); // STAND
	    shapes.add(Shapes.box(0.244, 0.356, 0.244, 0.381, 0.5, 0.381)); // STAND
	    shapes.add(Shapes.box(0.331, 0.375, 0.344, 0.438, 0.481, 0.444)); // STAND
	    shapes.add(Shapes.box(0, 0, 0.938, 0.062, 1, 1)); // HIT
	    shapes.add(Shapes.box(0.062, 0, 0.875, 0.125, 1, 0.938)); // HIT
	    shapes.add(Shapes.box(0.125, 0, 0.812, 0.188, 1, 0.875)); // HIT
	    shapes.add(Shapes.box(0.188, 0, 0.75, 0.25, 1, 0.812)); // HIT
	    shapes.add(Shapes.box(0.25, 0, 0.688, 0.312, 1, 0.75)); // HIT
	    shapes.add(Shapes.box(0.312, 0, 0.625, 0.375, 1, 0.688)); // HIT
	    shapes.add(Shapes.box(0.375, 0, 0.438, 0.438, 1, 0.625)); // HIT
	    shapes.add(Shapes.box(0.375, 0, 0.375, 0.625, 1, 0.438)); // HIT
	    shapes.add(Shapes.box(0.625, 0, 0.312, 0.688, 1, 0.375)); // HIT
	    shapes.add(Shapes.box(0.688, 0, 0.25, 0.75, 1, 0.312)); // HIT
	    shapes.add(Shapes.box(0.75, 0, 0.188, 0.812, 1, 0.25)); // HIT
	    shapes.add(Shapes.box(0.812, 0, 0.125, 0.875, 1, 0.188)); // HIT
	    shapes.add(Shapes.box(0.875, 0, 0.062, 0.938, 1, 0.125)); // HIT
	    shapes.add(Shapes.box(0.938, 0, 0, 1, 1, 0.062)); // HIT
	    
	    VoxelShape result = Shapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = Shapes.join(result, shape, BooleanOp.OR);
	    }
	    return result.optimize();
	}
	
	private VoxelShape generateVShape()
	{
	    List<VoxelShape> shapes = new ArrayList<>();
	    shapes.add(Shapes.box(0.25, 0, 0.25, 0.375, 0.312, 0.375)); // STAND
	    shapes.add(Shapes.box(0.266, 0.312, 0.266, 0.359, 0.356, 0.359)); // STAND
	    shapes.add(Shapes.box(0.244, 0.356, 0.244, 0.381, 0.5, 0.381)); // STAND
	    shapes.add(Shapes.box(0.331, 0.375, 0.344, 0.438, 0.481, 0.444)); // STAND
	    shapes.add(Shapes.box(0.4, 0.375, 0.406, 0.5, 0.481, 0.506)); // STAND
	    shapes.add(Shapes.box(0, 0, 0.938, 0.062, 1, 1)); // HIT
	    shapes.add(Shapes.box(0.062, 0, 0.875, 0.125, 1, 0.938)); // HIT
	    shapes.add(Shapes.box(0.125, 0, 0.812, 0.188, 1, 0.875)); // HIT
	    shapes.add(Shapes.box(0.188, 0, 0.75, 0.25, 1, 0.812)); // HIT
	    shapes.add(Shapes.box(0.25, 0, 0.688, 0.312, 1, 0.75)); // HIT
	    shapes.add(Shapes.box(0.312, 0, 0.625, 0.375, 1, 0.688)); // HIT
	    shapes.add(Shapes.box(0.375, 0, 0.562, 0.438, 1, 0.625)); // HIT
	    shapes.add(Shapes.box(0.438, 0, 0.5, 0.5, 1, 0.562)); // HIT
	    shapes.add(Shapes.box(0.5, 0, 0.438, 0.562, 1, 0.5)); // HIT
	    shapes.add(Shapes.box(0.562, 0, 0.375, 0.625, 1, 0.438)); // HIT
	    shapes.add(Shapes.box(0.625, 0, 0.312, 0.688, 1, 0.375)); // HIT
	    shapes.add(Shapes.box(0.688, 0, 0.25, 0.75, 1, 0.312)); // HIT
	    shapes.add(Shapes.box(0.75, 0, 0.188, 0.812, 1, 0.25)); // HIT
	    shapes.add(Shapes.box(0.812, 0, 0.125, 0.875, 1, 0.188)); // HIT
	    shapes.add(Shapes.box(0.875, 0, 0.062, 0.938, 1, 0.125)); // HIT
	    shapes.add(Shapes.box(0.938, 0, 0, 1, 1, 0.062)); // HIT

	    VoxelShape result = Shapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = Shapes.join(result, shape, BooleanOp.OR);
	    }
	    return result.optimize();
	}
	
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		VoxelShape temp = V_SHAPE;
		temp = Utils.rotateVoxelShape(temp, state.getValue(FACING).getCounterClockWise());
	    return temp;
	}
	
	@Override
	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		VoxelShape temp = C_SHAPE;
		temp = Utils.rotateVoxelShape(temp, state.getValue(FACING).getCounterClockWise());
	    return temp;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state,
		@Nonnull BlockEntityType<T> type) {
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) -> ((BlockEntityTicker<BlockEntity>)blockEntity).tick(level, pos, state, blockEntity);
	}
	
}
