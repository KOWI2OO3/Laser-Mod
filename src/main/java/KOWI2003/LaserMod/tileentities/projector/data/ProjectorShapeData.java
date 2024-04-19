package KOWI2003.LaserMod.tileentities.projector.data;

import KOWI2003.LaserMod.gui.widgets.DataProperties.SerializeProperty;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;

public class ProjectorShapeData extends ProjectorWidgetData {

	@SerializeProperty
	public float[] color = new float[] {1, 0, 0};
	
	@SerializeProperty
	public ShapeType type = ShapeType.Cube;
	
	public ProjectorShapeData() {
		super(ProjectorWidgetTypes.Shape);
	}
	
	public ProjectorShapeData(float x, float y, float z, float width, float height, float depth) {
		super(x, y, z, width, height, depth, ProjectorWidgetTypes.Shape);
	}
	
	@Override
	protected CompoundTag writeToNBT(CompoundTag tag) {
		Utils.putColor(tag, "Color", color);
		tag.putInt("ShapeType", type.ordinal());
		return super.writeToNBT(tag);
	}
	
	@Override
	protected void readNBT(CompoundTag tag) {
		super.readNBT(tag);
		color = Utils.conditionalGetColor("Color", tag);
		int ordinal = Utils.conditionalGetInt("ShapeType", tag, 0);
		if(ordinal >= 0 && ordinal < ShapeType.values().length)
			type = ShapeType.values()[ordinal];
	}

	public static enum ShapeType {
		Cube, 
//		Sphere,
		Pyramide;
	}
	
}
