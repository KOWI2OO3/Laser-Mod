package KOWI2003.LaserMod.gui.widgets;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public class Slider2D extends Button {

	public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(Reference.MODID,"textures/gui/mod_widgets.png");
	
	//Widget
	public float wX, wY = 0;
	public float wSize = 10;
	
	public String internalName = "Slider";
	
	public float r, g, b = 1f;
	
	public Slider2D(int x, int y, int widht, int height,
			OnPress action) {
		super(x, y, widht, height, MutableComponent.create(new LiteralContents("")), action);
	}
	
	public Slider2D(int x, int y, int widht, int height, String internalName,
			OnPress action) {
		super(x, y, widht, height, MutableComponent.create(new LiteralContents("")), action);
		this.internalName = internalName;
	}
	
	public void setSliderStartValues(float x, float y, float size) {
		wSize = size;
		setValueX(x);
		setValueY(y);
	}
	
	boolean wasHovered = false;
	
	public Vec2 getValue() {
		return new Vec2(getValueX(), getValueY());
	}
	
	public float getValueX() {
		return wX/(width - wSize);
	}
	
	public float getValueY() {
		return wY/(height - wSize);
	}
	
	public void setValueX(float x) {
		x = Math.min(1, Math.max(0, x));
		wX = x * (width - wSize);
	}
	
	public void setValueY(float y) {
		y = Math.min(1, Math.max(0, y));
		wY = y * (height - wSize);
	}
	
	public void setValue(Vec2 vec) {
		setValue(vec.x, vec.y);
	}
	
	public void setValue(float x, float y) {
		setValueX(x);
		setValueY(y);
	}
	
	@Override
	public void setWidth(int width) {
		float sX = getValueX();
		super.setWidth(width);
		setValueX(sX);
	}
	
	@Override
	public void setHeight(int height) {
		float sY = getValueY();
		super.setHeight(height);
		setValueY(sY);
	}
	
	@Override
	public void render(@Nonnull PoseStack pose, int x, int y, float partialTicks) {
		if (this.visible) {
	         this.isHovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;

	         if (this.visible) {
	            this.onRender(pose, x, y, partialTicks);
	         }
	         
	         this.wasHovered = this.isHovered;
	      }
	}
	
	public void onRender(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
	    RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(r, g, b, this.alpha);
		renderButton(stack, mouseX, mouseY, partialTicks);
		
		//Render Slider
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderUtils.Gui.drawQuad(stack, x + wX, y + wY, wSize, wSize, 20f / 256f + 0.0001f, 108f / 256f, 11f / 256f, -11f / 256f);
	}
	
	@Override
	public void renderButton(@Nonnull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		
		int uvX = 41;
		int uvY = 106; //46 + i * 20
		
		this.blit(pose, this.x, this.y, uvX, uvY, this.width / 2, this.height/2); //matrix, guiX, guiY, uvX, uvY, width, height
		this.blit(pose, this.x + this.width / 2, this.y, (256 - this.width / 2), uvY, this.width / 2, this.height/2);
		
		this.blit(pose, this.x, this.y + this.height / 2, uvX, (206 - this.height / 2), this.width / 2, this.height/2); //matrix, guiX, guiY, uvX, uvY, width, height
		this.blit(pose, this.x + this.width / 2, this.y + this.height / 2, (256 - this.width / 2), (206 - this.height / 2), this.width / 2, this.height/2);
		
		this.renderBg(pose, minecraft, mouseX, mouseY);
	
		if (this.isHovered)
			this.renderToolTip(pose, mouseX, mouseY);
	}
	
	@Override
	protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if(isDragging) {
			wX = (float)mouseX - x - wSize/2f;
			wX = Math.min(width, Math.max(0, wX) + wSize) - wSize;
			wY = (float)mouseY - y - wSize/2f;
			wY = Math.min(height, Math.max(0, wY) + wSize) - wSize;
		}
		super.onDrag(mouseX, mouseY, deltaX, deltaY);
	}
	
	public boolean isDragging = false;
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX,
			double deltaY) {
		if (this.isValidClickButton(button)) {
			if(mouseX >= wX + x && mouseX <= wX + wSize + x && mouseY >= wY + y && mouseY <= wY + y + wSize) {
				isDragging = true;
			}
		}
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public void mouseMoved(double deltaX, double deltaY) {
		super.mouseMoved(deltaX, deltaY);
	}
	
	@Override
	public void onPress() {
		super.onPress();
	}
	
	@Override
	public void onRelease(double p_231000_1_, double p_231000_3_) {
		if(isDragging)
			isDragging = false;
		super.onRelease(p_231000_1_, p_231000_3_);
	}
}
