package KOWI2003.LaserMod.gui.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.GuiLaserProjector;
import KOWI2003.LaserMod.gui.widgets.properties.BoolProperty;
import KOWI2003.LaserMod.gui.widgets.properties.ColorProperty;
import KOWI2003.LaserMod.gui.widgets.properties.DataProperty;
import KOWI2003.LaserMod.gui.widgets.properties.DoubleProperty;
import KOWI2003.LaserMod.gui.widgets.properties.EnumProperty;
import KOWI2003.LaserMod.gui.widgets.properties.FloatProperty;
import KOWI2003.LaserMod.gui.widgets.properties.IntProperty;
import KOWI2003.LaserMod.gui.widgets.properties.ItemProperty;
import KOWI2003.LaserMod.gui.widgets.properties.StringProperty;
import KOWI2003.LaserMod.network.PacketDataChanged;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.tileentities.projector.gui.ProjectorGui;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.item.ItemStack;

public class DataProperties extends AbstractWidget {

	ProjectorWidgetData data;
	List<DataProperty<?>> properties;
	
	int totalHeight = 0;
	
	int offset = 0;
	
	GuiLaserProjector guiInstance;
	
	public DataProperties(int x, int y, int width, int height, String name, ProjectorWidgetData data) {
		super(x, y, width, height, MutableComponent.create(new LiteralContents(name)));
		properties = new ArrayList<>();
		setData(data);
	}
	
	public DataProperties(int x, int y, int width, int height, String name, ProjectorWidgetData data, GuiLaserProjector gui) {
		super(x, y, width, height, MutableComponent.create(new LiteralContents(name)));
		properties = new ArrayList<>();
		this.guiInstance = gui;
		setData(data);
	}
	
	public void updateTotalHeight() {
		totalHeight = 0;
		for (DataProperty<?> property : properties)
			totalHeight += property.getHeight();
	}
	
	public void setData(ProjectorWidgetData data) {
		this.data = data;
		offset = 0;
		properties.clear();
		if(data == null) return;
		int y = 0;
		
		try {
			y = addProperty(y, data.getClass().getField("x"), data);
			y = addProperty(y, data.getClass().getField("y"), data);
			y = addProperty(y, data.getClass().getField("z"), data);
			if(ProjectorGui.getWidget(data).isScalable()) {
				y = addProperty(y, data.getClass().getField("width"), data);
				y = addProperty(y, data.getClass().getField("height"), data);
				y = addProperty(y, data.getClass().getField("depth"), data);
			}else if(ProjectorGui.getWidget(data).isRectangularScalable())
				y = addProperty(y, data.getClass().getField("scale"), data);

			y = addProperty(y, data.getClass().getField("rotation"), data);
			
			if(ProjectorGui.getWidget(data).hasAlpha())
				y = addProperty(y, data.getClass().getField("alpha"), data);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		for(Field field : data.getClass().getFields()) {
			if(field.isAnnotationPresent(SerializeProperty.class)) {
				y = addProperty(y, field, data);
			}
		}
		updateTotalHeight();
	}
	
	int addProperty(int yOffset, Field field, ProjectorWidgetData data) {
		float min = -1;
		float max = -1;
		if(field.isAnnotationPresent(RangeProperty.class)) {
			RangeProperty range = field.getAnnotation(RangeProperty.class);
			min = range.min();
			max = range.max();
		}
		
		DataProperty<?> property = getProperty(0, yOffset, this.width, 20, field, data, min, max);
		if(property != null) {
			properties.add(property);
			yOffset += property.getHeight();
		}
		return yOffset;
	}
	
	int addProperty(int yOffset, DataProperty<?> property) {
		if(property != null) {
			properties.add(property);
			yOffset += property.getHeight();
		}
		return yOffset;
	}
	
	@Override
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		float tint = 0.4f;
		RenderUtils.setupStencil();
		RenderUtils.Gui.drawQuadColor(matrix, x, y, width, height, tint, tint, tint);
		RenderUtils.setupRenderInside();
		
		isMouseOver(mouseX, mouseY);
		
		matrix.pushPose();
		matrix.translate(x, y + offset, 0);
		for (DataProperty<?> property : properties) {
			property.render(matrix, mouseX - x, mouseY - y - offset, partialTicks);
		}
		matrix.popPose();
		
		RenderUtils.disableStencil();
	}
	
	public boolean hasAnyChanged() {
		for (DataProperty<?> property : properties) {
			if(property.hasChanged())
				return true;
		}
		return false;
	}
	
	DataProperty<?> getProperty(int x, int y, int width, int height, Field field, ProjectorWidgetData data, float min, float max) {
		try {
			if(field.getType() == int.class)
				return new IntProperty(x, y, width, height, field.getName(), (int) field.get(data));
			else if(field.getType() == String.class)
				return new StringProperty(x, y, width, height, field.getName(), (String) field.get(data));
			else if(field.getType() == double.class)
				return new DoubleProperty(x, y, width, height, field.getName(), (double) field.get(data), min, max);
			else if(field.getType() == float.class)
				return new FloatProperty(x, y, width, height, field.getName(), (float) field.get(data), min, max);
			else if(field.getType() == boolean.class)
				return new BoolProperty(x, y, width, height, field.getName(), (boolean) field.get(data));
			else if(field.getType() == float[].class)
				return new ColorProperty(x, y, width, height, field.getName(), (float[]) field.get(data));
			else if(field.getType().isEnum())
				return new EnumProperty(x, y, width, height, field.getName(), (Enum<?>) field.get(data));
			else if(field.getType() == ItemStack.class)
				return new ItemProperty(x, y, width, height, field.getName(), (ItemStack) field.get(data), guiInstance);
			else
				return new DataProperty<>(x, y, width, height, field.getName(), field.get(data));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateValues() {
		try {
			for (DataProperty<?> property : properties) {
				property.setValue(data.getClass().getField(property.getFieldName()).get(data));
			}
		}catch(Exception ex) {}
	}
	
	public void syncData(TileEntityLaserProjector tileentity) {
		for (DataProperty<?> property : properties)
			property.sync(data);
		PacketHandler.sendToServer(new PacketDataChanged(tileentity, data));
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if(super.isMouseOver(mouseX, mouseY)) {
			for (DataProperty<?> property : properties) {
				property.setHoveredOver(property.isMouseOver(mouseX - x, mouseY - y - offset));
			}
		}else
			for (DataProperty<?> property : properties) {
				property.setHoveredOver(false);
			}
		return super.isMouseOver(mouseX, mouseY);
	}
	
	@Override
	public boolean changeFocus(boolean value) {
		for (DataProperty<?> property : properties) {
			property.changeFocus(value);
		}
		return super.changeFocus(value);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		boolean check = false;
		for (DataProperty<?> property : properties) {
			if(!property.isMouseOver(mouseX - x, mouseY - y - offset))
				property.changeFocus(false);
			check = property.mouseClicked(mouseX - x, mouseY - y - offset, button) || check;
		}
		return check || super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean check = false;
		if(super.isMouseOver(mouseX, mouseY)) {
			for (DataProperty<?> property : properties) {
				if(!property.isMouseOver(mouseX - x, mouseY - y - offset))
					property.changeFocus(false);
				check = property.mouseClicked(mouseX - x, mouseY - y - offset, button) || check;
			}
		}else
			changeFocus(false);
		return check;
	}
	
	@Override
	public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
		boolean check = false;
		for (DataProperty<?> property : properties) 
			check = property.keyPressed(p_94745_, p_94746_, p_94747_)  || check;

		return check;
	}
	
	@Override
	public boolean keyReleased(int p_94750_, int p_94751_, int p_94752_) {
		boolean check = false;
		for (DataProperty<?> property : properties) 
			check = property.keyReleased(p_94750_, p_94751_, p_94752_) || check;

		return check;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		boolean check = false;
		for (DataProperty<?> property : properties) 
			check = property.mouseReleased(mouseX - x, mouseY - y - offset, button) || check;
		
		return check;
	}
	
	@Override
	public void mouseMoved(double deltaX, double deltaY) {
		super.mouseMoved(deltaX, deltaY);
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaScroll) {
		if(super.isMouseOver(mouseX, mouseY) && totalHeight > height) {
			offset += deltaScroll * 5;
			offset = Math.min(0, Math.max(-(totalHeight - height), offset));
		}
		return super.isMouseOver(mouseX, mouseY);
	}
	
	@Override
	public boolean charTyped(char p_94732_, int p_94733_) {
		boolean check = false;
		for (DataProperty<?> property : properties)
			check = property.charTyped(p_94732_, p_94733_) || check;

		return check;
	}

	public void updateNarration(@Nonnull NarrationElementOutput p_169152_) {}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	@DefaultQualifierInHierarchy
	public static @interface SerializeProperty {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	@DefaultQualifierInHierarchy
	public static @interface RangeProperty {
		float min();
		float max();
	}
}
