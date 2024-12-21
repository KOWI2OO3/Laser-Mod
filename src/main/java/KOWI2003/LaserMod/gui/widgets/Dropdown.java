package KOWI2003.LaserMod.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;

public class Dropdown extends Button {

	int posX, posY;
	int currentIndex = 0;
	
	List<Button> buttons;
	boolean extended;
	
	public Dropdown(int posX, int posY, int width, int height, String... list) {
		super(posX, posY, width, height, MutableComponent.create(new LiteralContents("")), (button) -> {
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
		return MutableComponent.create(new LiteralContents(getCurrentSelected()));
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if(extended)
			for (Button button : buttons) {
				if(button.mouseClicked(mouseX, mouseY, mouseButton)) {
					extended = false;
					return true;
				}
			}
		if(extended) {
			extended = false;
			return false;
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		for (Button button : buttons) {
			button.changeFocus(button.isMouseOver(mouseX, mouseY));
		}
		return super.isMouseOver(mouseX, mouseY);
	}
	
	@Override
	public void renderButton(@Nonnull PoseStack matrix, int x, int y, float partialTicks) {
		super.renderButton(matrix, x, y, partialTicks);
		if(extended)
			for (Button button : buttons) {
				button.renderButton(matrix, x, y, partialTicks);
			}
	}
	
	public void setList(String... list) {
		buttons = new ArrayList<Button>();
		for(int i = 0; i < list.length; i++) {
			buttons.add(new Button(posX, (i+1) * height + posY, width, height, MutableComponent.create(new LiteralContents(list[i])), (button) ->  {
				currentIndex = this.buttons.indexOf(button);
			}));
		}
	}
	
	public void setList(List<String> list) {
		buttons = new ArrayList<Button>();
		for(int i = 0; i < list.size(); i++) {
			buttons.add(new Button(posX, (i+1) * height + posY, width, height, MutableComponent.create(new LiteralContents(list.get(i))), (button) ->  {
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
		buttons.add(new Button(posX, (buttons.size()) * height + posY, width, height, MutableComponent.create(new LiteralContents(element)), (button) ->  {
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
