package KOWI2003.LaserMod.tileentities;

import KOWI2003.LaserMod.init.ModTileTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaserController extends SyncableBlockEntity implements BlockEntityTicker<TileEntityLaserController> {

	BlockPos controlPos = null;
	
	public TileEntityLaserController(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER_CONTROLLER, pos, state);
	}
	
	public TileEntityLaserController(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
	}
	
	public boolean isConnected() {
		return controlPos != null;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		CompoundTag controlPosNBT = new CompoundTag();
		if(controlPos != null) {
			controlPosNBT.putInt("x", controlPos.getX());
			controlPosNBT.putInt("y", controlPos.getY());
			controlPosNBT.putInt("z", controlPos.getZ());
		}
		nbt.put("controlPosition", controlPosNBT);
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		if(nbt.contains("controlPosition")) {
			CompoundTag controlPosNBT = nbt.getCompound("controlPosition");
			if(controlPosNBT.contains("x") && controlPosNBT.contains("y") && controlPosNBT.contains("z")) {
				controlPos = new BlockPos(controlPosNBT.getInt("x"), controlPosNBT.getInt("y"), controlPosNBT.getInt("z"));
			}else
				controlPos = null;
		}
		super.load(nbt);
	}
	
	@Override
	public void tick(Level level, BlockPos pos, BlockState state, TileEntityLaserController te) {
		sync();
		if(!(getControlTileEntity() instanceof TileEntityLaser || getControlTileEntity() instanceof TileEntityLaserProjector)) {
			controlPos = null;
		}
	}
	
	public BlockEntity getControlTileEntity() {
		if(controlPos == null)
			return null;
		return level.getBlockEntity(controlPos);
	}
	
	public void link(BlockPos pos) {
		if(isConnected())
			Disconnect();
		this.controlPos = pos;
		BlockEntity tileentity = getControlTileEntity();
		if(tileentity instanceof TileEntityLaser) {
			((TileEntityLaser)tileentity).isRemoteControlled = true;
			((TileEntityLaser)tileentity).sync();
		}if(tileentity instanceof TileEntityLaserProjector) {
			((TileEntityLaserProjector)tileentity).isRemoteControlled = true;
			((TileEntityLaserProjector)tileentity).sync();
		}sync();
	}
	
	public void Disconnect() {
		BlockEntity tileentity = getControlTileEntity();
		if(tileentity instanceof TileEntityLaser) {
			((TileEntityLaser)tileentity).isRemoteControlled = false;
			((TileEntityLaser)tileentity).sync();
		}if(tileentity instanceof TileEntityLaserProjector) {
			((TileEntityLaserProjector)tileentity).isRemoteControlled = false;
			((TileEntityLaserProjector)tileentity).sync();
		}controlPos = null;
		sync();
	}
}
