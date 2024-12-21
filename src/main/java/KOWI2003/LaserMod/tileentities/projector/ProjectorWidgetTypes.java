package KOWI2003.LaserMod.tileentities.projector;

import java.lang.reflect.InvocationTargetException;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorPlayerData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextBoxData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;

public enum ProjectorWidgetTypes {
	None(ProjectorWidgetData.class), 
	Text(ProjectorTextData.class), 
	TextBox(ProjectorTextBoxData.class), 
	Item(ProjectorItemData.class),
	Player(ProjectorPlayerData.class),
	Shape(ProjectorShapeData.class);
	
	public Class<? extends ProjectorWidgetData> clazz;

	private ProjectorWidgetTypes(Class<? extends ProjectorWidgetData> clazz) {
		this.clazz = clazz;
	}
	
	public ProjectorWidgetData getData() {
		try {
			return clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return new ProjectorWidgetData(None);
		}
	}
}
