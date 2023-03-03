package KOWI2003.LaserMod.tileentities.projector;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorPlayerData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum ProjectorTemplates {
	Text(new ProjectorGuiContext(new ProjectorTextData(0, 4, 0, "Unknown"))),
	Item(new ProjectorGuiContext(new ProjectorItemData(0, 8.3f, 0, 1.0f, new ItemStack(Items.STICK)))),
	Player(new ProjectorGuiContext(new ProjectorPlayerData(0, -10, 0, 1.0f, ""))),
	Custom(new ProjectorGuiContext());
	
	ProjectorGuiContext context;
	
	private ProjectorTemplates(ProjectorGuiContext context) {
		this.context = context;
	}
	
	public ProjectorGuiContext getContext() {
		return context.copy();
	}
}
