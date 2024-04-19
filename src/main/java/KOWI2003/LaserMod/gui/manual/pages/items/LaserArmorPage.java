package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.init.ModKeybindings;

public class LaserArmorPage extends GuiContext {

	public LaserArmorPage(String id) {
		super(id);
		setTitle("manual.items.category.armor");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -50, -27, ModItems.LaserHelmet.get().getDefaultInstance(), 2));
		addComponent(new ItemComponent("item", -50, -12, ModItems.LaserChestplate.get().getDefaultInstance(), 3));
		addComponent(new ItemComponent("item", -50, 3, ModItems.LaserLeggings.get().getDefaultInstance(), 3));
		addComponent(new ItemComponent("item", -50, 17, ModItems.LaserBoots.get().getDefaultInstance(), 3));
		
		addComponent(new TextBoxComponent("info", -20, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.armor.info.base", null));
		addComponent(new TextBoxComponent("info", -20, -8, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.armor.info.extra", null));
		addComponent(new TextBoxComponent("info", -20, 10, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.armor.info.usage", null).withArgument(() -> ModKeybindings.ArmorToggle.getKey().getDisplayName().getString()));

		addPageSelector(-40 + width("manual.misc.see") / 2 + 3, 35, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -40, 35, 100, 0, null, "manual.misc.see", null));
	}
}
