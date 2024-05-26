package KOWI2003.LaserMod.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import KOWI2003.LaserMod.gui.manual.widgets.ManualButton;
import KOWI2003.LaserMod.gui.manual.widgets.ManualComponent;
import KOWI2003.LaserMod.gui.widgets.ButtonTexture;
import KOWI2003.LaserMod.gui.widgets.DataProperties;
import KOWI2003.LaserMod.gui.widgets.PartSelector;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.network.PacketDataChanged;
import KOWI2003.LaserMod.network.PacketDataRemoved;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketTemplateProjector;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.ProjectorTemplates;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.tileentities.projector.gui.ProjectorGui;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorWidget;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;

public class GuiLaserProjector extends Screen {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/laser_projector/window.png");
	public static final ResourceLocation WIDGETS = new ResourceLocation(Reference.MODID,
			"textures/gui/laser_projector/widgets.png");
	
	public static final int WINDOW_WIDTH = 252;
	public static final int WINDOW_HEIGHT = 172;
	private static final int WINDOW_INSIDE_X = 9;
	private static final int WINDOW_INSIDE_Y = 18;
	public static final int WINDOW_INSIDE_WIDTH = 234;
	public static final int WINDOW_INSIDE_HEIGHT = 145;
	private static final int WINDOW_TITLE_X = 8;
	private static final int WINDOW_TITLE_Y = 6;
	private static final Component TITLE = new TranslatableComponent("block.lasermod.laser_projector");
	
	float x = 0, y = 0, z = 0;
	float rotX = 0, rotY = 0;
	float scale = 0.4f;
	static boolean useGrid = true;
	static boolean showSurroudings = false;
	int surroudingRange = 1;
	
	List<AbstractWidget> buttons;
		
	public TileEntityLaserProjector te;
	
	public static boolean is3D = false;
	
	ProjectorWidgetData selectedWidget = null;
	ProjectorWidgetData hoveringWidget = null;
	boolean isMovingWidget = false;
//	ProjectorWidgetData movingWidget = null;
	
	public Button nextTemplate;
	public Button prevTemplate;
	
	public ButtonTexture gridToggle;
	public ButtonTexture toggle3D;
	public ButtonTexture centreView;
	public ButtonTexture toggleSurrounding;
	
	public DataProperties properties;
	public PartSelector parts;
	
	public static boolean debug = false;
	
	public GuiLaserProjector(TileEntityLaserProjector te) {
		super(new TextComponent("Null"));
		this.te = te;
		buttons = new ArrayList<>();
		this.minecraft = Minecraft.getInstance();
		centreView();
		
		nextTemplate = new Button(0, 0, 20, 20, new TextComponent(">"), (button) -> {
			int index = te.template.ordinal();
			index++;
			if(index >= ProjectorTemplates.values().length)
				index = 0;
			PacketHandler.sendToServer(new PacketTemplateProjector(te.getBlockPos(), ProjectorTemplates.values()[index]));
			setSelectedWidget(null);
			hoveringWidget = null;
		});
		
		prevTemplate = new Button(0, 0, 20, 20, new TextComponent("<"), (button) -> {
			int index = te.template.ordinal();
			index--;
			if(index < 0)
				index = ProjectorTemplates.values().length-1;
			PacketHandler.sendToServer(new PacketTemplateProjector(te.getBlockPos(), ProjectorTemplates.values()[index]));
			setSelectedWidget(null);
			hoveringWidget = null;
		});

		
		
		gridToggle = new ButtonTexture(0, 0, 103, 0, 20, 20, new TextComponent(" "), (button) -> {
			useGrid = !useGrid;
		}, WIDGETS);
		toggle3D = new ButtonTexture(0, 0, is3D ? 82 : 102, is3D ? 41 : 42, 20, 20, new TextComponent(" "), (button) -> {
			is3D = !is3D;
			if(!is3D) {
				rotX = 0;
				rotY = 0;
			}
			toggle3D.uvX = is3D ? 82 : 102;
			toggle3D.uvY = is3D ? 21 : 22;
		}, WIDGETS);
		centreView = new ButtonTexture(0, 0, 124, 0, 20, 20, new TextComponent(" "), (button) -> {
			centreView();
		}, WIDGETS);
		toggleSurrounding = new ButtonTexture(0, 0, !showSurroudings ? 102 : 122, 123, 20, 20, new TextComponent(" "), (button) -> {
			showSurroudings = !showSurroudings;
			toggleSurrounding.uvX = !showSurroudings ? 102 : 122;
			toggleSurrounding.uvY = !showSurroudings ? 103 : 103;
		}, WIDGETS);
		
		properties = new DataProperties(0, 0, 92, 160, "properties", selectedWidget, this);
		parts = new PartSelector(0, 0, 70, 160, te);
	}
	
	public void centreView() {
		x = 0;
		y = -40;
		z = 0;
		rotX = 0;
		rotY = 0;
	}
	
	@Override
	protected void clearWidgets() {
		buttons.clear();
		super.clearWidgets();
	}
	
	@Override
	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
		if(widget instanceof AbstractWidget) buttons.add((AbstractWidget)widget);
		return super.addRenderableWidget(widget);
	}
	
	protected void init() {
		super.init();
	      
	    clearWidgets();
	    
	    addRenderableWidget(nextTemplate);
	    addRenderableWidget(prevTemplate);
	    
	    addRenderableWidget(gridToggle);
	    addRenderableWidget(toggle3D);
	    addRenderableWidget(centreView);
	    addRenderableWidget(toggleSurrounding);
	    
	    addRenderableWidget(properties);
	    addRenderableWidget(parts);
	    
	    ChangeSizeButtonLocationUpdate();
	}
	
	public void ChangeSizeButtonLocationUpdate() {
		int posx = width / 2;
		int posy = height / 2;
		
		nextTemplate.x = posx+55;
		nextTemplate.y = posy-112;
		
		prevTemplate.x = posx-72;
		prevTemplate.y = posy-112;
		
		gridToggle.x = posx + 38;
		gridToggle.y = posy + 91;
		
		toggle3D.x = gridToggle.x + 30;
		toggle3D.y = gridToggle.y;
		
		toggleSurrounding.x = toggle3D.x + 30;
		toggleSurrounding.y = toggle3D.y;

		centreView.x = gridToggle.x - 30;
		centreView.y = gridToggle.y;
		
		properties.x = posx + 128;
		properties.y = posy - 80;
		
		parts.x = posx - 200;
		parts.y = posy - 80;
		
		for (AbstractWidget widget : buttons) {
			WidgetBase data = null;
			if(widget instanceof ManualButton) {
				data = ((ManualButton)widget).getData();
			}else if(widget instanceof ManualComponent<?>) {
				data = ((ManualComponent<?>)widget).getData();
			}
			if(data != null) {
				widget.x = posx + data.X;
				widget.y = posy + data.Y;
			}
			
			if(widget instanceof ManualComponent<?>)
				((ManualComponent<?>)widget).updateOnSizeChanged();
		}
	}
	
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		ChangeSizeButtonLocationUpdate();
		this.renderBackground(matrix);
		renderBg(matrix, partialTicks, mouseX, mouseY);
		super.render(matrix, mouseX, mouseY, partialTicks);
		renderFG(mouseX, mouseY);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
		RenderUtils.bindTexture(TEXTURE);
		int i = (this.width - WINDOW_WIDTH) / 2;
	    int j = (this.height - WINDOW_HEIGHT) / 2;
		//this.blit(matrix, 5, 5, 0, 0, this.imageWidth, this.imageHeight);
	    renderInside(matrix, partialTicks, mouseX, mouseY, 	i, j);
	    renderWindow(matrix, i, j);
		renderAdditions(matrix, partialTicks, mouseX, mouseY, i, j);
		
		handleRemove();
	}

	protected void renderAdditions(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		RenderUtils.bindTexture(WIDGETS);
		this.blit(matrix, guiLeft + WINDOW_WIDTH, guiTop, 158, 0, 98, WINDOW_HEIGHT);
		this.blit(matrix, guiLeft, guiTop - 33, 0, 222, WINDOW_WIDTH, 33);
		this.blit(matrix, guiLeft - 80, guiTop, 0, 0, 80, WINDOW_HEIGHT);
		
		this.blit(matrix, guiLeft + WINDOW_WIDTH - 126, guiTop + WINDOW_HEIGHT, 0, 173, 126, 31);
		
		renderTemplateSelector(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
		renderPartsSelector(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
		if(selectedWidget != null)
			renderProperties(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
		if(debug)
			renderDebug(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
	}
	
	protected void renderProperties(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		if(properties.hasAnyChanged()) {
			properties.syncData(te);
		}
	}
	
	protected void renderTemplateSelector(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		RenderUtils.bindTexture(WIDGETS);
		this.blit(matrix, guiLeft + WINDOW_WIDTH/2 - 109/2+1, guiTop - 33/2 - 10, 140, 172, 109, 20);
		drawCenteredString(matrix, font, te.template.name(), guiLeft + WINDOW_WIDTH/2, guiTop - 20, 0xffffff);
	}
	
	protected void renderPartsSelector(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		
	}
	
	protected void renderDebug(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		Window win = Minecraft.getInstance().getWindow();
		float minX = (hitbox[0] / 2 + 0.5f) * win.getGuiScaledWidth();
		float minY = (hitbox[1] / 2 + 0.5f) * win.getGuiScaledHeight();
		float maxX = (hitbox[2] / 2 + 0.5f) * win.getGuiScaledWidth();
		float maxY = (hitbox[3] / 2 + 0.5f) * win.getGuiScaledHeight();
		
		RenderUtils.Gui.drawOutline(matrix, minX, minY, maxX - minX, maxY - minY, 0, 0, 1);
		
		float size = 10f;
		float centreX = (minX + maxX)/2f;
		float centreY = (minY + maxY)/2f;
		RenderUtils.Gui.drawOutline(matrix, centreX-size/2f, centreY-size/2f, size, size, 0, 0, 1);
	}
	
	protected void renderInside(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		RenderUtils.setupStencil();
		{
			RenderUtils.Gui.drawQuadColor(matrix, new float[] {WINDOW_INSIDE_X + guiLeft, WINDOW_INSIDE_Y + guiTop}, new float[] {WINDOW_INSIDE_WIDTH, WINDOW_INSIDE_HEIGHT}, new float[] {.4f, .4f, .4f});
		}
		RenderUtils.setupRenderInside();
		
		//Saving The Orthographic matrix to a variable
		Matrix4f ortho = RenderSystem.getProjectionMatrix();
		
		//Setup Projection Matrix
		RenderSystem.setProjectionMatrix(Matrix4f.perspective(Math.toRadians(60f), 
				(float)this.minecraft.getWindow().getWidth() / (float)this.minecraft.getWindow().getHeight()
				, 10F, 1000f));
		
		//Saving Model Matrix
		Matrix4f modelmatrixSaved = RenderSystem.getModelViewStack().last().pose().copy();
		
		//Setup new Identity Model matrix
        PoseStack modelMatrix = RenderSystem.getModelViewStack();
        modelMatrix.setIdentity();
        RenderSystem.applyModelViewMatrix();
        
        {	
        	//setup render properties for 3d rendering
			RenderSystem.enableCull();
			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
        }
        
		{
			matrix.pushPose();
			float scalar = 0.005f;
			matrix.scale(scalar, scalar, scalar);
			matrix.translate(0, 0, - 10000f);
			matrix.translate(x, y, z);

			matrix.mulPose(Vector3f.XP.rotationDegrees(rotY));
			matrix.mulPose(Vector3f.YP.rotationDegrees(-rotX));
			
			if(useGrid)
				renderGrid(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
			
			renderProjector(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
			
			if (showSurroudings)
				renderSurroudings(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
			
			renderWidgets(matrix, partialTicks, mouseX, mouseY, guiLeft, guiTop);
			
			
			matrix.popPose();
		}
		RenderUtils.disableStencil();

		RenderSystem.depthMask(false);
		
		//Resetting the projection matrix to the saved orthographic matrix
		RenderSystem.setProjectionMatrix(ortho);
        modelMatrix.mulPoseMatrix(modelmatrixSaved);
		RenderSystem.applyModelViewMatrix();
	}
	
	void renderGrid(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		matrix.pushPose();
		float gridSize = 400;
		float steps = 10;
		float stepSize = gridSize / steps - 0.01f;
		float tint = 0.7f;
		
		float doubleSize = gridSize * 2f;
		float halfSize = gridSize/2f;
		RenderUtils.renderGrid(matrix, stepSize, new float[] {-halfSize, -halfSize, 0}, new float[] {doubleSize, doubleSize, 1}, new float[] {tint, tint, tint});
		if(is3D) {
			matrix.pushPose();
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderGrid(matrix, stepSize, new float[] {-halfSize, -halfSize, 0}, new float[] {doubleSize, doubleSize, 1}, new float[] {tint, tint, tint});
			matrix.popPose();
			matrix.pushPose();
			matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			matrix.mulPose(Vector3f.ZP.rotationDegrees(270));
			RenderUtils.renderGrid(matrix, stepSize, new float[] {-halfSize, -halfSize, 0}, new float[] {doubleSize, doubleSize, 1}, new float[] {tint, tint, tint});
			matrix.popPose();
		}
		
		{
//			float cubeSize = 10;
//			RenderUtils.renderCube(matrix, -cubeSize/2f, -cubeSize/2f, -cubeSize/2f, cubeSize, cubeSize, cubeSize, 1, 0, 0);
			
			float[] lineColor = new float[] {1, 0, 0, 1};
			RenderUtils.renderLine(matrix, new float[] {0, 0, 0}, new Vector3f(0, gridSize, 0), lineColor);
			RenderUtils.renderLine(matrix, new float[] {0, 0, 0}, new Vector3f(gridSize, 0, 0), lineColor);
			RenderUtils.renderLine(matrix, new float[] {0, 0, 0}, new Vector3f(0, 0, -gridSize), lineColor);
		}
		matrix.popPose();
	}
	
	void renderProjector(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		matrix.pushPose();
		float scale = 40f;
		BufferSource buffer =  Minecraft.getInstance().renderBuffers().bufferSource();
		
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		RenderUtils.renderItemOnTop(matrix, new ItemStack(ModBlocks.LaserProjector.get()), 0, 0, 0, scale, buffer, 15728880, OverlayTexture.NO_OVERLAY);
		matrix.scale(scale, scale, scale);
		
		RenderSystem.depthMask(false);
		matrix.translate(-0.5f, -0.5f, -0.5f);
		RenderUtils.renderLighting(matrix, new float[] {1, 0, 0}, 0.3f, 0.4f, 0.3f);
		RenderSystem.depthMask(true);
		matrix.popPose();
	}
	
	void renderWidgets(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		matrix.mulPose(Vector3f.YP.rotationDegrees(180f));
		
		RenderContext<TileEntityLaserProjector> context = getRenderContext(matrix, partialTicks);
		
		{
			scale = 0.4f;
			context.getMatrix().translate(0.0f, 34f, 0);
			context.getMatrix().scale(scale, scale, scale);
			
			hoveringWidget = getHoveredProjectorWidget(matrix, mouseX, mouseY);
				
			for (ProjectorWidgetData data : te.context.getWidgets()) {
				ProjectorWidget widget = ProjectorGui.getWidget(data);
				context.getMatrix().pushPose();
				widget.renderWidget(context);
				context.getMatrix().popPose();

				context.getMatrix().pushPose();
				if(data == hoveringWidget || data.equals(selectedWidget))
					widget.renderOutline(context);
				context.getMatrix().popPose();
			}
		}
		if(context.getBuffer() instanceof BufferSource)
			((BufferSource)context.getBuffer()).endBatch();
		
		matrix.popPose();
	}
	
	void renderSurroudings(PoseStack matrix, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		matrix.pushPose();
		float rot = te.getBlockState().getValue(BlockHorizontal.FACING).toYRot();
		matrix.mulPose(Vector3f.YP.rotationDegrees(rot));
		matrix.scale(40, 40, 40);
		matrix.translate(-.5f, -.5f, -.5f);
		BufferSource buffer =  Minecraft.getInstance().renderBuffers().bufferSource();
		
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		
		BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();
		BlockEntityRenderDispatcher tileRenderer = Minecraft.getInstance().getBlockEntityRenderDispatcher();
		ModelBlockRenderer modelRenderer = renderer.getModelRenderer();
		Player player = Minecraft.getInstance().player;
		Level level = player.getLevel();
		
		for(int i = -surroudingRange; i <= surroudingRange; i++) {
			for(int j = -surroudingRange; j <= surroudingRange; j++) {
				for(int k = -surroudingRange; k <= surroudingRange; k++) {
					if(i == 0 && j == 0 && k == 0)
						continue;
					matrix.pushPose();
					BlockPos pos = te.getBlockPos().offset(i, j, k);
					BlockState state = level.getBlockState(pos);
					BlockEntity tile = level.getBlockEntity(pos);
					if(state == null)
						continue;
					matrix.translate(i, j, k);
					BakedModel model = renderer.getBlockModel(state);
					IModelData modelData = model.getModelData(minecraft.level, pos, state, null);
					float[] color = Utils.getFloatRGBAFromHexInt(Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0));
					
//					renderer.renderBatched(state, pos, level, matrix, buffer.getBuffer(RenderType.translucent()), true, new Random(), modelData); //--> has shadows on the blocks that have an block next tot them in the world
					modelRenderer.renderModel(matrix.last(), buffer.getBuffer(ItemBlockRenderTypes.getRenderType(state, false)), state, model, color[0], color[1], color[2], 15728880, OverlayTexture.NO_OVERLAY, modelData);
//					renderer.renderSingleBlock(state, matrix, buffer, 15728880, OverlayTexture.NO_OVERLAY, modelData); //--> Crashes when an block with tintindex is visible like the laser
					if(tile != null)
						tileRenderer.render(tile, partialTicks, matrix, buffer);
					matrix.popPose();
				}
			}
		}
		
		matrix.popPose();
	}
	
	public void handleRemove() {
		List<ProjectorWidgetData> toBeRemoved = new ArrayList<>();
		for (ProjectorWidgetData data : te.context.getWidgets()) {
			if(data instanceof ProjectorTextData) {
				if(((ProjectorTextData)data).text.isEmpty() && data != selectedWidget)
					toBeRemoved.add(data);
			}
		}
		for (ProjectorWidgetData data : toBeRemoved) {
			PacketHandler.sendToServer(new PacketDataRemoved(te, data));
		}
	}
	
	public Matrix4f getWorldToClip(PoseStack matrix) {
		Matrix4f projMat = RenderSystem.getProjectionMatrix().copy();
		Matrix4f viewMat = matrix.last().pose().copy();
		projMat.multiply(Matrix4f.createScaleMatrix(1, -1, 1));
		projMat.multiply(RenderSystem.getModelViewMatrix().copy());
		projMat.multiply(viewMat);
		return projMat;
	}
	
	public float[] getMouseInClipSpace(int mouseX, int mouseY) {
		Window win = Minecraft.getInstance().getWindow();
		return new float[] {((mouseX / (float)win.getGuiScaledWidth()) - 0.5f) * 2f, ((mouseY / (float)win.getGuiScaledHeight()) - 0.5f) * 2f};
	}
	
	float[] hitbox = new float[4];
	
	public ProjectorWidgetData getHoveredProjectorWidget(PoseStack matrix, int mouseX, int mouseY) {
		//TODO make more Efficient...
		float[] ClipMousePos = getMouseInClipSpace(mouseX, mouseY);
		
		float ClipMouseX = ClipMousePos[0];
		float ClipMouseY = ClipMousePos[1];
		
		for (ProjectorWidgetData widget : te.context.getWidgets()) {
			var wid = ProjectorGui.getWidget(widget);
			
			matrix.pushPose();
			matrix = wid.getRenderMatrix(matrix);
			if(debug)
				RenderUtils.renderOutline(matrix, wid.getX(), wid.getY(), wid.getZ(), wid.getWidth(), wid.getHeight(), wid.getDepth(), new float[] {1, 0, 1});
			Matrix4f WorldToClip = getWorldToClip(matrix);
			matrix.popPose();
			
			
			Vector4f TLF = new Vector4f(wid.getX(), wid.getY(), wid.getZ(), 1);
			Vector4f TRF = new Vector4f(wid.getX() + wid.getWidth(), wid.getY(), wid.getZ(), 1);
			Vector4f BLF = new Vector4f(wid.getX(), wid.getY() + wid.getHeight(), wid.getZ(), 1);
			Vector4f BRF = new Vector4f(wid.getX() + wid.getWidth(), wid.getY() + wid.getHeight(), wid.getZ(), 1);
			
			Vector4f TLB = new Vector4f(wid.getX(), wid.getY(), wid.getZ() + wid.getDepth(), 1);
			Vector4f TRB = new Vector4f(wid.getX() + wid.getWidth(), wid.getY(), wid.getZ() + wid.getDepth(), 1);
			Vector4f BLB = new Vector4f(wid.getX(), wid.getY() + wid.getHeight(), wid.getZ() + wid.getDepth(), 1);
			Vector4f BRB = new Vector4f(wid.getX() + wid.getWidth(), wid.getY() + wid.getHeight(), wid.getZ() + wid.getDepth(), 1);
			
			transposeToScreen(TLF, WorldToClip);
			transposeToScreen(TRF, WorldToClip);
			transposeToScreen(BLF, WorldToClip);
			transposeToScreen(BRF, WorldToClip);

			transposeToScreen(TLB, WorldToClip);
			transposeToScreen(TRB, WorldToClip);
			transposeToScreen(BLB, WorldToClip);
			transposeToScreen(BRB, WorldToClip);
			
			float minX = Utils.min(TLF.x(), TRF.x(), BLF.x(), BRF.x(), TLB.x(), TRB.x(), BLB.x(), BRB.x());
			float minY = Utils.min(TLF.y(), TRF.y(), BLF.y(), BRF.y(), TLB.y(), TRB.y(), BLB.y(), BRB.y());
			float maxX = Utils.max(TLF.x(), TRF.x(), BLF.x(), BRF.x(), TLB.x(), TRB.x(), BLB.x(), BRB.x());
			float maxY = Utils.max(TLF.y(), TRF.y(), BLF.y(), BRF.y(), TLB.y(), TRB.y(), BLB.y(), BRB.y());
			
			hitbox = new float[] {minX, minY, maxX, maxY};
			
			if(ClipMouseX >= minX && ClipMouseX <= maxX && ClipMouseY >= minY && ClipMouseY <= maxY)
				return widget;
		}
		return null;
	}
	
	Vector4f transposeToScreen(Vector4f vec, Matrix4f WorldToClip) {
		vec.transform(WorldToClip);
		vec.mul(1/vec.w());
		return vec;
	}
	
	public RenderContext<TileEntityLaserProjector> getRenderContext(PoseStack matrix, float partialTicks) {
		return new RenderContext<TileEntityLaserProjector>(te, partialTicks, matrix, Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY);
	}	
	
	protected void renderToolTip(PoseStack matrix, int mouseX, int mouseY, int guiLeft, int guiTop) {
		
	}
	
	protected void renderFG(int mouseX, int mouseY) {
//		int actualMouseX = mouseX - ((this.width - WINDOW_WIDTH) / 2);
//		int actualMouseY = mouseY - ((this.height - WINDOW_HEIGHT) / 2);
	}
	
	boolean isMovingView = false;
	boolean isRotatingView = false;
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(isMovingView || isRotatingView)
			return false;
		
		boolean click = false;
		
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseClicked(mouseX, mouseY, button);

		int i = (this.width - WINDOW_WIDTH) / 2;
	    int j = (this.height - WINDOW_HEIGHT) / 2;
	    
		if(!click && mouseX >= i && mouseX <= i + WINDOW_WIDTH && mouseY >= j && mouseY <= j + WINDOW_HEIGHT) {
			
			prevX = mouseX;
			prevY = mouseY;
			if(button == 0) {
				if(hoveringWidget != null) {
					setSelectedWidget(hoveringWidget);
					return true;
				}else if(selectedWidget != null) {
					setSelectedWidget(hoveringWidget);
					return true;
				}
			}
			if(button == 0) 
				click = isMovingView = true;
			else if(button == 1 && is3D)
				click = isRotatingView = true;
		}
		return click;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int buttonId, double deltaX, double deltaY) {
		if(hoveringWidget != null && buttonId == 0 && !is3D) {
			isMovingWidget = true;
			return true;
		}
		
		boolean click = super.mouseDragged(mouseX, mouseY, buttonId, deltaX, deltaY);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseDragged(mouseX, mouseY, buttonId, deltaX, deltaY);
		return click;
	}
	
	double prevX = 0, prevY = 0;
	
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		if(selectedWidget != null && isMovingWidget) {
			int index = te.context.getWidgets().indexOf(selectedWidget);
			ProjectorWidgetData data = te.context.getWidgets().get(index);
			if(!is3D) {
				Window win = Minecraft.getInstance().getWindow();
				
				data.x -= (mouseX - prevX) / (0.46f*(win.getGuiScaledWidth()/480f));
				data.y -= (mouseY - prevY) / (0.46f*(win.getGuiScaledHeight()/253f));
				prevX = mouseX;
				prevY = mouseY;
				properties.setData(data);
				PacketHandler.sendToServer(new PacketDataChanged(te.getBlockPos(), index, data));
				return;
			}
		}
		if(isMovingView) {
			x += (mouseX - prevX) / 1.5f;
			y -= (mouseY - prevY) / 1.5f;
			prevX = mouseX;
			prevY = mouseY;
			return;
		}else if(isRotatingView) {
			rotX -= (mouseX - prevX) / 1;
			rotY += (mouseY - prevY) / 1;
			
			if(rotY > 90) 
				rotY = 90;
			else if(rotY < -90) 
				rotY = -90;
			
			
			prevX = mouseX;
			prevY = mouseY;
			return;
		}
		super.mouseMoved(mouseX, mouseY);
		for (AbstractWidget widget : buttons)
			widget.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
		if(p_97765_ == GLFW.GLFW_KEY_DELETE && selectedWidget != null) {
			PacketHandler.sendToServer(new PacketDataRemoved(te, selectedWidget));
			setSelectedWidget(null);
			return true;
		}
			
		return super.keyPressed(p_97765_, p_97766_, p_97767_) || properties.keyPressed(p_97765_, p_97766_, p_97767_);
	}
	
	@Override
	public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
		return super.keyReleased(p_94715_, p_94716_, p_94717_) || properties.keyReleased(p_94715_, p_94716_, p_94717_);
	}
	
	@Override
	public boolean charTyped(char p_94683_, int p_94684_) {
		boolean click = false;
		for (AbstractWidget widget : buttons)
			click = click || widget.charTyped(p_94683_, p_94684_);
		return click;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if((isMovingWidget || isMovingView) && button == 0) {
			isMovingWidget = false;
			isMovingView = false;
			return true;
		}if(isRotatingView && button == 1) {
			isRotatingView = false;
			return true;
		}
		
		boolean click = false;
		for (AbstractWidget widget : buttons)
			click = widget.mouseReleased(mouseX, mouseY, button) || click;
		return click;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaScroll) {
		
		boolean click = super.mouseScrolled(mouseX, mouseY, deltaScroll);
		for (AbstractWidget widget : buttons)
			click = click || widget.mouseScrolled(mouseX, mouseY, deltaScroll);

		int i = (this.width - WINDOW_WIDTH) / 2;
	    int j = (this.height - WINDOW_HEIGHT) / 2;
		if(!click && mouseX >= i && mouseX <= i + WINDOW_WIDTH && mouseY >= j && mouseY <= j + WINDOW_HEIGHT) {
			z += deltaScroll * 1000f;
		}
		return click;
	}
		
	public void setSelectedWidget(ProjectorWidgetData data) {
		selectedWidget = data;
		properties.setData(data);
	}
	
	public void renderCheckBox(PoseStack matrix, Checkbox checkbox) {
		int posx = checkbox.x;
		int posy = checkbox.y;
		checkbox.x = 0;
		checkbox.y = 0;
		matrix.pushPose();
		matrix.translate(posx, posy, 0);
		float scale = 0.5f;
		matrix.scale(scale, scale, scale);
		checkbox.setWidth(20);
		checkbox.setHeight(20);
		checkbox.renderButton(matrix, 0, 0, 0);
		checkbox.setWidth(10);
		checkbox.setHeight(10);
		matrix.popPose();
		checkbox.visible = false;
	}

	public void renderWindow(PoseStack p_97357_, int p_97358_, int p_97359_) {
		p_97357_.pushPose();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);
		this.blit(p_97357_, p_97358_, p_97359_, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.font.draw(p_97357_, TITLE, (float)(p_97358_ + WINDOW_TITLE_X), (float)(p_97359_ + WINDOW_TITLE_Y), 4210752);
		p_97357_.popPose();
	}

}
