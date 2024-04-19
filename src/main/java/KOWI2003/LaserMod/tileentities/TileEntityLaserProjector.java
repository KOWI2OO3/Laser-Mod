package KOWI2003.LaserMod.tileentities;

import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.tileentities.projector.ProjectorGuiContext;
import KOWI2003.LaserMod.tileentities.projector.ProjectorTemplates;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLaserProjector extends SyncableBlockEntity implements BlockEntityTicker<TileEntityLaserProjector> {

	public ItemStackHandler handler;
	
	public boolean isActive;
	public boolean isRemoteControlled;
	
	//Other Variables
	public ProjectorGuiContext context;
	public ProjectorTemplates template;
	
	public TileEntityLaserProjector(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER_PROJECTOR, pos, state);
		handler = new ItemStackHandler(3);
		setTemplate(ProjectorTemplates.Text);
	}
	
	
	@Override
	public void tick(Level level, BlockPos pos, BlockState state, TileEntityLaserProjector tile) {
		if(isActive) {
			sync();
		}
		
		if(!isRemoteControlled) {
			if(isActive != Utils.isBlockPowered(getBlockPos(), getLevel())) {
				isActive = Utils.isBlockPowered(getBlockPos(), getLevel());
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inv", handler.serializeNBT());
		nbt.putBoolean("isRemoteControlled", isRemoteControlled);
		nbt.putBoolean("isActive", isActive);
		nbt.put("Context", context.serializeNBT());
		nbt.putInt("Template", template.ordinal());
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		if(nbt.contains("inv"))
			handler.deserializeNBT(nbt.getCompound("inv"));
		if(nbt.contains("isRemoteControlled"))
			isRemoteControlled = nbt.getBoolean("isRemoteControlled");
		if(nbt.contains("isActive"))
			isActive = nbt.getBoolean("isActive");
		context.deserializeNBT(Utils.conditionalGetCompound("Context", nbt));
		int tempIndex = Utils.conditionalGetInt("Template", nbt, 0);
		if(tempIndex >= 0 && tempIndex < ProjectorTemplates.values().length)
		template = ProjectorTemplates.values()[tempIndex];
		super.load(nbt);
	}
	
	public void setTemplate(ProjectorTemplates template) {
		context = template.getContext();
		this.template = template;
		sync();
	}
	
	public void setActive(boolean value) {
		isActive = value;
		sync();
	}
	
	public ProjectorGuiContext getContext() {
		return context;
	}

}
