package KOWI2003.LaserMod.blocks;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaserController;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.TileEntityRemoteCC;
import KOWI2003.LaserMod.utils.ModChecker;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockRemote extends BlockHorizontal {

	public final VoxelShape SHAPE;
	
	AABB MAIN = new AABB(0.175, 0.569, -0.031, 0.35, 0.75, 0.031);
	AABB MODE = new AABB(0.738, 0.569, -0.031, 0.806, 0.75, 0.031);
	AABB RED = new AABB(0.544, 0.444, -0.031, 0.812, 0.513, 0.031);
	AABB GREEN = new AABB(0.544, 0.363, -0.031, 0.812, 0.431, 0.031);
	AABB BLUE = new AABB(0.544, 0.281, -0.031, 0.812, 0.35, 0.031);
	
	public BlockRemote(Properties properties) {
		super(properties);
		SHAPE = this.generateShape();
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		Vec3 loc = hit.getLocation().subtract(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
		TileEntityLaserController te = (TileEntityLaserController) world.getBlockEntity(pos);
		
		if(!te.isConnected())
			return super.use(state, world, pos, player, hand, hit);
			
		BlockEntity tile = te.getControlTileEntity();
		
		AABB temp = Utils.rotateAABB(MAIN, state.getValue(FACING).getClockWise());
		if(temp.contains(loc)) {
			if(tile instanceof TileEntityLaser) {
				TileEntityLaser laser = ((TileEntityLaser)tile);
				laser.setActive(!laser.active);
			}else if(tile instanceof TileEntityLaserProjector) {
				TileEntityLaserProjector laser = ((TileEntityLaserProjector)tile);
				laser.setActive(!laser.isActive);
			}
			return InteractionResult.SUCCESS;
		}
		temp = Utils.rotateAABB(MODE, state.getValue(FACING).getClockWise());
		if(temp.contains(loc)) {
			if(tile instanceof TileEntityLaser) {
				TileEntityLaser laser = ((TileEntityLaser)tile);
				if(laser.getProperties().hasUpgarde("mode")) {
					int index = laser.mode.ordinal(); 
					if(player.isShiftKeyDown())
						index -= 1;
					else
						index += 1;
					if(index >= KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE.values().length) {
						index = 0;
					}
					if(index < 0) {
						index = KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE.values().length-1;
					}
					laser.mode = KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE.values()[index];
				}
			}else if(tile instanceof TileEntityLaserProjector) {
//				TileEntityLaserProjector laser = ((TileEntityLaserProjector)tile);
			}
			return InteractionResult.SUCCESS;
		}
		temp = Utils.rotateAABB(RED, state.getValue(FACING).getClockWise());
		if(temp.contains(loc)) {
			float value = 0;
			if(state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH)
				value = (float) ((loc.x - (state.getValue(FACING) == Direction.NORTH ? temp.maxX : temp.minX)) / 
						(temp.maxX - temp.minX));
			else
				value = (float) ((loc.z - (state.getValue(FACING) == Direction.EAST ? temp.maxZ : temp.minZ)) / 
						(temp.maxZ - temp.minZ));
			value = Math.abs(value);
			if(tile instanceof TileEntityLaser) {
				TileEntityLaser laser = ((TileEntityLaser)tile);
				laser.setColor(value, laser.green, laser.blue);
				return InteractionResult.SUCCESS;
			}
			
		}
		temp = Utils.rotateAABB(GREEN, state.getValue(FACING).getClockWise());
		if(temp.contains(loc)) {
			float value = 0;
			if(state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH)
				value = (float) ((loc.x - (state.getValue(FACING) == Direction.NORTH ? temp.maxX : temp.minX)) / 
						(temp.maxX - temp.minX));
			else
				value = (float) ((loc.z - (state.getValue(FACING) == Direction.EAST ? temp.maxZ : temp.minZ)) / 
						(temp.maxZ - temp.minZ));
			value = Math.abs(value);
			if(tile instanceof TileEntityLaser) {
				TileEntityLaser laser = ((TileEntityLaser)tile);
				laser.setColor(laser.red, value, laser.blue);
				return InteractionResult.SUCCESS;
			}
		}
		temp = Utils.rotateAABB(BLUE, state.getValue(FACING).getClockWise());
		if(temp.contains(loc)) {
			float value = 0;
			if(state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH)
				value = (float) ((loc.x - (state.getValue(FACING) == Direction.NORTH ? temp.maxX : temp.minX)) / 
						(temp.maxX - temp.minX));
			else
				value = (float) ((loc.z - (state.getValue(FACING) == Direction.EAST ? temp.maxZ : temp.minZ)) / 
						(temp.maxZ - temp.minZ));
			value = Math.abs(value);
			if(tile instanceof TileEntityLaser) {
				TileEntityLaser laser = ((TileEntityLaser)tile);
				laser.setColor(laser.red, laser.green, value);
				return InteractionResult.SUCCESS;
			}
		}
		
		return super.use(state, world, pos, player, hand, hit);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		ModChecker.check();
		return ModChecker.isComputercraftLoaded ? new TileEntityRemoteCC(pos, state) : new TileEntityLaserController(pos, state);
	}
	
	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityRemoteCC) {
			TileEntityRemoteCC te = ((TileEntityRemoteCC)tileentity);
			if(te.isConnected())
				te.Disconnect();
		}
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof TileEntityRemoteCC) {
			TileEntityRemoteCC te = ((TileEntityRemoteCC)tileentity);
			if(te.isConnected())
				te.Disconnect();
		}
		super.destroy(world, pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}
	
	private VoxelShape generateShape()
	{
	    List<VoxelShape> shapes = new ArrayList<>();
	    shapes.add(Shapes.box(0, 0, 0, 0.125, 0.031, 0.125)); // CUBE
	    shapes.add(Shapes.box(0.875, 0, 0, 1, 0.031, 0.125)); // CUBE
	    shapes.add(Shapes.box(0.875, 0, 0.875, 1, 0.031, 1)); // CUBE
	    shapes.add(Shapes.box(0, 0, 0.875, 0.125, 0.031, 1)); // CUBE
	    shapes.add(Shapes.box(0.031, 0.031, 0.906, 0.094, 0.125, 0.969)); // CUBE
	    shapes.add(Shapes.box(0.031, 0.031, 0.031, 0.094, 0.125, 0.094)); // CUBE
	    shapes.add(Shapes.box(0.906, 0.031, 0.031, 0.969, 0.125, 0.094)); // CUBE
	    shapes.add(Shapes.box(0.906, 0.031, 0.906, 0.969, 0.125, 0.969)); // CUBE
	    shapes.add(Shapes.box(0.013, 0.125, 0.013, 0.987, 0.925, 0.987)); // CUBE
	    shapes.add(Shapes.box(0, 0.112, 0.938, 0.062, 0.938, 1)); // CUBE
	    shapes.add(Shapes.box(0.938, 0.112, 0.938, 1, 0.938, 1)); // CUBE
	    shapes.add(Shapes.box(0, 0.112, 0, 0.062, 0.938, 0.062)); // CUBE
	    shapes.add(Shapes.box(0.938, 0.112, 0, 1, 0.938, 0.062)); // CUBE
	    shapes.add(Shapes.box(0.056, 0.112, 0.938, 0.944, 0.175, 1)); // CUBE
	    shapes.add(Shapes.box(0.056, 0.112, 0, 0.944, 0.175, 0.062)); // CUBE
	    shapes.add(Shapes.box(0.056, 0.875, 0, 0.944, 0.938, 0.062)); // CUBE
	    shapes.add(Shapes.box(0.056, 0.875, 0.938, 0.944, 0.938, 1)); // CUBE
	    shapes.add(Shapes.box(0, 0.875, 0.062, 0.062, 0.938, 0.938)); // CUBE
	    shapes.add(Shapes.box(0.938, 0.875, 0.062, 1, 0.938, 0.938)); // CUBE
	    shapes.add(Shapes.box(0.938, 0.112, 0.062, 1, 0.175, 0.938)); // CUBE
	    shapes.add(Shapes.box(0, 0.112, 0.062, 0.062, 0.175, 0.938)); // CUBE

	    VoxelShape result = Shapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = Shapes.join(result, shape, BooleanOp.OR);
	    }
	    return result.optimize();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
	    return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
