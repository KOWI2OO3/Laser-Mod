package KOWI2003.LaserMod.tileentities.projector;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class ProjectorGuiContext implements INBTSerializable<CompoundTag> {

	List<ProjectorWidgetData> widgets;
	
	public ProjectorGuiContext(ProjectorWidgetData... widgets) {
		if(widgets.length > 0) {
			this.widgets = new ArrayList<>(widgets.length);
			for (ProjectorWidgetData data : widgets)
				this.widgets.add(data);
		}else
			this.widgets = new ArrayList<>();
	}

	public List<ProjectorWidgetData> getWidgets() {
		return widgets;
	}
	
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		ListTag widgetsList = new ListTag();
		for (ProjectorWidgetData widget : widgets) {
			widgetsList.add(widget.serializeNBT());
		}
		tag.put("Elements", widgetsList);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		ListTag widgetsList = Utils.conditionalGetListTag("Elements", tag, Tag.TAG_COMPOUND);
		widgets.clear();
		for (Tag elementTag : widgetsList) {
			if(elementTag instanceof CompoundTag) {
				CompoundTag widgetTag = (CompoundTag)elementTag;
				ProjectorWidgetData data = ProjectorWidgetData.deserializeFromNBT(widgetTag);
				if(data != null && data.type != ProjectorWidgetTypes.NONE)
					widgets.add(data);
			}
		}
	}
	
	public ProjectorGuiContext copy() {
		ProjectorGuiContext context = new ProjectorGuiContext(widgets.toArray(new ProjectorWidgetData[] {}));
		return context;
	}
	
	
	
}
