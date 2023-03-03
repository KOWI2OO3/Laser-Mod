package KOWI2003.LaserMod.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.network.PacketDataChanged;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorPlayerData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;

public class PartSelector extends AbstractWidget {

	public static final ResourceLocation WIDGETS = new ResourceLocation(Reference.MODID,
			"textures/gui/laser_projector/widgets.png");
	
	int offset;
	
	final List<Button> buttons = new ArrayList<>();
	
	TileEntityLaserProjector te;
	
	public PartSelector(int x, int y, int width, int height, TileEntityLaserProjector te) {
		super(x, y, width, height, MutableComponent.create(new LiteralContents("parts")));
		this.te = te;
		init();
	}
	
	public void init() {
		buttons.clear();
		
		int yOff = 0;
		yOff = addButton(82, 83, yOff, (button) -> {
			PacketHandler.sendToServer(new PacketDataChanged(te, new ProjectorTextData(0, 0, 0, "Unknown")));
		});
		yOff = addButton(102, 83, yOff, (button) -> {
			PacketHandler.sendToServer(new PacketDataChanged(te, new ProjectorItemData()));});
		yOff = addButton(122, 83, yOff, (button) -> {
			PacketHandler.sendToServer(new PacketDataChanged(te, new ProjectorPlayerData()));});
		yOff = addButton(82, 123, yOff, (button) -> {
			PacketHandler.sendToServer(new PacketDataChanged(te, new ProjectorShapeData()));});
	}
	
	public int addButton(int uvX, int uvY, int offset, OnPress action) {
		int padding = 2;
		ButtonTexture button = new ButtonTexture(buttons.size() % 3 * (20 + padding) + padding, offset + padding, uvX, uvY, 20, 20, MutableComponent.create(new LiteralContents(" ")), action, WIDGETS);
		buttons.add(button);
		return offset += (button.getHeight() + padding) * (buttons.size() % 3 > 0 ? 0 : 1);
	}
	
	public int addButton(int offset, Button button) {
		buttons.add(button);
		return offset += button.getHeight();
	}

	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		float tint = 0.4f;
		RenderUtils.setupStencil();
		RenderUtils.Gui.drawQuadColor(matrix, x, y, width, height, tint, tint, tint);
		RenderUtils.setupRenderInside();

		matrix.pushPose();
		matrix.translate(x, y + offset, 0);
		for (Button widget : buttons) {
			widget.render(matrix, mouseX - x, mouseY - y - offset, partialTicks);
		}
		matrix.popPose();
		
		RenderUtils.disableStencil();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean click = false;
		if(isMouseOver(mouseX, mouseY)) {
			for (Button widget : buttons) {
				click = click || widget.mouseClicked(mouseX - x, mouseY - y - offset, button);
			}
		}
		return click;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		boolean click = false;
		for (Button widget : buttons) {
			click = click || widget.mouseReleased(mouseX - x, mouseY - y - offset, button);
		}
		return click;
	}
	
	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {}
	
}
