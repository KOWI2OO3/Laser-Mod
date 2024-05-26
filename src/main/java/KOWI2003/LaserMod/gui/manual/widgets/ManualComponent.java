package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class ManualComponent<T extends WidgetBase> extends AbstractWidget {

	T data;
	
	public ManualComponent(T data) {
		this(data.X, data.Y, data.Width, data.Height, data.Name);
		this.data = data;
	}
	
	private ManualComponent(int x, int y, int width, int heigth,
			Component title) {
		super(x, y, width, heigth, title);
	}
	
	private ManualComponent(int x, int y, int width, int heigth, String name) {
		this(x, y, width, heigth, new TextComponent(name));
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		if(data.backgroundColor != null) {
			float[] finalColor = Utils.parseColor(data.backgroundColor);
			RenderUtils.Gui.drawQuadColor(stack, getX(), getY(), width, height, finalColor[0], finalColor[1], finalColor[2]);
		}
		
		isHovered = isMouseOver(mouseX, mouseY);
		setFocused(false);
		
		renderComponent(stack, mouseX, mouseY);
		//renderToolTip(stack, mouseX, mouseY);
	}
	
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		
	}
	
	@Override
	public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
		if(data.Tooltip != null && !data.Tooltip.isEmpty() && isHovered) {
//			float[] color = new float[] {1, 1, 1, 1};
//			if(data.TooltipColor != null)
//				color = Utils.parseColor(data.TooltipColor);
//			String s = data.Tooltip;
		}
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaScroll) {
		return super.mouseScrolled(mouseX, mouseY, deltaScroll);
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		super.onClick(mouseX, mouseY);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int buttonId) {
		return super.mouseReleased(mouseX, mouseY, buttonId);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int buttonId) {
		if(isMouseOver(mouseX, mouseY)) {
			onClick(mouseX, mouseY);
			return true;
		}
		return false;
	}
	
	@Override
	public void mouseMoved(double deltaX, double deltaY) {
		super.mouseMoved(deltaX, deltaY);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int buttonId, double deltaX,
			double deltaY) {
		return super.mouseDragged(mouseX, mouseY, buttonId, deltaX, deltaY);
	}

	@Override
	public void updateNarration(NarrationElementOutput output) {}
	
	public T getData() {
		return data;
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
//		return super.isMouseOver(mouseX, mouseY);
		return this.active && this.visible && mouseX >= (double)getX() && mouseY >= (double)getY() && mouseX < (double)(getX() + getWidth()) && mouseY < (double)(getY() + getHeight());
	}
	
	public int getX() {
		return x + data.X;
	}
	
	public int getY() {
		return y + data.Y;
	}
	
//	public void updateComponent() {}
	public void updateOnSizeChanged() {}
}
