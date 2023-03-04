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
		setTitle("Laser Armor");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -50, -27, ModItems.LaserHelmet.get().getDefaultInstance(), 2));
		addComponent(new ItemComponent("item", -50, -12, ModItems.LaserChestplate.get().getDefaultInstance(), 3));
		addComponent(new ItemComponent("item", -50, 3, ModItems.LaserLeggings.get().getDefaultInstance(), 3));
		addComponent(new ItemComponent("item", -50, 17, ModItems.LaserBoots.get().getDefaultInstance(), 3));
		
		addComponent(new TextBoxComponent("info", -20, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "Special Armor created of an base plate which holds the laser crystal,"
				+ " and when activated acts like actual armor", null));
		addComponent(new TextBoxComponent("info", -20, -8, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This Armor can be upgraded using an modification station and upgrades", null));
		addComponent(new TextBoxComponent("info", -20, 10, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "To activate the armor, press '" + ModKeybindings.ArmorToggle.getKey().getDisplayName().getString() + "' while wearing it", null));

		addPageSelector(-40 + 33, 35, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -40, 35, 100, 0, null, "Also Check: ", null));
	}
}
