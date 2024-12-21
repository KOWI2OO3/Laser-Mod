package KOWI2003.LaserMod.tileentities;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.utils.TileEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaserCatcher extends SyncableBlockEntity implements ILaserInteractable, IColorable {

	public boolean isHit = false;
	ILaserAccess te;

	BlockPos laserPosition;
	
	public TileEntityLaserCatcher(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER_CATCHER, pos, state);
	}

	@Override
	protected void saveAdditional(@Nonnull CompoundTag nbt) {
		nbt.putBoolean("isHit", isHit);
		var pos = te != null ? te.getPos() : laserPosition;
		if(pos != null)
			nbt.putIntArray("laserPos", new int[] {pos.getX(), pos.getY(), pos.getZ()});
			
		super.saveAdditional(nbt);
	}
	
	@Override
	@SuppressWarnings("null")
	public void load(@Nonnull CompoundTag nbt) {
		isHit = nbt.getBoolean("isHit");
		if(nbt.contains("laserPos")) {
			int[] pos = nbt.getIntArray("laserPos");
			laserPosition = new BlockPos(pos[0], pos[1], pos[2]);

			if(getLevel() != null) {
				var tile = getLevel().getBlockEntity(laserPosition);
				if(tile instanceof ILaserAccess)
					te = (ILaserAccess)tile;
			}
		}
		super.load(nbt);
	}
	
	@Override
	@SuppressWarnings("null")
	public void interactWithLaser(ILaserAccess te) {
		if(getBlockState().getValue(BlockRotatable.FACING) == te.getDirection()) {
			isHit = true;
			this.te = te.getLaser();
			getLevel().blockUpdated(getBlockPos(), getBlockState().getBlock());
			sync();
			TileEntityUtils.syncColorToClient(this);
		}
	}

	@Override
	@SuppressWarnings("null")
	public void onLaserInteractStop(ILaserAccess te) {
		isHit = false;
		this.te = null;
		getLevel().blockUpdated(getBlockPos(), getBlockState().getBlock());
		sync();
		TileEntityUtils.syncColorToClient(this);
	}

	@Override
	public float[] getColor(int index) {
		if(isHit) {
			if(te != null)
				return new float[] {te.getLaser().red, te.getLaser().green, te.getLaser().blue};
		}
		return new float[] {.4f, .4f, .4f};
	}
	
	@Override
	public void setColor(int index, float[] color) {}
}
