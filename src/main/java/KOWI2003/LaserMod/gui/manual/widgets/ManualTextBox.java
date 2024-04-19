package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ManualTextBox extends ManualComponent<TextBoxComponent> {
	
	String[] lines;
	String msg;
	
	int offset = 6;
	
	public ManualTextBox(TextBoxComponent data) {
		super(data);
		this.msg = data.Text;
		int height = (Minecraft.getInstance().font.split(getComponent(), width).size() + 1) * Minecraft.getInstance().font.lineHeight;
		if(this.height < height)
			this.height = height;
		this.width += offset * 3;
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		if(data.Text != null && !data.Text.isEmpty()) {
			float[] color = Utils.parseColor(data.TextColor);
			MutableComponent s = MutableComponent.create(new TranslatableContents(data.Text));
			Style sty = Style.EMPTY;
			sty.withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(color)));
			s.setStyle(sty);

			Component component = getComponent();
			int localOffset = 0;
			if(data.centred)
				localOffset = (int)Math.min(width/2f, Minecraft.getInstance().font.width(component) / 2f);

			Minecraft.getInstance().font.drawWordWrap(component, super.getX() + offset - localOffset, super.getY() + offset, width - 5, Utils.getHexIntFromRGB(color));
		}
	}
	
	
	@Override
	public void updateOnSizeChanged() {
		int height = (Minecraft.getInstance().font.split(getComponent(), width).size() + 1) * Minecraft.getInstance().font.lineHeight;
		if(this.height < height)
			this.height = height;
	}

	private Component getComponent() {
		return data.arguments != null && data.arguments.size() > 0 ? Component.translatable(msg, data.getArguments()) : Component.translatable(msg);
	}
	
	@Override
	public int getX() {
		return data.centred ? (super.getX() - super.getWidth()/2) : super.getX();
	}
}
