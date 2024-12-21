package KOWI2003.LaserMod.gui.widgets.properties;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.EnumUtils;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraftforge.client.gui.ScreenUtils;

public class EnumProperty extends DataProperty<Enum<?>> {

	Button Next;
	Button Prev;
	
	final List<Enum<?>> enumList;
	
	@SuppressWarnings("unchecked")
	public EnumProperty(int x, int y, int width, int height, String displayName, Enum<?> value) {
		super(x, y, width, height, displayName, value);
		
		enumList = EnumUtils.getEnumList(value.getClass());
		
		Prev = new Button(x + 35, y, 10, height, MutableComponent.create(new LiteralContents("<")), (button) -> {
			int nextIndex = this.value.ordinal() - 1;
			if(nextIndex < 0)
				nextIndex = enumList.size() - 1;
			setValue(enumList.get(nextIndex));
			setHasChanged();});
		
		Next = new Button(Prev.x + 45, y, 10, height, MutableComponent.create(new LiteralContents(">")), (button) -> {
			int nextIndex = this.value.ordinal() + 1;
			if(nextIndex >= enumList.size())
				nextIndex = 0;
			setValue(enumList.get(nextIndex));
			setHasChanged();
		});
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        ScreenUtils.blitWithBorder(matrix, WIDGETS_LOCATION, this.x + 44, this.y, 0, 46, 37, 20, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
        
		Next.render(matrix, mouseX, mouseY, partialTicks);
		Prev.render(matrix, mouseX, mouseY, partialTicks);
		
		Font font = Minecraft.getInstance().font;
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 35f, 10, 0xffffff);
		
		matrix.pushPose();
		matrix.translate(x + 45, y + font.lineHeight/2 + 2, 0);
		RenderUtils.Gui.drawCenteredStringWithinLine(matrix, getValue().toString(), 0, 0, 35f, 0xffffff);
		matrix.popPose();

	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean check = Next.mouseClicked(mouseX, mouseY, button);
		check = Prev.mouseClicked(mouseX, mouseY, button) || check;
		return check;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		boolean check = Next.mouseReleased(mouseX, mouseY, button);
		check = Prev.mouseReleased(mouseX, mouseY, button) || check;
		return check;
	}

}
