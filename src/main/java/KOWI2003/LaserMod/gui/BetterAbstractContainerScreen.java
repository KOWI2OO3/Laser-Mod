package KOWI2003.LaserMod.gui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class BetterAbstractContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	public List<AbstractWidget> buttons;
	
	public BetterAbstractContainerScreen(T container, Inventory inv, Component title) {
		super(container, inv, title);
		buttons = new LinkedList<AbstractWidget>();
	}
	
	@Override
	protected void clearWidgets() {
		buttons.clear();
		super.clearWidgets();
	}
	
	
	@Override
	protected <K extends GuiEventListener & Renderable & NarratableEntry> K addRenderableWidget(K widget) {
		if(widget instanceof AbstractWidget) buttons.add((AbstractWidget)widget);
		return super.addRenderableWidget(widget);
	}
	
	@Override
	public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
		boolean click = super.mouseClicked(p_97748_, p_97749_, p_97750_);
		for (AbstractWidget widget : buttons)
			if(widget.isMouseOver(p_97748_, p_97749_))
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
			if(widget.isMouseOver(p_97812_, p_97813_))
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
