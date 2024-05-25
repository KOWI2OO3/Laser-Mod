package KOWI2003.LaserMod.gui;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.container.ContainerLaser;
import KOWI2003.LaserMod.gui.widgets.Slider2D;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketLaser;
import KOWI2003.LaserMod.network.PacketLaserDirection;
import KOWI2003.LaserMod.network.PacketLaserMode;
import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.utils.MathUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class GuiLaser extends BetterAbstractContainerScreen<ContainerLaser> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/laser.png");
	
	public TileEntityLaser te;
	public Player player;
	public Inventory playerInv;
	
	public Button options;
	public boolean menuOpen = false;
	
	public ForgeSlider Red;
	public ForgeSlider Green;
	public ForgeSlider Blue;
	
	public Button modeNext;
	public Button modePrev;
	
	public Slider2D gimbalSlider;
	
	public GuiLaser(ContainerLaser container, Inventory playerInv, Component titleIn) {
		super(container, playerInv, titleIn);
		this.te = container.getTileEntity();
		this.playerInv = playerInv;
		this.player = playerInv.player;

		options = new Button(width + getGuiLeft() + 260 - 30, getGuiTop() + 100, 50, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.button.options")), (button) -> 
			toggleMenu());
		
		Red = new ForgeSlider(0, 0, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")), MutableComponent.create(new LiteralContents("")), 0f, 1f, te.red, .01f, 0, true);
		Green = new ForgeSlider(0, 40, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")), MutableComponent.create(new LiteralContents("")), 0f, 1f, te.green, .01f, 0, true);
		Blue = new ForgeSlider(0, 80, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")), MutableComponent.create(new LiteralContents("")), 0f, 1f, te.blue, .01f, 0, true);
		
		Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		
		modePrev = new Button(0, 0, 20, 20, MutableComponent.create(new TranslatableContents("<")), (button) -> {
			PacketHandler.sendToServer(new PacketLaserMode(te.getBlockPos(), false));
		});
		
		modeNext = new Button(modePrev.x + modePrev.getWidth() + 60, modePrev.y, modePrev.getWidth(), modePrev.getHeight(), MutableComponent.create(new TranslatableContents(">")), (button) -> {
			PacketHandler.sendToServer(new PacketLaserMode(te.getBlockPos(), true));
		});
		
		gimbalSlider = new Slider2D(0, 0, 100, 100, (o) -> {});
		gimbalSlider.setSliderStartValues(0.5f, 0.5f, 15f);
		if(te instanceof TileEntityAdvancedLaser) {
			float absAngle = Math.abs(TileEntityAdvancedLaser.minAngle);
			Vec2 rotation = ((TileEntityAdvancedLaser)te).getEularRotation();
			gimbalSlider.setSliderStartValues(Math.round(((rotation.y/absAngle)/2f + 0.5f) * 100f) / 100f, Math.round(((-rotation.x/absAngle)/2f + 0.5f) * 100f) / 100f, gimbalSlider.wSize);
		}
	}
	
	public void toggleMenu() {
		menuOpen = !menuOpen;
	}
	
	protected void init() {
	  super.init();
	  this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	
	  ChangeSizeButtonLocationUpdate();
	  options.active = options.visible = hasSideMenu();
	  
	  clearWidgets();
	  if(te instanceof TileEntityAdvancedLaser)
	  addRenderableWidget(gimbalSlider);
	  
	  addRenderableWidget(options);
	  addRenderableWidget(Red);
	  addRenderableWidget(Green);
	  addRenderableWidget(Blue);
	  
	  addRenderableWidget(modeNext);
	  addRenderableWidget(modePrev);
	  
	}
	
	public void ChangeSizeButtonLocationUpdate() {
		int posx = width / 2;
		int posy = height / 2;
		
		this.options.x = (posx + 30);
		this.options.y = (posy - 25);
		
		this.modePrev.x = (posx + 91);
		this.modePrev.y = (posy - 40);
		
		this.modeNext.x = (modePrev.x + modePrev.getWidth() + 30);
		this.modeNext.y = (modePrev.y);
		
		int x = 89;
		int y = 10;
		
		this.Red.x = (posx + x);
		this.Red.y = (posy + 1 - y);
		this.Green.x = (posx + x);
		this.Green.y = (posy + 21 - y);
		this.Blue.x = (posx + x);
		this.Blue.y = (posy + 41 - y);

		Red.setWidth(75);
		Green.setWidth(75);
		Blue.setWidth(75);
		
		if(te instanceof TileEntityAdvancedLaser) {
			gimbalSlider.x = (posx-81);
			gimbalSlider.y = (posy-65);
			gimbalSlider.setWidth(50);
			gimbalSlider.setHeight(50);
		}
	}

	public void render(@Nonnull PoseStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.renderBackground(matrix);
		super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
		this.renderTooltip(matrix, p_230430_2_, p_230430_3_);
	}
	
	@SuppressWarnings("null")
	protected void renderBg(@Nonnull PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		if(te != null) {
		    RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(te.red, te.green, te.blue, 1.0f);
			if(te instanceof TileEntityAdvancedLaser) {
				gimbalSlider.r = te.red;
				gimbalSlider.g = te.green;
				gimbalSlider.b = te.blue;
			}
		}else
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
		
		if(hasChanged())
			onUpgradeAddOrRemove();
		
		Red.active = Red.visible =
			Blue.active = Blue.visible =
			Green.active = Green.visible = te.getProperties().hasUpgarde("color") && menuOpen;
		
		modePrev.active = modePrev.visible =
				modeNext.active = modeNext.visible = te.getProperties().hasUpgarde("mode") && menuOpen;
		
		if(hasSideMenu() && menuOpen) {
			this.minecraft.getTextureManager().bindForSetup(TEXTURE);
			this.blit(matrix, i + 175, j, 175, 0, 81, imageHeight);
			
			if(te.getProperties().hasUpgarde("mode")) {
				ScreenUtils.blitWithBorder(matrix, Button.WIDGETS_LOCATION, modePrev.x, modePrev.y - modePrev.getHeight(), 0, 46, 
						modeNext.x + modeNext.getWidth() - modePrev.x,modePrev.getHeight(), 200, 20, 2, 3, 2, 2, this.getBlitOffset());
				
				Component modeText = MutableComponent.create(new LiteralContents(te.mode.getFormalName())).copy().setStyle(title.getStyle());
				drawCenteredString(matrix, font, modeText, modePrev.x + 
						(modeNext.x + modeNext.getWidth() - modePrev.x)/2, modePrev.y - modePrev.getHeight()/2 - font.lineHeight/2, Utils.getHexIntFromRGB(1f, 1f, 1f));
				Component text = MutableComponent.create(new LiteralContents("Laser Mode:")).copy().setStyle(title.getStyle());
				font.draw(matrix, text, getGuiLeft() + 185, getGuiTop() + 10, Utils.getHexIntFromRGB(0.3f, 0.3f, 0.3f));
			}
		}
		
		renderFG(mouseX, mouseY);
	}
	
	protected void renderFG(int mouseX, int mouseY) {
		if(te instanceof TileEntityAdvancedLaser) {
			Vec2 rotation = gimbalSlider.getValue();
			rotation = new Vec2(-(rotation.y - 0.5f) * 2f, (rotation.x - 0.5f) * 2f);
			rotation = MathUtils.mulVector(rotation, Math.abs(TileEntityAdvancedLaser.minAngle));
			rotation = new Vec2(Math.round(rotation.x * 100f) / 100f, Math.round(rotation.y * 100f) / 100f);
			PacketHandler.INSTANCE.sendToServer(new PacketLaserDirection(te.getBlockPos(), rotation));
		}
	}
	
	private void onUpgradeAddOrRemove() {
		if(menuOpen && !hasSideMenu())
			menuOpen = false;
		options.active = options.visible = hasSideMenu();
		if(!te.getProperties().hasUpgarde("color")) {
			Red.setValue(1.0f);
			Green.setValue(0.0f);
			Blue.setValue(0.0f);
			PacketHandler.sendToServer(new PacketLaser(te.getBlockPos(), (float)Red.getValue(), (float)Green.getValue(), (float)Blue.getValue()));
		}
		if(!te.getProperties().hasUpgarde("mode")) {
			PacketHandler.sendToServer(new PacketLaserMode(te.getBlockPos(), 0));
		}
	}
	
	public boolean hasSideMenu() {
		return te.getProperties().hasUpgarde("color") || te.getProperties().hasUpgarde("mode");
	}
	
	boolean Ucol, UMode = false;
	public boolean hasChanged() {
		if(te.getProperties().hasUpgarde("color") != Ucol || te.getProperties().hasUpgarde("mode") != UMode) {
			Ucol = te.getProperties().hasUpgarde("color");
			UMode = te.getProperties().hasUpgarde("mode");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
//		for (AbstractWidget widget : buttons) {
			//widget.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
//		}
		boolean i = super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
		Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		return i;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int p_231045_5_, double p_231045_6_,
			double p_231045_8_) {
		if((Red.isMouseOver(mouseX, mouseY) && Red.mouseDragged(mouseX, mouseY, p_231045_5_, p_231045_6_, p_231045_8_)) ||
				(Green.isMouseOver(mouseX, mouseY) && Green.mouseDragged(mouseX, mouseY, p_231045_5_, p_231045_6_, p_231045_8_)) ||
				(Blue.isMouseOver(mouseX, mouseY) && Blue.mouseDragged(mouseX, mouseY, p_231045_5_, p_231045_6_, p_231045_8_))) {
			PacketHandler.sendToServer(new PacketLaser(te.getBlockPos(), (float)Red.getValue(), (float)Green.getValue(), (float)Blue.getValue()));

			Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
			Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
			Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		}
		if(te instanceof TileEntityAdvancedLaser)
			gimbalSlider.mouseDragged(mouseX, mouseY, p_231045_5_, p_231045_6_, p_231045_8_);
		return super.mouseDragged(mouseX, mouseY, p_231045_5_, p_231045_6_, p_231045_8_);
	}
	
	@Override
	public void mouseMoved(double deltaX, double deltaY) {
		Red.mouseMoved(deltaX, deltaY);
		Green.mouseMoved(deltaX, deltaY);
		Blue.mouseMoved(deltaX, deltaY);
		if(te instanceof TileEntityAdvancedLaser)
			gimbalSlider.mouseMoved(deltaX, deltaY);
		super.mouseMoved(deltaX, deltaY);
	}
	
	@Override
	public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
		if(Red.isMouseOver(p_231048_1_, p_231048_3_) && Red.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_))
			Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		if(Green.isMouseOver(p_231048_1_, p_231048_3_) &&Green.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_))
			Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		if(Blue.isMouseOver(p_231048_1_, p_231048_3_) &&Blue.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_))
			Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		PacketHandler.sendToServer(new PacketLaser(te.getBlockPos(), (float)Red.getValue(), (float)Green.getValue(), (float)Blue.getValue()));
		if(te instanceof TileEntityAdvancedLaser)
			gimbalSlider.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
		return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
	}

}
