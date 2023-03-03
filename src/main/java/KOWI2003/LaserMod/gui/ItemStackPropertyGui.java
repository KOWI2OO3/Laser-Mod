package KOWI2003.LaserMod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.container.ContainerItemStackProperty;
import KOWI2003.LaserMod.gui.widgets.properties.ItemProperty;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemStackPropertyGui extends BetterAbstractContainerScreen<ContainerItemStackProperty> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/item_property.png");
	
	onClose closeAction;
	
	String propertyName;
	
	public ItemStackPropertyGui(ContainerItemStackProperty container, Inventory inv, Component title) {
		super(container, inv, MutableComponent.create(new LiteralContents("")));
		closeAction = () -> {ItemProperty.openInstance.onCloseMenu();};
		propertyName = title.getString();
	}
	
	@Override
	protected void init() {
		super.init();
		titleLabelX = 80;
		titleLabelY = 52;
	}
	
	@Override
	protected void renderLabels(PoseStack matrix, int mouseX, int mouseY) {
//		super.renderLabels(matrix, mouseX, mouseY);
		int posx = width / 2;
		int posy = height / 2;
		
		drawCenteredString(matrix, font, propertyName, posx-getGuiLeft(), posy-getGuiTop()-42, 0xffffff);
	}
	
	@Override
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		renderBackground(matrix);
	    RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	@Override
	public void onClose() {
		super.onClose();
		if(ItemProperty.openInstance != null)
			ItemProperty.openInstance.onCloseMenu();
	}
	
	public static interface onClose {
		void run();
	}

}
