package KOWI2003.LaserMod.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import KOWI2003.LaserMod.gui.manual.data.widget.ButtonComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.FlipPageComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.ImageComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.PageSelector;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.gui.manual.widgets.ManualButton;
import KOWI2003.LaserMod.gui.manual.widgets.ManualComponent;
import KOWI2003.LaserMod.gui.manual.widgets.ManualFlipPage;
import KOWI2003.LaserMod.gui.manual.widgets.ManualImage;
import KOWI2003.LaserMod.gui.manual.widgets.ManualItem;
import KOWI2003.LaserMod.gui.manual.widgets.ManualPageSelector;
import KOWI2003.LaserMod.gui.manual.widgets.ManualTextBox;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;

public class GuiManual extends Screen {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/manual/manual_base.png");
	
	List<AbstractWidget> buttons;
	GuiContext gui;

	protected int imageWidth = 176;
	protected int imageHeight = 166;
	
	public static GuiContext openMenu;
	
	public GuiManual(GuiContext gui) {
		super(Component.translatable(gui.getId()));
		buttons = new ArrayList<>();
		this.minecraft = Minecraft.getInstance();
		
		this.gui = gui;
		openMenu = gui;
		
//		System.out.println(gui.page + " -> " + ManualHandler.hasPrev(gui.page) + " : " + ManualHandler.hasNext(gui.page));
	}

	@Override
	protected void clearWidgets() {
		buttons.clear();
		super.clearWidgets();
	}
	
	@Override
	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
		if(widget instanceof AbstractWidget) buttons.add((AbstractWidget)widget);
		return super.addRenderableWidget(widget);
	}
	
	@Override
	protected void init() {
		super.init();
		gui.init();
		clearWidgets();
  
		for (WidgetBase base : gui.getComponents()) {
			switch (base.type) {
				case None:
					break;
				case TextBox:
					addRenderableWidget(new ManualTextBox((TextBoxComponent)base));
					break;
				case Image:
					addRenderableWidget(new ManualImage((ImageComponent)base));
					break;
				case Button:
					addRenderableWidget(new ManualButton((ButtonComponent)base));
					break;
				case Item:
					addRenderableWidget(new ManualItem((ItemComponent)base));
					break;
				case PageSelector:
					addRenderableWidget(new ManualPageSelector((PageSelector)base));
					break;
				case PageFlip:
					addRenderableWidget(new ManualFlipPage((FlipPageComponent)base));
					break;
				default:
					break;
			}
		}
		
		ChangeSizeButtonLocationUpdate();
	}
	
	public void ChangeSizeButtonLocationUpdate() {
		int posx = width / 2;
		int posy = height / 2;
		
		for (AbstractWidget widget : buttons) {
			WidgetBase data = null;
			if(widget instanceof ManualButton) {
				data = ((ManualButton)widget).getData();
			}else if(widget instanceof ManualComponent<?>) {
				data = ((ManualComponent<?>)widget).getData();
			}
			
			if(data != null) {
				widget.x = (posx + data.X);
				widget.y = (posy + data.Y);
			}
			
			if(widget instanceof ManualComponent<?>)
				((ManualComponent<?>)widget).updateOnSizeChanged();
		}
	}
	
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
//		init(); //Debugging command
		this.renderBackground(matrix);
		renderBg(matrix, partialTicks, mouseX, mouseY);
		super.render(matrix, mouseX, mouseY, partialTicks);
//		this.renderTooltip(matrix, mouseX, mouseY);
		renderFG(mouseX, mouseY);
	}
	
	@Override
	public boolean isPauseScreen() {
		return true;
	}
	
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		RenderUtils.bindTexture(TEXTURE);
		int i = (this.width - 475) / 2;
		int j = (this.height - 256) / 2;
		blit(matrix, i, j, getBlitOffset(), (float)0, (float)0, 475, 256, 512, 512);
		//this.blit(matrix, 5, 5, 0, 0, this.imageWidth, this.imageHeight);
		
	}
	
	protected void renderFG(int mouseX, int mouseY) {
//		int actualMouseX = mouseX - ((this.width - this.imageWidth) / 2);
//		int actualMouseY = mouseY - ((this.height - this.imageHeight) / 2);
	}
	
	@Override
	public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
		boolean click = false;//super.mouseClicked(p_97748_, p_97749_, p_97750_);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseClicked(p_97748_, p_97749_, p_97750_);
		return click;
	}
	
	@Override
	public boolean mouseDragged(double p_97752_, double p_97753_, int p_97754_, double p_97755_, double p_97756_) {
		boolean click = super.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
		return click;
	}
	
	@Override
	public void mouseMoved(double p_94758_, double p_94759_) {
		super.mouseMoved(p_94758_, p_94759_);
		for (AbstractWidget widget : buttons)
			widget.mouseMoved(p_94758_, p_94759_);
	}
	
	@Override
	public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
		boolean click = super.keyPressed(p_97765_, p_97766_, p_97767_);
		for (AbstractWidget widget : buttons)
			click = click || widget.keyPressed(p_97765_, p_97766_, p_97767_);
		return click;
	}
	
	@Override
	public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
		boolean click = super.keyReleased(p_94715_, p_94716_, p_94717_);
		for (AbstractWidget widget : buttons)
			click = click || widget.keyReleased(p_94715_, p_94716_, p_94717_);
		return click;
	}
	
	@Override
	public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
		boolean click = super.mouseReleased(p_97812_, p_97813_, p_97814_);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseReleased(p_97812_, p_97813_, p_97814_);
		return click;
	}
	
	@Override
	public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
		boolean click = super.mouseScrolled(p_94686_, p_94687_, p_94688_);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseScrolled(p_94686_, p_94687_, p_94688_);
		return click;
	}
	
}
