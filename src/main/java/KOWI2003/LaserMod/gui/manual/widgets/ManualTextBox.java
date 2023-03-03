package KOWI2003.LaserMod.gui.manual.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;

public class ManualTextBox extends ManualComponent<TextBoxComponent> {
	
	String[] lines;
	
	int offset = 6;
	
	public ManualTextBox(TextBoxComponent data) {
		super(data);
		updateLines();
		int height = Minecraft.getInstance().font.lineHeight * lines.length + offset*2;
		if(this.height < height)
			this.height = height;
		this.width += offset * 3;
	}
	
	@Override
	public void renderComponent(PoseStack stack, int mouseX, int mouseY) {
		if(data.Text != null && !data.Text.isEmpty()) {
			float[] color = Utils.parseColor(data.TextColor);
			MutableComponent s = MutableComponent.create(new LiteralContents(data.Text));
			Style sty = Style.EMPTY;
			sty.withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(color)));
			s.setStyle(sty);
			
			for (int i = 0; i < lines.length; i++) {
				if(!data.centred)
					drawString(stack, Minecraft.getInstance().font, lines[i], super.getX() + offset, super.getY() + offset + Minecraft.getInstance().font.lineHeight * i, Utils.getHexIntFromRGB(color));
				else
					drawCenteredString(stack, Minecraft.getInstance().font, lines[i], super.getX() + offset, super.getY() + offset + Minecraft.getInstance().font.lineHeight * i, Utils.getHexIntFromRGB(color));
			}
		}
	}
	
	@Override
	public void updateOnSizeChanged() {
		updateLines();
	}
	
	@Override
	public int getX() {
		return data.centred ? (super.getX() - super.getWidth()/2) : super.getX();
	}
	
	public void updateLines() {
		if(data.Text != null && !data.Text.isEmpty())
			if(Minecraft.getInstance().font.width(data.Text) > data.Width-offset*2) {
				if(width > 10)
					lines = Utils.splitToFitWidth(data.Text.replace("\n", "").replace("\r", ""), data.Width-offset*2);
				else lines = new String[0];
			}else
				lines = new String[] {data.Text};
	}
}
