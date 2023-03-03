package KOWI2003.LaserMod.tileentities.projector.data;

import KOWI2003.LaserMod.gui.widgets.DataProperties.RangeProperty;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;

public class ProjectorWidgetData {

	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float width = 1;
	public float height = 1;
	public float depth = 1;
	
	@RangeProperty(min = 0, max = 360)
	public float rotation = 0;
	
	public float scale = 1;
	
	@RangeProperty(min = 0, max = 1)
	public float alpha = 1;
	
	public final ProjectorWidgetTypes type;

	private int internalID = -1;
	
	public ProjectorWidgetData(ProjectorWidgetTypes type) {
		this.type = type;
		this.internalID = hashCode();
	}

	public ProjectorWidgetData(float x, float y, float z, float width, float height, float depth,
			ProjectorWidgetTypes type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.type = type;
		this.internalID = hashCode();
	}

	public final CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("Type", type.ordinal());
		tag.putFloat("X", x);
		tag.putFloat("Y", y);
		tag.putFloat("Z", z);
		tag.putFloat("Width", width);
		tag.putFloat("Height", height);
		tag.putFloat("Depth", depth);
		tag.putFloat("Rotation", rotation);
		tag.putFloat("Scale", scale);
		tag.putFloat("Alpha", alpha);
		tag.putInt("internalId", internalID);
		return writeToNBT(tag);
	}
	
	protected CompoundTag writeToNBT(CompoundTag tag) {
		return tag;
	}
	
	public static final ProjectorWidgetData deserializeFromNBT(CompoundTag nbt) {
		if(!nbt.contains("Type"))
			return null;
		ProjectorWidgetTypes type = ProjectorWidgetTypes.values()[nbt.getInt("Type")];
		ProjectorWidgetData data = type.getData();
		data.readNBT(nbt);
		return data;
	}
	
	protected void readNBT(CompoundTag tag) {
		x = Utils.conditionalGetFloat("X", tag, 0);
		y = Utils.conditionalGetFloat("Y", tag, 0);
		z = Utils.conditionalGetFloat("Z", tag, 0);
		width = Utils.conditionalGetFloat("Width", tag, 0);
		height = Utils.conditionalGetFloat("Height", tag, 0);
		depth = Utils.conditionalGetFloat("Depth", tag, 0);
		rotation = Utils.conditionalGetFloat("Rotation", tag, 0);
		alpha = Utils.conditionalGetFloat("Alpha", tag, 1);
		internalID = Utils.conditionalGetInt("internalId", tag, hashCode());
		scale = Utils.conditionalGetFloat("Scale", tag, 1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProjectorWidgetData) {
			return ((ProjectorWidgetData) obj).internalID == internalID;
		}
		return super.equals(obj);
	}
}
