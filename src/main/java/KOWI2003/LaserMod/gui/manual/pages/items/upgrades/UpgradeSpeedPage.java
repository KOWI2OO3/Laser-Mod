package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeSpeedPage extends GuiContext {
	
	public UpgradeSpeedPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Speed.get().getName(ModUpgrades.Speed.get().getDefaultInstance()).getString());
		
		addComponent(new ItemComponent("item", -70, -43, ModUpgrades.Speed.get().getDefaultInstance(), 5));
		addComponent(new TextBoxComponent("text", -50, 36, 100, 60, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet nisl suscipit adipiscing bibendum est. Nascetur ridiculus mus mauris vitae ultricies leo integer. Viverra orci sagittis eu volutpat odio facilisis. Faucibus interdum posuere lorem ipsum dolor sit amet consectetur. Congue quisque egestas diam in arcu cursus euismod quis viverra. Ultrices sagittis orci a scelerisque purus semper eget. Dis parturient montes nascetur ridiculus. Ante in nibh mauris cursus mattis molestie a iaculis at. Morbi tristique senectus et netus. Vestibulum morbi blandit cursus risus at ultrices. Fusce ut placerat orci nulla pellentesque dignissim. Condimentum id venenatis a condimentum vitae. Nibh mauris cursus mattis molestie a iaculis at erat pellentesque. Urna molestie at elementum eu facilisis sed odio morbi.\r\n"
				+ "\r\n"
				+ "At imperdiet dui accumsan sit amet nulla facilisi morbi tempus. Ac orci phasellus egestas tellus. Gravida arcu ac tortor dignissim convallis aenean. Tellus at urna condimentum mattis. Ut pharetra sit amet aliquam. Diam ut venenatis tellus in metus. Habitant morbi tristique senectus et netus et malesuada fames. Lacus laoreet non curabitur gravida. Sed vulputate odio ut enim blandit volutpat maecenas volutpat. Tempor nec feugiat nisl pretium. Sem fringilla ut morbi tincidunt augue interdum velit. Maecenas volutpat blandit aliquam etiam. Non quam lacus suspendisse faucibus interdum posuere. At varius vel pharetra vel turpis nunc. Tempor nec feugiat nisl pretium fusce id velit. Ultrices in iaculis nunc sed augue lacus viverra vitae congue. Sagittis eu volutpat odio facilisis mauris sit amet massa. Diam volutpat commodo sed egestas egestas fringilla. Id leo in vitae turpis massa sed elementum tempus.\r\n", null));
	}
}
