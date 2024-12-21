package KOWI2003.LaserMod.gui.widgets.properties;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class FloatProperty extends DataProperty<Float> {

	EditBox intText;
	Button Raise;
	Button Lower;
	
	boolean hasRange = false;
	ForgeSlider rangeSlider;
	
	public FloatProperty(int x, int y, int width, int height, String name, float value, float min, float max) {
		this(x, y, width, height, name, value);
		hasRange = min < max;
		
		rangeSlider = new ForgeSlider(x + 35, y, width - 37, height, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 
			min, max, value, .001f, 0, false);
	}
	
	@SuppressWarnings("resource")
	public FloatProperty(int x, int y, int width, int height, String name, float value) {
		super(x, y, width, 21, name, value);
		int localX = (name.length() <= 3 ? 0 : Minecraft.getInstance().font.width(getDisplayName())/2);
		intText = new EditBox(Minecraft.getInstance().font, x+30 + localX, y + 2, 50 - localX, 16, MutableComponent.create(new LiteralContents("intText")));
		intText.setValue(String.format("%.02f", value).replace(".", ","));
		
		Raise = new Button(x + localX + 80 - localX, y, 10, 20, MutableComponent.create(new LiteralContents(">")), (button) -> {
			this.value += (Utils.isShiftDown() ? (Utils.isCtrlDown() ? 0.1f : 10f) : (Utils.isCtrlDown() ? 100f : 1f));
			intText.setValue(String.format("%.02f", this.value).replace(".", ","));
			setHasChanged();
		});
		
		Lower = new Button(x + localX + 20, y, 10, 20, MutableComponent.create(new LiteralContents("<")), (button) -> {
			this.value -= (Utils.isShiftDown() ? (Utils.isCtrlDown() ? 0.1f : 10f) : (Utils.isCtrlDown() ? 100f : 1f));
			intText.setValue(String.format("%.02f", this.value).replace(".", ","));
			setHasChanged();
		});
		
	}
	
	@Override
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		if(hasRange) {
			rangeSlider.render(matrix, mouseX, mouseY, partialTicks);
			if(rangeSlider.getValue() != value) {
				value = (float) rangeSlider.getValue();
				setHasChanged();
			}
		}else {
			intText.render(matrix, mouseX, mouseY, partialTicks);
			Raise.render(matrix, mouseX, mouseY, partialTicks);
			Lower.render(matrix, mouseX, mouseY, partialTicks);
		}
		RenderUtils.Gui.drawStringWithinLine(matrix, getDisplayName() + ": ", x + 2, y + 10, 35f, 0xffffff);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean check = false;
		if(isMouseOver(mouseX, mouseY)) {
			if(hasRange) {
				setHasChanged();
				if(rangeSlider.isMouseOver(mouseX, mouseY))
					check = rangeSlider.mouseClicked(mouseX, mouseY, button);
			}else {
				if(intText.isMouseOver(mouseX, mouseY))
					check = intText.mouseClicked(mouseX, mouseY, button) || check;
				if(Raise.isMouseOver(mouseX, mouseY))
					check = Raise.mouseClicked(mouseX, mouseY, button) || check;
				if(Lower.isMouseOver(mouseX, mouseY))
					check = Lower.mouseClicked(mouseX, mouseY, button) || check;
			}
		}
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
	public boolean charTyped(char p_94732_, int p_94733_) {
		if(hasRange)
			return false;
		boolean check = intText.charTyped(p_94732_, p_94733_);
		try {
			float temp = Float.parseFloat(intText.getValue().replace(",", "."));
			value = temp;
			setHasChanged();
		}catch(NumberFormatException | NullPointerException e) {}
		return check;
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
		boolean i = false;
		if(hasRange) {
			setHasChanged();
			i = rangeSlider.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
		}
		return i || super.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
	}
	
	@Override
	public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
		if(hasRange)
			return false;
		boolean check = intText.keyPressed(p_94745_, p_94746_, p_94747_);
		try {
			float temp = Float.parseFloat(intText.getValue().replace(",", "."));
			value = temp;
			setHasChanged();
		}catch(NumberFormatException | NullPointerException e) {}
		return check;
	}

	
	
}
