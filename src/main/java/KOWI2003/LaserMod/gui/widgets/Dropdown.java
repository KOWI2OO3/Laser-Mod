package KOWI2003.LaserMod.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class Dropdown extends Button {

	int posX, posY;
	int currentIndex = 0;
	
	List<Button> buttons;
	boolean extended;
	
	public Dropdown(int posX, int posY, int width, int height, String... list) {
		super(posX, posY, width, height, new TextComponent(""), (button) -> {
			((Dropdown)button).extended = !((Dropdown)button).extended;
		});
		this.posX = posX;
		this.posY = posY;
		setList(list);
	}
	
	public void setPos(int x, int y) {
		posX = x;
		posY = y;
		this.x = x;
		this.y = y;
		for (Button button : buttons) {
			button.x = posX;
			button.y = (buttons.indexOf(button)+1) * height + posY;
		}
	}
	
	@Override
	public Component getMessage() {
		return new TextComponent(getCurrentSelected());
	}
	
	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
		if(extended)
			for (Button button : buttons) {
				if(button.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_)) {
					extended = false;
					return true;
				}
			}
		if(extended) {
			extended = false;
			return false;
		}
		return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
	}
	
	@Override
	public boolean isMouseOver(double p_231047_1_, double p_231047_3_) {
		for (Button button : buttons) {
			button.changeFocus(button.isMouseOver(p_231047_1_, p_231047_3_));
		}
		return super.isMouseOver(p_231047_1_, p_231047_3_);
	}
	
	@Override
	public void renderButton(PoseStack matrix, int x, int y, float partialTicks) {
		super.renderButton(matrix, x, y, partialTicks);
		if(extended)
			for (Button button : buttons) {
				button.renderButton(matrix, x, y, partialTicks);
			}
	}
	
	public void setList(String... list) {
		buttons = new ArrayList<Button>();
		for(int i = 0; i < list.length; i++) {
			buttons.add(new Button(posX, (i+1) * height + posY, width, height, new TextComponent(list[i]), (button) ->  {
				currentIndex = this.buttons.indexOf(button);
			}));
		}
	}
	
	public void setList(List<String> list) {
		buttons = new ArrayList<Button>();
		for(int i = 0; i < list.size(); i++) {
			buttons.add(new Button(posX, (i+1) * height + posY, width, height, new TextComponent(list.get(i)), (button) ->  {
				currentIndex = this.buttons.indexOf(button);
			}));
		}
	}
	
	public String getAtIndex(int index) {
		if(buttons.size() > index)
			return buttons.get(index).getMessage().getString();
		return "";
	}
	
	public String getCurrentSelected() {
		return getAtIndex(currentIndex);
	}
	
	public void addElement(String element) {
		buttons.add(new Button(posX, (buttons.size()) * height + posY, width, height, new TextComponent(element), (button) ->  {
			currentIndex = this.buttons.indexOf(button);
		}));
	}
	
	public void removeElement(String element) {
		Button bttn = null;
		for (Button button : buttons) {
			if(button.getMessage().getString().equals(element)) {
				bttn = button;
				break;
			}
		}
		if(bttn != null)
			buttons.remove(bttn);
	}

}
