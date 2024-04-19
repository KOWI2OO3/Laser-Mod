package KOWI2003.LaserMod.tileentities.projector.data;

import KOWI2003.LaserMod.gui.widgets.DataProperties.SerializeProperty;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;

public class ProjectorTextData extends ProjectorWidgetData {

	@SerializeProperty
//	@PropertyRange(0, 1f)
	public String text = "";
	@SerializeProperty
	public boolean Centered = true;
	
//	@SerializeProperty
//	public float[] textColor = new float[] {1, 1, 1};
	
	public ProjectorTextData() {
		super(ProjectorWidgetTypes.Text);
	}

	public ProjectorTextData(float x, float y, float z, String text) {
		super(x, y, z, 1, 1, 1, ProjectorWidgetTypes.Text);
		this.text = text;
	}
	
	@Override
	protected CompoundTag writeToNBT(CompoundTag tag) {
		tag.putString("Text", text);
		tag.putBoolean("IsCentered", Centered);
		return super.writeToNBT(tag);
	}
	
	@Override
	protected void readNBT(CompoundTag tag) {
		super.readNBT(tag);
		text = Utils.conditionalGetString("Text", tag, "Unknown");
		Centered = Utils.conditionalGetBoolean("IsCentered", tag, true);
	}
}
