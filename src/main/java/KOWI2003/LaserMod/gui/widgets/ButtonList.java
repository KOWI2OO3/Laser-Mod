package KOWI2003.LaserMod.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;

public class ButtonList extends AbstractWidget {

	List<Button> buttons = new ArrayList<>();
	
	float offset = 0;
	float scrollSpeed = 1;
	
	boolean isSet = false;
	
	public ButtonList(int x, int y, int width, int heigth,
			Component title) {
		super(x, y, width, heigth, title);
		offset = 0;
	}
	
	public ButtonList(int x, int y, int width, int heigth) {
		this(x, y, width, heigth, MutableComponent.create(new LiteralContents("Null")));
	}
	
	public void resetScrollSpeed() {
		scrollSpeed = 1;
	}
	
	public void setScrollSpeed(float speed) {
		scrollSpeed = speed;
	}
	
	/**
	 * wether to allow multiple instances of the same object in the list
	 */
	public void allowMultipleInstances(boolean value) {
		isSet = value;
	}

	public void clearList() {
		buttons.clear();
	}
	
	public boolean removeButton(Button button) {
		if(buttons.contains(button))
			return buttons.remove(button);
		return false;
	}
	
	public void setList(List<Button> buttons) {
		int height = 0;
		for (Button button : buttons) {
			button.y += height;
			height += button.getHeight();
		}
		this.buttons = buttons;
	}
	
	public void setListClean(List<Button> buttons) {
		int height = this.y;
		for (Button button : buttons) {
			button.setWidth(width);
			button.x = this.x;	
			button.y = height;
			height += button.getHeight();
		}
		this.buttons = buttons;
	}
	
	private boolean addButtonInternal(Button button) {
		if(this.buttons.contains(button) && isSet)
			return false;
		return this.buttons.add(button);
	}
	
	public boolean addButton(Button button) {
		if(buttons.size() > 0) {
			Button lastButton = buttons.get(buttons.size()-1);
			button.y = lastButton.y + lastButton.getHeight();
		}else
			button.y = this.y;
		button.setWidth(width);
		button.x = this.x;
		return addButtonInternal(button);
	}
	
	@Override
//	TODO use stencils of some kind of window, to make the scroll possible if the list is to long, and possibly a scrollbar
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		mouseY -= offset;

		{
			Window scr = Minecraft.getInstance().getWindow();
			int[] fbo = RenderUtils.setupRenderToTexture(scr.getWidth(), scr.getHeight());
			GL30.glClearColor(0.3f, 0.3f, 0.3f, 1);
			GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT); // we're not using the stencil buffer now
			
			matrix.pushPose();
			GL11.glPushMatrix();
			GL11.glTranslatef(-this.x, -this.y, 0);
			for (Button button : buttons) {
				button.render(matrix, mouseX, mouseY, partialTicks);
			}
			matrix.popPose();
			GL11.glPopMatrix();
			
			RenderUtils.stopRenderToTexture();
			
			RenderUtils.bindTexture(fbo[1]);
			float f = (float)scr.getWidth()/(float)Minecraft.getInstance().screen.width;
			float w = (float)width*f/(float)scr.getWidth();
			float h = (float)height*f/(float)scr.getHeight();
			RenderUtils.Gui.drawQuad(new PoseStack(), x, y, width, height, 0, (offset*f/scr.getHeight()), w, h);
			Minecraft.getInstance().textureManager.bindForSetup(WIDGETS_LOCATION);
			RenderUtils.destoryFBO(fbo);
		}
	}
	
	public int getTotalHeight() {
		int height = 0;
		for (Button button : buttons) {
			height += button.getHeight();
		}
		return height;
	}
	
	public int getLowestValue() {
		return getTotalHeight() - this.height;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaScroll) {
		deltaScroll *= scrollSpeed;
		if(isMouseOver(mouseX, mouseY)) {
			if(getTotalHeight() < this.height) {
				offset = 0;
				return false;
			}
			int topLimit = 0;
			if(offset >= topLimit && deltaScroll > 0) {
				offset = topLimit;
				return false;
			}
			if(offset <= -getLowestValue() && deltaScroll < 0) {
				offset = -getLowestValue();
				return false;
			}
			offset += deltaScroll;
			if(offset >= topLimit)
				offset = topLimit;
			return true;
		}
		return false;
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		mouseY -= offset;
		for (Button button : buttons) {
			if(button.isMouseOver(mouseX, mouseY)) {
				button.onClick(mouseX, mouseY);
				break;
			}
		}
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {}
}
