package KOWI2003.LaserMod.gui.widgets.properties;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;

public class ColorProperty extends DataProperty<float[]> {

	FloatProperty red;
	FloatProperty green;
	FloatProperty blue;
	
	public ColorProperty(int x, int y, int width, int height, String displayName, float[] value) {
		super(x, y, width, height * 4, displayName, value);
		value = Utils.parseColor(value);
		
		int indent = 5;
		
		red = new FloatProperty(x + indent, y + height, width - indent, height, "Red", value[0], 0, 1);
		green = new FloatProperty(x + indent, y + height*2, width - indent, height, "Green", value[1], 0, 1);
		blue = new FloatProperty(x + indent, y + height*3, width - indent, height, "Blue", value[2], 0, 1);
	}
	
	@Override
	@SuppressWarnings("resource")
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		drawString(matrix, Minecraft.getInstance().font, getDisplayName() + ": ", x + 2, y + Minecraft.getInstance().font.lineHeight/2, 0xffffff);
		red.render(matrix, mouseX, mouseY, partialTicks);
		green.render(matrix, mouseX, mouseY, partialTicks);
		blue.render(matrix, mouseX, mouseY, partialTicks);
		
		if(value[0] != red.value) {
			value[0] = red.value;
			setHasChanged();
		}
		if(value[1] != green.value) {
			value[1] = green.value;
			setHasChanged();
		}
		if(value[2] != blue.value) {
			value[2] = blue.value;
			setHasChanged();
		}
	}
	
	@Override
	public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_) {
		boolean check = red.mouseClicked(p_93641_, p_93642_, p_93643_);
		check = green.mouseClicked(p_93641_, p_93642_, p_93643_) || check;
		check = blue.mouseClicked(p_93641_, p_93642_, p_93643_) | check;
		return check;
	}
	
	@Override
	public void mouseMoved(double p_94758_, double p_94759_) {
		red.mouseMoved(p_94758_, p_94759_);
		green.mouseMoved(p_94758_, p_94759_);
		blue.mouseMoved(p_94758_, p_94759_);
	}
	
	@Override
	public boolean mouseReleased(double p_93684_, double p_93685_, int p_93686_) {
		boolean check = red.mouseReleased(p_93684_, p_93685_, p_93686_);
		check = green.mouseReleased(p_93684_, p_93685_, p_93686_) || check;
		check = blue.mouseReleased(p_93684_, p_93685_, p_93686_) || check;
		return check;
	}
	
	@Override
	public boolean mouseDragged(double p_93645_, double p_93646_, int p_93647_, double p_93648_, double p_93649_) {
		boolean check = red.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_);
		check = green.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_) || check;
		check = blue.mouseDragged(p_93645_, p_93646_, p_93647_, p_93648_, p_93649_) || check;
		return check;
	}

}
