package KOWI2003.LaserMod.gui.widgets;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.GuiLaserProjector;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ModSlider extends AbstractWidget {

	int sliderWidth = 7;
	
	float sliderX = 0;
	
	float min;
	float max;
	
	public ModSlider(int x, int y, int width, int height, Component title, float min, float max) {
		super(x, y, width, height, title);
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTick) {
		RenderSystem._setShaderTexture(0, GuiLaserProjector.WIDGETS);
		blit(matrix, x, y + 6, 1, 209, this.width / 2, 6);
		blit(matrix, x + this.width / 2, y+6, 1 + 105 - this.width / 2, 209, this.width / 2, 6);
		blit(matrix, (int) (sliderX + x), y, 249, 172, 7, 20);
	}
	
	public float getValue() {
		float minX = 0;
		float maxX = width - sliderWidth/2f;
		float diff = (sliderX) / (minX + maxX);
		return diff * (max-min) + min;
	}

	boolean selected = false;
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		sliderX = (float) (mouseX - sliderWidth/2f);
		sliderX = Math.min(width - sliderWidth/2f, Math.max(0, sliderX));
		
		if(mouseX >= sliderX+x && mouseX <= sliderWidth+sliderX+x && mouseY >= y && mouseY <= y+height && button == 0) {
			selected = true;
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if(button == 0 && selected) {
			sliderX = (float) (mouseX - x);
		}
		return true;
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		if( selected) {
			sliderX = (float) (mouseX - sliderWidth/2f);
			sliderX = Math.min(width - sliderWidth/2f, Math.max(0, sliderX));
		}
		super.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if(selected && button == 0)
			selected = false;
		return true;
	}
	
	
	@Override
	public void updateNarration(@Nonnull NarrationElementOutput p_169152_) {}

}
