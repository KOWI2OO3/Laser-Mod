package KOWI2003.LaserMod.gui.manual.widgets;

import KOWI2003.LaserMod.gui.manual.data.widget.ButtonComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.IButtonAction;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ManualButton extends Button {

	ButtonComponent data;
	IButtonAction action;
	
	public ManualButton(ButtonComponent data) {
		this(data.X, data.Y, data.Width, data.Height, data.Name, data.action);
		this.data = data;
	}
	
	private ManualButton(int x, int y, int width, int heigth,
			Component title, IButtonAction action) {
		super(x, y, width, heigth, title, (button) -> {});
		this.action = action;
	}
	
	private ManualButton(int x, int y, int width, int heigth, String name, IButtonAction action) {
		this(x, y, width, heigth, MutableComponent.create(new TranslatableContents(name)), action);
	}
	
	public void onClick(double mouseX, double mouseY) {
		this.action.run(data);
	}
	
	public ButtonComponent getData() {
		return data;
	}
}
