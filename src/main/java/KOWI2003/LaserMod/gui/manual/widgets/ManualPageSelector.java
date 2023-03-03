package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.GuiManual;
import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.widget.PageSelector;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;

public class ManualPageSelector extends ManualComponent<PageSelector> {

	public ManualPageSelector(PageSelector data) {
		super(data);
		int w = (data.item != null ? 20 : 0) + Minecraft.getInstance().font.width(data.Text) + 5;
		if(width < w)
			width = w;
		height = 20;
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		float[] color = Utils.parseColor(data.TextColor);
		if(data.TextHoverColor != null && isMouseOver(mouseX, mouseY))
			color = Utils.parseColor(data.TextHoverColor);
		
		if(data.item != null) {
			if(!data.centered)
				RenderUtils.Gui.renderItem(data.item, super.getX(), getY(), 1);
			else
				RenderUtils.Gui.renderItem(data.item, super.getX() - 10 - Minecraft.getInstance().font.width(data.Text)/2, getY(), 1);
		}
		
		if(data.centered) {
			drawCenteredString(stack, Minecraft.getInstance().font, data.Text, super.getX() + (data.item != null ? 10 : 0), getY() + 10 - Minecraft.getInstance().font.lineHeight/2, Utils.getHexIntFromRGB(color));
		}else
			drawString(stack, Minecraft.getInstance().font, data.Text, super.getX() + (data.item != null ? 20 : 0), getY() + 10 - Minecraft.getInstance().font.lineHeight/2, Utils.getHexIntFromRGB(color));
	}
	
	@Override
	public int getX() {
		return data.centered ? (super.getX() - super.getWidth()/2) : super.getX();
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		if(isMouseOver(mouseX, mouseY)) {
			if(ManualHandler.hasGuiId(data.GuiId))
				Minecraft.getInstance().setScreen(new GuiManual(ManualHandler.getGui(data.GuiId)));
			else
				System.err.println("Unable to find gui with id [" + data.GuiId + "]");
		}
		super.onClick(mouseX, mouseY);
	}
}
