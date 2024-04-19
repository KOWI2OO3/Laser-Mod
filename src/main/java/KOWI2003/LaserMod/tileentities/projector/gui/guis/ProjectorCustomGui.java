package KOWI2003.LaserMod.tileentities.projector.gui.guis;

import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.tileentities.projector.gui.ProjectorGui;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorWidget;

public class ProjectorCustomGui extends ProjectorGui<TileEntityLaserProjector> {
	
	public ProjectorCustomGui(TileEntityLaserProjector te) {
		super(te);
		init();
	}
	
	@Override
	public void init() {
		super.init();
		
		for (ProjectorWidgetData data : getTileentity().getContext().getWidgets()) {
			ProjectorWidget widget = getWidget(data);
//			System.out.println(widget);
			if(widget != null)
				widgets.add(widget);
		}
	}

}
