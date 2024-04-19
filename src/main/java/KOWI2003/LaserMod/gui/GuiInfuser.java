package KOWI2003.LaserMod.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.container.ContainerInfuser;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class GuiInfuser extends AbstractContainerScreen<ContainerInfuser> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/infuser.png");
	
	public TileEntityInfuser te;
	public Player player;
	public Inventory playerInv;
	
	public GuiInfuser(ContainerInfuser container, Inventory playerInv, Component titleIn) {
		super(container, playerInv, titleIn);
		this.te = container.getTileEntity();
		this.playerInv = playerInv;
		this.player = playerInv.player;
	}
	
	protected void init() {
      super.init();
      this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	public void render(PoseStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.renderBackground(matrix);
		super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
		this.renderTooltip(matrix, p_230430_2_, p_230430_3_);
	}
	
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		RenderUtils.bindTexture(TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
		
		if(te.getProgress() < 1 && te.getProgress() != 0)
			this.blit(matrix, i + 64, j + 60, 176, 0, Math.round(Math.abs(45 * (te.getProgress() - 1))), 9);
		
		renderFG(mouseX, mouseY);
	}
	
	protected void renderFG(int mouseX, int mouseY) {
		
	}
}
