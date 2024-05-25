package KOWI2003.LaserMod.gui;

import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.container.ContainerModStation;
import KOWI2003.LaserMod.gui.widgets.UpgradeList;
import KOWI2003.LaserMod.items.ItemLaserArmorBase;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.interfaces.ILaserUpgradable;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketLaser;
import KOWI2003.LaserMod.network.PacketModStation;
import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class GuiModStation extends BetterAbstractContainerScreen<ContainerModStation> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/mod_station.png");
	public static final ResourceLocation TEXTURE_LEFT = new ResourceLocation(Reference.MODID,
			"textures/gui/container/gui_left_eddition.png");
	
	public TileEntityModStation te;
	public Player player;
	public Inventory playerInv;
	
	public Button InsertUpgarde;
	public UpgradeList upgrades;
	
	public ForgeSlider Red,Green,Blue;
	
	ILaserUpgradable tool;
	boolean wasColorOpen = false;
	
	public GuiModStation(ContainerModStation container, Inventory playerInv, Component titleIn) {
		super(container, playerInv, titleIn);
		this.te = container.getTileEntity();
		this.playerInv = playerInv;
		this.player = playerInv.player;
		
		InsertUpgarde = new Button(60, 0, 40, 20, MutableComponent.create(new LiteralContents("Insert")), (button) ->  {
			PacketHandler.sendToServer(new PacketModStation(te.getBlockPos(), "", true));
			te.addUpgrade();
			if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable)
				upgrades.replaceList(getFormalUpgradeName(LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgrades()));
		});
		
		upgrades = new UpgradeList(0, 0, 63, 20, null, (button) -> {
			PacketHandler.sendToServer(new PacketModStation(te.getBlockPos(), getUpgradeName(button.getMessage().getString()), false));
			te.removeUpgrade(getUpgradeName(button.getMessage().getString()));
			if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable)
				upgrades.replaceList(getFormalUpgradeName(LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgrades()));
		},
			new ResourceLocation(Reference.MODID,
					"textures/gui/mod_widgets.png")
		);
		
		

		Red = new ForgeSlider(0, 0, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")), MutableComponent.create(new LiteralContents("")), 0, 1, te.getColor()[0], 0.001d, 0, true);
		Green = new ForgeSlider(0, 40, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")), MutableComponent.create(new LiteralContents("")), 0, 1, te.getColor()[0], 0.001d, 0, true);
		Blue = new ForgeSlider(0, 80, 20, 20, MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")), MutableComponent.create(new LiteralContents("")), 0, 1, te.getColor()[0], 0.001d, 0, true);
		
		Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
	}
	
	protected void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2 + 37;
		this.inventoryLabelY = this.imageHeight - 91;
      
		ChangeSizeButtonLocationUpdate();
      
		clearWidgets();
		addRenderableWidget(InsertUpgarde);
		addRenderableWidget(upgrades);
		addRenderableWidget(Red);
		addRenderableWidget(Green);
		addRenderableWidget(Blue);
	}

	public void ChangeSizeButtonLocationUpdate() {
		int posx = width / 2;
		int posy = height / 2;
		
		upgrades.setPos(posx-78, posy - 74);
		
		InsertUpgarde.x = (posx);
		InsertUpgarde.y = (posy - 45);

		int x = -164;
		int y = 40;
		
		this.Red.x = (posx + x);
		this.Red.y = (posy + 1 - y);
		this.Green.x = (posx + x);
		this.Green.y = (posy + 21 - y);
		this.Blue.x = (posx + x);
		this.Blue.y = (posy + 41 - y);

		Red.setWidth(75);
		Green.setWidth(75);
		Blue.setWidth(75);
	}
	
	public void render(@Nonnull PoseStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.renderBackground(matrix);
		super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
		this.renderTooltip(matrix, p_230430_2_, p_230430_3_);
	}
	
	protected void renderBg(@Nonnull PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		
		matrix.pushPose();
		RenderUtils.bindTexture(TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
		
		if(te.handler.getStackInSlot(0).isEmpty() && tool != null) {
			upgrades.replaceList();
			tool = null;
		}
		if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable)
			if(te.handler.getStackInSlot(0).getItem() != tool) {
				tool = ((ILaserUpgradable)te.handler.getStackInSlot(0).getItem());
				upgrades.replaceList(getFormalUpgradeName(LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgrades()));
			}else if(LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgrades().size() != upgrades.size()){
				upgrades.replaceList(getFormalUpgradeName(LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgrades()));
			}
		
		Red.active = Red.visible =
				Green.active = Green.visible =
				Blue.active = Blue.visible = 
				te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable ? LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).hasUpgarde("color") : false;
		
		if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable) {
			LaserProperties properties = LaserItemUtils.getProperties(te.handler.getStackInSlot(0));

			if(properties.hasUpgarde("color")) {
				if(!wasColorOpen) {
					Red.setValue(te.getColor()[0]);
					Green.setValue(te.getColor()[1]);
					Blue.setValue(te.getColor()[2]);
				}
				RenderUtils.bindTexture(TEXTURE_LEFT);
				this.blit(matrix, getGuiLeft() - 80, getGuiTop(), 0, 0, 81, this.imageHeight);
				wasColorOpen = true;
				int Sx = width / 2 - 157;
				int Sy = height/ 2 - 70;
				ScreenUtils.blitWithBorder(matrix, new ResourceLocation(Reference.MODID,
						"textures/gui/mod_widgets.png"), Sx, Sy, 0, 66, 
			    		60, 20, 200, 20, 2, 3, 2, 2, this.getBlitOffset());

				drawCenteredString(matrix, font, "Color", Sx + 60 / 2, Sy + (20 - 8) / 2, Utils.getHexIntFromRGB(1.0f, 1.0f, 1.0f));
			    RenderUtils.bindTexture(new ResourceLocation(Reference.MODID,
			    		"textures/gui/mod_widgets.png"));
			    
			    //RenderSystem.disableTexture();
			    int relX = (width / 2);
			    int relY = (height / 2);
			    RenderUtils.Gui.drawQuadColor(matrix, relX - 146, relY + 30, 40, 40, 0.19607843f, 0.19607843f, 0.19607843f);
			    RenderUtils.Gui.drawQuadColor(matrix, relX - 144, relY + 32, 36, 36, te.getColor()[0], te.getColor()[1], te.getColor()[2]);
			    //RenderSystem.enableTexture();
				
			}else if(wasColorOpen) {
				PacketHandler.sendToServer(new PacketLaser(te.getBlockPos(), 1.0f, 0f, 0f));
				te.setColor(1.0f, 0, 0);
			}
		}
		
		if(!te.handler.getStackInSlot(0).isEmpty())
			renderStats(matrix, mouseX, mouseY);

		matrix.popPose();
	}
	
	public String[] getFormalUpgradeName(List<ItemUpgradeBase> bases) {
		String[] names = new String[bases.size()];
		for (int i = 0; i < bases.size(); i++) {
			names[i] = Utils.makeFirstCaps(bases.get(i).getUpgradeBaseName());
		}
		return names;
	}
	
	public String getUpgradeName(String formalUpgradeName) {
		if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable) {
			ItemUpgradeBase base = LaserItemUtils.getProperties(te.handler.getStackInSlot(0)).getUpgarde(formalUpgradeName.toLowerCase());
			if(base == null)
				return "";
			return base.getUpgradeName();
		}
		return "";
	}
	
	protected void renderStats(PoseStack matrix, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderUtils.bindTexture(GuiLaser.TEXTURE);
		this.blit(matrix, i + 175, j, 175, 0, 81, imageHeight);
		
		i += 175;
		
		if(te.handler.getStackInSlot(0).getItem() instanceof ILaserUpgradable) {
			ItemStack stack = te.handler.getStackInSlot(0);
			Item tool = stack.getItem();
			
			RenderUtils.Gui.drawFontString(matrix, "Stats", i + 25, j + 7, 0.2f, 0.2f, 0.2f);
			RenderUtils.Gui.drawStringWithinLine(matrix, tool.getName(stack).getString(), i + 8, j + 24, 65, Utils.getHexIntFromRGB(0.3f, 0.3f, 0.3f));
//			RenderUtils.Gui.drawFontString(matrix, tool.getName(stack).getString(), i + 8, j + 24, 0.3f, 0.3f, 0.3f);
			
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			
			if(te.handler.getStackInSlot(0).getItem() instanceof ItemLaserToolBase) {
				RenderUtils.Gui.drawFontString(matrix, "Damage: ", i + 8, j + 44, 0.35f, 0.35f, 0.35f);
				RenderUtils.Gui.drawFontString(matrix, "" + ((Math.round(properties.getProperty(Properties.DAMAGE)*10f)/10f) + 1f), i + 48, j + 44, 0.35f, 1f, 0.35f);
				
				j += 13;
				
				RenderUtils.Gui.drawFontString(matrix, "Speed: ", i + 8, j + 44, 0.35f, 0.35f, 0.35f);
				RenderUtils.Gui.drawFontString(matrix, "" + properties.getProperty(Properties.SPEED), i + 48, j + 44, 0.35f, 1f, 0.35f);
			}else if(te.handler.getStackInSlot(0).getItem() instanceof ItemLaserArmorBase) {
				RenderUtils.Gui.drawFontString(matrix, "Defense: ", i + 8, j + 44, 0.35f, 0.35f, 0.35f);
				RenderUtils.Gui.drawFontString(matrix, "" + ((Math.round(properties.getProperty(Properties.DEFENCE)*10f)/10f)), i + 58, j + 44, 0.35f, 1f, 0.35f);
				
				j += 13;
				
				RenderUtils.Gui.drawFontString(matrix, "Toughness: ", i + 4, j + 44, 0.35f, 0.35f, 0.35f, 0.9f);
				RenderUtils.Gui.drawFontString(matrix, "" + properties.getProperty(Properties.TOUGHNESS), i + 59, j + 44, 0.35f, 1f, 0.35f, 0.9f);
			}
			
			j += 13;
			
//			RenderUtils.Gui.drawFontString(matrix, "Max Charge: ", i + 8, j + 44, 0.35f, 0.35f, 0.35f);
//			RenderUtils.Gui.drawFontString(matrix, "" + tool.getMaxCharge(), i + 48, j + 44, 0.35f, 1f, 0.35f);
			
			j += 20;
			
			if(properties.hasUpgradeWithAbilityName()) {
				RenderUtils.Gui.drawFontString(matrix, "Abilities", i + 21f, j + 33, 0.35f, 0.35f, 0.35f);
				
				for (ItemUpgradeBase upgrade : properties.getUpgrades()) {
					if(((ILaserUpgradable)tool).getAbilityNames(upgrade).length > 0) {
						float[] color = upgrade.getAbilityNameColor();
						for(String abilityName : ((ILaserUpgradable)tool).getAbilityNames(upgrade)) {
							RenderUtils.Gui.drawFontString(matrix, abilityName, i + 13.5f, j + 44, 
									color.length > 0 ? color[0] : 0.35f,
									color.length > 1 ? color[1] : 0.35f,
									color.length > 2 ? color[2] : 0.35f);
							j+= 10;
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean i = super.mouseClicked(mouseX, mouseY, button);
		Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
		Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
		Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		return i;
	}
	
	@Override
	public boolean isMouseOver(double p_231047_1_, double p_231047_3_) {
		for (AbstractWidget widget : buttons) {
			widget.isMouseOver(p_231047_1_, p_231047_3_);
		}
		return super.isMouseOver(p_231047_1_, p_231047_3_);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if((Red.isMouseOver(mouseX, mouseY) && Red.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) ||
				(Green.isMouseOver(mouseX, mouseY) && Green.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) ||
				(Blue.isMouseOver(mouseX, mouseY) && Blue.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))) {
			PacketHandler.sendToServer(new PacketLaser(te.getBlockPos(), (float)Red.getValue(), (float)Green.getValue(), (float)Blue.getValue()));
			te.setColor((float)Red.getValue(), (float)Green.getValue(), (float)Blue.getValue());
			
			Red.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.red")));
			Green.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.green")));
			Blue.setMessage(MutableComponent.create(new TranslatableContents("container.lasermod.laser.blue")));
		}
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public void mouseMoved(double p_212927_1_, double p_212927_3_) {
		for (AbstractWidget widget : buttons) {
			widget.mouseMoved(p_212927_1_, p_212927_3_);
		}
		super.mouseMoved(p_212927_1_, p_212927_3_);
	}
	
	@Override
	public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
//		for (AbstractWidget widget : buttons) {
//			widget.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
//		}
		return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
	}
}
