package KOWI2003.LaserMod.gui.widgets.properties;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.TextComponent;

public class BoolProperty extends DataProperty<Boolean> {

	Checkbox box;
	
	public BoolProperty(int x, int y, int width, int height, String name, boolean value) {
		super(x, y, width, height, name, value);
		box = new Checkbox(x + 65, y + 3, 10, 10, new TextComponent(""), value);
	}

	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		renderCheckBox(matrix, box);
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 70f, 10, 0xffffff);
//		drawString(matrix, Minecraft.getInstance().font, getDisplayName() + ": ", x + 2, y + Minecraft.getInstance().font.lineHeight/2, 0xffffff);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		
		if(super.mouseClicked(mouseX, mouseY, button)) {
			if(box.isMouseOver(mouseX, mouseY)) {
				box.mouseClicked(mouseX, mouseY, button);
				value = box.selected();
				setHasChanged();
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public void renderCheckBox(PoseStack matrix, Checkbox checkbox) {
		int posx = checkbox.x;
		int posy = checkbox.y;
		int width = checkbox.getWidth();
		int height = checkbox.getHeight();
		checkbox.x = 0;
		checkbox.y = 0;
		
		matrix.pushPose();
		matrix.translate(posx, posy + 2.5f, 0);
		float scale = 0.5f;
		matrix.scale(scale, scale, scale);
		checkbox.setWidth(20);
		checkbox.setHeight(20);
		checkbox.renderButton(matrix, 0, 0, 0);
		matrix.popPose();
		
		checkbox.x = posx;
		checkbox.y = posy;
		checkbox.setWidth(width);
		checkbox.setHeight(height);
		checkbox.visible = true;
	}
	
}
