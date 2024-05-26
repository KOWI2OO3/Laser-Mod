package KOWI2003.LaserMod.tileentities.projector.data;

import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;

public class ProjectorTextBoxData extends ProjectorWidgetData {

	public String text = "";
	public float[] textColor = new float[] {1, 1, 1, 1};
	public boolean isCentered = false;

	public ProjectorTextBoxData() {
		this(0, 0, 0, 1, "Unknown", new float[] {1, 1, 1, 1}, true);
		// super(ProjectorWidgetTypes.TextBox);
	}

	public ProjectorTextBoxData(float x, float y, float z, float size,
			String text, float[] textColor, boolean isCentered) {
		super(x, y, z, size, size, size, ProjectorWidgetTypes.TextBox);
		this.text = text;
		this.isCentered = isCentered;
		this.textColor = Utils.parseColor(textColor);
	}
	
	@Override
	protected CompoundTag writeToNBT(CompoundTag tag) {
		tag.putString("Text", text);
		Utils.putColor(tag, "TextColor", textColor);
		tag.putBoolean("IsCentered", isCentered);
		return super.writeToNBT(tag);
	}
	
	@Override
	protected void readNBT(CompoundTag tag) {
		super.readNBT(tag);
		text = Utils.conditionalGetString("Text", tag, "Unknown");
		textColor = Utils.conditionalGetColor("TextColor", tag);
		isCentered = Utils.conditionalGetBoolean("IsCentered", tag, false);
	}
	
}
