package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.utils.RenderUtils;

public class ManualItem extends ManualComponent<ItemComponent> {

	public ManualItem(ItemComponent data) {
		super(data);
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		
		RenderUtils.Gui.renderItem(data.stack, data.X + super.getX(), data.Y + super.getY(), data.size);
		
	}

}
