package KOWI2003.LaserMod.gui.manual.data;

import java.util.LinkedList;
import java.util.List;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.widget.FlipPageComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.FlipPageComponent.ButtonIconType;
import KOWI2003.LaserMod.gui.manual.data.widget.PageSelector;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuiContext {

	public int page = 0;
	
	String id;
	List<WidgetBase> components;
	
	GuiContext parent;
	
	String title;
	
	public GuiContext(String id) {
		this.id = id;
		this.components = new LinkedList<>();
	}
	
	public void init() {
		try {
			components.clear();
			
			if(ManualHandler.hasNext(page)) 
				addComponent(new FlipPageComponent("next", 100, 45, 20, 20, ManualHandler.getByPage(page+1).getId(), ButtonIconType.NextArrow));
			if(ManualHandler.hasPrev(page)) 
				addComponent(new FlipPageComponent("prev", -110, 45, 20, 20, ManualHandler.getByPage(page-1).getId(), ButtonIconType.PrevArrow));
			if(parent != null) 
				addComponent(new FlipPageComponent("toParent", -110, -60, 20, 20, parent.getId(), ButtonIconType.CompleteReturn));
			if(title != null && !title.isEmpty())
				addComponent(new TextBoxComponent("title", -5, -60, 300, 10, title + "", null, true));
			addComponent(new TextBoxComponent("pageNumber", -5, 50, 100, 10, page + "", null, true));
		}catch(Exception ex) {
			System.err.println(page + ": -> "  + (page +1) + " : " + (page-1));
			ex.printStackTrace();
		}

	}

	public void addComponent(WidgetBase component) {
		components.add(component);
	}
	
	public String getId() {
		return id;
	}
	
	public List<WidgetBase> getComponents() {
		return components;
	}
	
	public int getPage() {
		return page;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, String text) {
		addComponent(new PageSelector(text.replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				text, new float[] {}, new float[] {.6f, .6f, 1f}, false));
		offset += 10;
		return offset;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, String text, boolean centered) {
		addComponent(new PageSelector(text.replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				text, new float[] {}, new float[] {.6f, .6f, 1f}, centered));
		offset += 10;
		return offset;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, String text, ItemStack stack) {
		addComponent(new PageSelector(text.replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				text, stack, new float[] {}, new float[] {.6f, .6f, 1f}, false));
		offset += 10;
		return offset;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, ItemStack stack) {
		addComponent(new PageSelector(stack.getItem().getName(stack).getString().replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				stack.getItem().getName(stack).getString(), stack, new float[] {}, new float[] {.6f, .6f, 1f}, false));
		offset += 10;
		return offset;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, String text, ItemStack stack, boolean centered) {
		addComponent(new PageSelector(text.replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				text, stack, new float[] {}, new float[] {.6f, .6f, 1f}, centered));
		offset += 10;
		return offset;
	}
	
	public int addPageSelector(int x, int y, int offset, GuiContext gui, ItemStack stack, boolean centered) {
		addComponent(new PageSelector(stack.getItem().getName(stack).getString().replace(" ", "").toLowerCase(), x, offset+y, 20, 20, gui.getId(), 
				stack.getItem().getName(stack).getString(), stack, new float[] {}, new float[] {.6f, .6f, 1f}, centered));
		offset += 10;
		return offset;
	}
	
	public void setParent(GuiContext parent) {
		this.parent = parent;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTitle(ItemLike item) {
		setTitle(item.asItem().getName(item.asItem().getDefaultInstance()).getString());
	}
}
