package KOWI2003.LaserMod.tileentities.projector.data;

import KOWI2003.LaserMod.gui.widgets.DataProperties.SerializeProperty;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ProjectorItemData extends ProjectorWidgetData {

	@SerializeProperty
	public ItemStack item = ItemStack.EMPTY;
	@SerializeProperty
	public boolean onSurface = false;
	
	public ProjectorItemData() {
		super(ProjectorWidgetTypes.Item);
	}
	
	public ProjectorItemData(float x, float y, float z, float size, ItemStack stack) {
		super(x, y, z, size, size, size, ProjectorWidgetTypes.Item);
		this.item = stack;
	}
	
	@Override
	protected CompoundTag writeToNBT(CompoundTag tag) {
		Utils.putItemStack(tag, "Item", item);
		tag.putBoolean("onSurface", onSurface);
		return super.writeToNBT(tag);
	}
	
	@Override
	protected void readNBT(CompoundTag tag) {
		super.readNBT(tag);
		item = Utils.conditionalGetItemStack("Item", tag);
		onSurface = Utils.conditionalGetBoolean("onSurface", tag, false);
	}
	
	public void setStack(ItemStack stack) {
		this.item = stack;
	}

}
