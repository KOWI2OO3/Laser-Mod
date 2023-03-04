package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.manual.data.widget.ImageComponent;
import KOWI2003.LaserMod.utils.RenderUtils;

public class ManualImage extends ManualComponent<ImageComponent> {

	public ManualImage(ImageComponent data) {
		super(data);
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		
		RenderUtils.bindTexture(data.ImageLocation);
		RenderSystem.enableTexture();
		
		if(data.borderSize < width && data.borderSize < height) {
			float halfBorder = data.borderSize/2f;
			RenderUtils.Gui.drawQuad(stack, getX()+halfBorder, getY()+halfBorder, width-halfBorder, height-halfBorder, 0, 0, 1, -1);
		}else {
			RenderUtils.Gui.drawQuad(stack, getX(), getY(), width, height, 0, 0, 1, -1);
		}
		
	}

}
