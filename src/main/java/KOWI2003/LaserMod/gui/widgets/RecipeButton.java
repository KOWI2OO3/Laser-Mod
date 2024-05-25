package KOWI2003.LaserMod.gui.widgets;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeButton extends Button {
	
	Recipe<?> recipeID;
	protected final IRecipePressable onPress;
	
	public RecipeButton(int x, int y, int width, int height,
			Component title, IRecipePressable action) {
		super(x, y, width, height, title, btn -> {});
		this.onPress = action;
	}
	
	public RecipeButton(int x, int y, int width, int height,
			Recipe<?> recipe, IRecipePressable action) {
		this(x, y, width, height, MutableComponent.create(new LiteralContents("Null")), action);
		setRecipe(recipe);
	}
	
	public void setRecipe(Recipe<?> recipe) {
		recipeID = recipe;
		setMessage(recipe == null ? MutableComponent.create(new LiteralContents("Null")) : recipe.getResultItem().getHoverName());
	}
	
	@Override
	public void renderButton(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		Font fontrenderer = minecraft.font;
		minecraft.getTextureManager().bindForSetup(new ResourceLocation(Reference.MODID,
				"textures/gui/mod_widgets.png"));
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getYImage(this.isHovered);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		int tempWidth = getWidth();
		float scales = (float)getHeight()/20f;
		setWidth((int) (tempWidth * 1f/scales));
		
		matrix.pushPose();
		matrix.translate(this.x, this.y, 0);
		matrix.scale(scales, scales, scales);
		this.blit(matrix, 0, 0, 0, 46 + i * 20, this.width / 2, 20);
		matrix.popPose();
		matrix.pushPose();
		matrix.translate(this.x, this.y, 0);
		matrix.scale(scales, scales, scales);
		matrix.translate(this.width*scales, 0, 0);
		this.blit(matrix, 0, 0, 200 - this.width / 2, 46 + i * 20, this.width / 2, 20);
		matrix.popPose();
		this.renderBg(matrix, minecraft, mouseX, mouseY);
		int j = this.getFGColor();
		setWidth(tempWidth);
		if(recipeID == null)
			return;
		
		float scale = 0.55f;
		GL11.glPushMatrix();
		GL11.glTranslated(this.x + 2, this.y + (height-8*scale)/2 - 4*scale, 0);
		GL11.glScaled(scale, scale, scale);
		Minecraft.getInstance().getItemRenderer().renderGuiItem(recipeID.getResultItem(), 0, 0);
		GL11.glPopMatrix();
		matrix.pushPose();
		matrix.translate(this.x + this.width / 2 - 8/scale, this.y + (this.height - 8 * scale) / 2, 0);
		matrix.scale(scale, scale, scale);
		String message = getMessage().getString();
		if(message.length() > 12)
			message = message.substring(0, 12) + "...";
		drawString(matrix, fontrenderer, MutableComponent.create(new LiteralContents(message)), 0, 0, j);
		matrix.popPose();
		
		if (this.isHovered) {
			this.renderToolTip(matrix, mouseX, mouseY);
		}
	}
	
	@Override
	public void onPress() {
		if(onPress != null)
			this.onPress.onPress(this, recipeID);
	}
	
	public static interface IRecipePressable {
		public void onPress(Button button, Recipe<?> recipe);
	}
}
