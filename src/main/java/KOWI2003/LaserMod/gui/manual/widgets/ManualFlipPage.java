package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.gui.GuiManual;
import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.widget.FlipPageComponent;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ManualFlipPage extends ManualComponent<FlipPageComponent> {

	static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/mod_widgets.png");
	
	public ManualFlipPage(FlipPageComponent data) {
		super(data);
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		int uvX = 0;
		int uvY = 0;
		
		switch (data.buttonType) {
			case NextArrow:
				uvX = 21;
				uvY = 147;
				break;
			case PrevArrow:
				uvX = 1;
				uvY = 147;
				break;
			case CompleteReturn: 
				uvX = 1;
				uvY = 107;
				break;
			default:
				break;
		}
		
		if(isMouseOver(mouseX, mouseY))
			uvY += 20;
		
		RenderUtils.bindTexture(TEXTURE);
		RenderSystem.enableTexture();
		RenderUtils.Gui.drawQuad(stack, getX(), getY(), getWidth(), getHeight(), uvX/256f, uvY/256f, 18/256f, -18/256f);
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
