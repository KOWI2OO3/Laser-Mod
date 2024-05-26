package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextBoxData;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;

public class ProjectorTextBox extends ProjectorWidget {

	public String[] lines;
	public String text;
	public float[] textColor;
	
	public boolean isCentered;
	
	int offset = 6;
	
	public ProjectorTextBox(ProjectorTextBoxData data) {
		super(data);
		this.text = data.text;
		this.isCentered = data.isCentered;
		updateLines();
		int height = Minecraft.getInstance().font.lineHeight * lines.length + offset*2;
		if(this.height < height)
			this.height = height;
		this.width += offset * 3;
		this.textColor = data.textColor;
	}
	
	public ProjectorTextBox(float x, float y, float width, String text, float[] textColor, boolean isCentered) {
		super(x, y, width, 1);
		this.text = text;
		this.isCentered = isCentered;
		updateLines();
		int height = Minecraft.getInstance().font.lineHeight * lines.length + offset*2;
		if(this.height < height)
			this.height = height;
		this.width += offset * 3;
		this.textColor = textColor;
	}
	
	

	public ProjectorTextBox(float x, float y, float z, float width, String text,
			float[] textColor, boolean isCentered) {
		super(x, y, z, width, 1, 1);
		this.text = text;
		this.isCentered = isCentered;
		updateLines();
		int height = Minecraft.getInstance().font.lineHeight * lines.length + offset*2;
		if(this.height < height)
			this.height = height;
		this.width += offset * 3;
		this.textColor = textColor;
	}

	@Override
	public void renderWidget(RenderContext<?> context) {
		if(text != null && !text.isEmpty()) {
			float[] color = Utils.parseColor(textColor);
			TextComponent s = new TextComponent(text);
			Style sty = Style.EMPTY;
			sty.withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(color)));
			s.setStyle(sty);
			
			for (int i = 0; i < lines.length; i++) {
				if(!isCentered)
					RenderUtils.renderString(context.getMatrix(), lines[i], super.getX() + offset, super.getY() + offset + Minecraft.getInstance().font.lineHeight * i, Utils.getHexIntFromRGB(color));
				else {
					RenderUtils.renderString(context.getMatrix(), lines[i], super.getX() + offset - Minecraft.getInstance().font.width(lines[i])/2f,
							super.getY() + offset + Minecraft.getInstance().font.lineHeight * i, Utils.getHexIntFromRGB(color));
				}
			}
		}
	}

	@Override
	public float getX() {
		return isCentered ? (super.getX() - super.getWidth()/2) : super.getX();
	}
	
	public void updateLines() {
		if(text != null && !text.isEmpty())
			if(Minecraft.getInstance().font.width(text) > width-offset*2) {
				if(width > 10)
					lines = Utils.splitToFitWidth(text.replace("\n", "").replace("\r", ""), (int) (width-offset*2));
				else lines = new String[0];
			}else
				lines = new String[] {text};
	}
	
}
