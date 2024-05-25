package KOWI2003.LaserMod.gui.widgets;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ButtonTexture extends Button{

	ResourceLocation texture;
	
	public int uvX = 0;
	public int uvY = 0;
	
	public ButtonTexture(int x, int y, int uvX, int uvY, int width, int height, Component title, OnPress action, ResourceLocation texture) {
		super(x, y, width, height, title, action);
		this.uvX = uvX;
		this.uvY = uvY-this.height;
		this.texture = texture;
	}
	
	public ButtonTexture(int x, int y, int uvX, int uvY, int width, int height, Component title, OnPress action, OnTooltip tooltip, ResourceLocation texture) {
		super(x, y, width, height, title, action, tooltip);
		this.uvX = uvX;
		this.uvY = uvY-this.height;
		this.texture = texture;
	}
	
	@Override
	public void renderButton(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		renderButtonRaw(matrix, mouseX, mouseY, partialTicks);
		if (this.isHoveredOrFocused()) {
			this.renderToolTip(matrix, mouseX, mouseY);
		}
	}
	
	private void renderButtonRaw(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getYImage(this.isHoveredOrFocused());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(matrix, this.x, this.y, uvX, uvY + i * this.height, this.width, this.height);
		this.renderBg(matrix, minecraft, mouseX, mouseY);
		int j = getFGColor();
		drawCenteredString(matrix, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
	}
	

}
