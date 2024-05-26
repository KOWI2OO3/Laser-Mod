package KOWI2003.LaserMod.gui.widgets.properties;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;

public class StringProperty extends DataProperty<String> {

	EditBox text;
	
	public StringProperty(int x, int y, int width, int height, String name, String value) {
		super(x, y, width, 40, name, value);
		text = new EditBox(Minecraft.getInstance().font, 
//				x + Minecraft.getInstance().font.width(name + ": ") + 3
				x + 5
				, y - 1
				+18
				, 70, 20, new TextComponent(""));
		text.setValue(value);
		text.setEditable(true);
	}

	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		text.render(matrix, mouseX, mouseY, partialTicks);
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 80f, 10, 0xffffff);
//		drawString(matrix, Minecraft.getInstance().font, getDisplayName() + ": ", x + 2, y + Minecraft.getInstance().font.lineHeight/2, 0xffffff);
	}
	
	@Override
	public boolean changeFocus(boolean value) {
		super.setFocused(value);
		text.setFocus(value);
		return value;
	}
	
	@Override
	public boolean charTyped(char character, int p_94733_) {
		boolean check = text.charTyped(character, p_94733_);
		value = text.getValue();
		setHasChanged();
		return check;
	}
	
	@Override
	public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
		boolean check = text.keyPressed(p_94745_, p_94746_, p_94747_);
		value = text.getValue();
		setHasChanged();
		return check;
	}
	
	@Override
	public boolean keyReleased(int p_94750_, int p_94751_, int p_94752_) {
		boolean check = text.keyReleased(p_94750_, p_94751_, p_94752_);
		return check;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(super.isMouseOver(mouseX, mouseY)) {
			if(text.isMouseOver(mouseX, mouseY))
				return text.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return super.isMouseOver(mouseX, mouseY);
	}

	
	
}
