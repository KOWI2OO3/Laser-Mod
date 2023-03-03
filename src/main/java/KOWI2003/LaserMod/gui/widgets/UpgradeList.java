package KOWI2003.LaserMod.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ScreenUtils;

public class UpgradeList extends Button {

	List<Button> buttons = new ArrayList<Button>();
	protected ResourceLocation TEXTURE = WIDGETS_LOCATION;
	
	public UpgradeList(int x, int y, int width, int height,
			Component title, OnPress pressable, String... strings) {
		super(x, y, width, height, title, pressable);
		for (int i = 0; i < strings.length; i++) {
			buttons.add(new Button(x, y + i * height, width, height, MutableComponent.create(new LiteralContents(strings[i])), pressable));
		}
	}
	
	public UpgradeList(int x, int y, int width, int height,
			Component title, OnPress pressable, ResourceLocation Texture, String... strings) {
		this(x, y, width, height, title, pressable, strings);
		this.TEXTURE = Texture;
	}
	
	public int size() {
		return buttons.size();
	}
	
	public void replaceList(String... names) {
		buttons.clear();
		for (int i = 0; i < names.length; i++) {
			buttons.add(new Button(x, y + i * height, width, height, MutableComponent.create(new LiteralContents(names[i])), onPress));
		}
		if(buttons.size() > 3)
			cropList();
	}
	
	public void cropList() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setHeight((int)(height/(((float)buttons.size()-1f)/2.5f)));
			buttons.get(i).x = x;
			buttons.get(i).y = y + i * buttons.get(i).getHeight();
		}
	}
	
	public void unCropList() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).x = x;
			buttons.get(i).y = y + i * height;
			buttons.get(i).setHeight(height);
		}
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).x = x;
			buttons.get(i).y = y + i * height;
		}
		if(buttons.size() > 3)
			cropList();
		else
			unCropList();
	}
	
	public void RemoveElements(String... names) {
		for (String string : names) {
			RemoveElement(string);
		}
	}
	
	public void RemoveElement(String name) {
		Button toBeRemoved = null;
		for (Button button : buttons) {
			if(button.getMessage().getString().equals(name)) {
				toBeRemoved = button;
				break;
			}
		}
		buttons.remove(toBeRemoved);
		if(buttons.size() <= 3)
			unCropList();
	}
	
	public void AddElement(String name) {
		buttons.add(new Button(x, y + buttons.size() * height, width, height, MutableComponent.create(new LiteralContents(name)), onPress));
		if(buttons.size() > 3)
			cropList();
	}
	
	public void AddElements(String... names) {
		for (int i = 0; i < names.length; i++) {
			buttons.add(new Button(x, y + (i + buttons.size()) * height, width, height, MutableComponent.create(new LiteralContents(names[i])), onPress));
		}
		if(buttons.size() > 3)
			cropList();
	}
	
	public void renderButton(Button button, PoseStack matrix, int x, int y, float partialticks, ResourceLocation TEXTURE) {
		Minecraft minecraft = Minecraft.getInstance();
	    Font fontrenderer = minecraft.font;
	    minecraft.getTextureManager().bindForSetup(TEXTURE);
	    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
	    int i = this.getYImage(button.isHoveredOrFocused());
	    RenderSystem.enableBlend();
	    RenderSystem.defaultBlendFunc();
	    RenderSystem.enableDepthTest();
	    //button.blit(matrix, button.x, button.y, 0, 46 + i * 20, button.getWidth() / 2, button.getHeight());
	    //button.blit(matrix, button.x + button.getWidth() / 2, button.y, 200 - button.getWidth() / 2, 46 + i * 20, button.getWidth() / 2, button.getHeight());
	    ScreenUtils.blitWithBorder(matrix, TEXTURE, button.x, button.y, 0, (i*20) + 46, 
	    		button.getWidth(), button.getHeight(), 200, 20, 2, 3, 2, 2, this.getBlitOffset());
		
	    minecraft.getTextureManager().bindForSetup(WIDGETS_LOCATION);
//	    button.renderBg(matrix, minecraft, x, y);
	    int j = getFGColor();
	    drawCenteredString(matrix, fontrenderer, button.getMessage(), button.x + button.getWidth() / 2, button.y + (button.getHeight() - 8) / 2, j | (int)Math.ceil(this.alpha * 255.0F) << 24);
	    if (button.isHoveredOrFocused()) {
	    	button.renderToolTip(matrix, x, y);
	    }
	}
	
	@Override
	public void renderButton(PoseStack matrix, int x, int y, float partialticks) {
		for (Button button : buttons) {
			button.render(matrix, x, y, partialticks);
			renderButton(button, matrix, x, y, partialticks, TEXTURE);
		}	
	}
	
	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
		for (Button button : buttons) {
			if(button.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isMouseOver(double p_231047_1_, double p_231047_3_) {
		for (Button button : buttons) {
			boolean cond = button.isMouseOver(p_231047_1_, p_231047_3_);
			if(cond != button.isFocused())
				button.changeFocus(cond);
			if(cond)
				return true;
		}
		return super.isMouseOver(p_231047_1_, p_231047_3_);
	}
	
	@Override
	public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
		for (Button button : buttons) {
			if(button.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_)) {
				return true;
			}
		}
		return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
	}

}
