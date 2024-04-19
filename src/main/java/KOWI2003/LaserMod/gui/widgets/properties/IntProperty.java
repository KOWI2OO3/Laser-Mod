package KOWI2003.LaserMod.gui.widgets.properties;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class IntProperty extends DataProperty<Integer> {

	EditBox intText;
	Button Raise;
	Button Lower;
	
	boolean hasRange = false;
	ForgeSlider rangeSlider;
	
	public IntProperty(int x, int y, int width, int height, String name, int value, float min, float max) {
		this(x, y, width, height, name, value);
		hasRange = min < max;
		
		rangeSlider = new ForgeSlider(x + 33, y, 55, height, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), min, max, value, 1f, 0, false);
//		rangeSlider = new Slider(x, y, MutableComponent.create(new LiteralContents(""), min, max, value, (button) -> {}, (button) -> {});
	}
	
	public IntProperty(int x, int y, int width, int height, String name, int value) {
		super(x, y, width, 21, name, value);
		int localX = 20;
		intText = new EditBox(Minecraft.getInstance().font, x+10 + localX, y + 2, 50, 16, MutableComponent.create(new LiteralContents("intText")));
		intText.setValue(String.format("%.02f", value));
		
		Raise = new Button(x + localX + 60, y, 10, 20, MutableComponent.create(new LiteralContents(">")), (button) -> {
			this.value += (Utils.isCtrlDown() ? 1 : (Utils.isShiftDown() ? 10 : 100));
			intText.setValue(String.format("%.02f", this.value));
			setHasChanged();
		});
		
		Lower = new Button(x + localX, y, 10, 20, MutableComponent.create(new LiteralContents("<")), (button) -> {
			this.value -= (Utils.isCtrlDown() ? 1 : (Utils.isShiftDown() ? 10 : 100));
			intText.setValue(String.format("%.02f", this.value));
			setHasChanged();
		});
		
	}
	
	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		if(hasRange) {
			rangeSlider.render(matrix, mouseX, mouseY, partialTicks);
			if(rangeSlider.getValue() != value) {
				value = (int)rangeSlider.getValue();
				setHasChanged();
			}
		}else {
			intText.render(matrix, mouseX, mouseY, partialTicks);
			Raise.render(matrix, mouseX, mouseY, partialTicks);
			Lower.render(matrix, mouseX, mouseY, partialTicks);
		}
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 35f, 10, 0xffffff);
//		drawString(matrix, Minecraft.getInstance().font, getDisplayName() + ": ", x + 2, y + Minecraft.getInstance().font.lineHeight/2, 0xffffff);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(hasRange)
			return false;
		boolean check = false;
		if(hasRange) {
			setHasChanged();
			check = rangeSlider.mouseClicked(mouseX, mouseY, button);
		}else {
			check = intText.mouseClicked(mouseX, mouseY, button) || check;
			check = Raise.mouseClicked(mouseX, mouseY, button) || check;
			check = Lower.mouseClicked(mouseX, mouseY, button) || check;
		}
		return check;
	}
	
	@Override
	public boolean charTyped(char p_94732_, int p_94733_) {
		if(hasRange)
			return false;
		boolean check = intText.charTyped(p_94732_, p_94733_);
		try {
			int temp = Integer.parseInt(intText.getValue().replace(",", "."));
			value = temp;
			setHasChanged();
		}catch(NumberFormatException | NullPointerException e) {}
		return check;
	}
	
	@Override
	public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
		if(hasRange)
			return false;
		boolean check = intText.keyPressed(p_94745_, p_94746_, p_94747_);
		try {
			int temp = Integer.parseInt(intText.getValue().replace(",", "."));
			value = temp;
			setHasChanged();
		}catch(NumberFormatException | NullPointerException e) {}
		return check;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if(hasRange) {
			setHasChanged();
			return rangeSlider.mouseReleased(mouseX, mouseY, button);
		}
		return false;
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		if(hasRange) {
			setHasChanged();
			rangeSlider.mouseMoved(mouseX, mouseY);
		}
	}
	
	@Override
	public boolean mouseDragged(double p_93645_, double p_93646_, int p_93647_, double p_93648_, double p_93649_) {
		if(hasRange) {
			setHasChanged();
			rangeSlider.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
		}
		return super.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
	}	

}
