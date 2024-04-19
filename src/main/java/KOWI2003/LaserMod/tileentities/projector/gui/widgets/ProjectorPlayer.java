package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorPlayerData;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import KOWI2003.LaserMod.utils.Utils.GenericConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProjectorPlayer extends ProjectorWidget {

	public boolean liveModel;
	public boolean isChild;
	public String username;
	public GenericConsumer<GameProfile> profile;
	
	private boolean isProfileChecked = false;
	
	public ProjectorPlayer(ProjectorPlayerData data) {
		super(data);
		liveModel = data.liveModel;
		isChild = data.child;
		username = data.username;
		profile = data.profile;
		if(profile == null || profile.getStored() == null) {
			profile = new GenericConsumer<>();
			profile.accept(new GameProfile(null, username + "_"));
		}
		setScalingType(ScalingType.Rectangular);
	}
	
	public ProjectorPlayer(float x, float y, boolean liveModel, boolean isChild, String username, GenericConsumer<GameProfile> profile) {
		super(x, y, 1, 1);
		this.liveModel = liveModel;
		this.isChild = isChild;
		this.username = username;
		this.profile = profile;
//		updateProfile();
		setScalingType(ScalingType.Rectangular);
		
	}
	
	public ProjectorPlayer(float x, float y, float z, boolean liveModel, boolean isChild, String username, GenericConsumer<GameProfile> profile) {
		super(x, y, z, 1, 1, 1);
		this.liveModel = liveModel;
		this.isChild = isChild;
		this.username = username;
		this.profile = profile;
//		updateProfile();
		setScalingType(ScalingType.Rectangular);
	}
	
	@Override
	public PoseStack getRenderMatrix(PoseStack matrix) {
		matrix = super.getRenderMatrix(matrix);
		matrix.translate(x, y, z);
//		float scale = 100f * getScale();
//		scale *= 0.7f;
//		if(liveModel) {
//			scale *= 1.05f;
//			matrix.scale(scale, scale, scale);
//		}
		return matrix;
	}
	
	@Override
	public void renderWidget(RenderContext<?> context) {
//		context.getMatrix().pushPose();
//		renderOutline(context);
//		context.getMatrix().popPose();
		
		PoseStack matrix = context.getMatrix();
		BlockEntity te = context.getTileentity();
		matrix.pushPose();
		
		matrix.mulPose(Vector3f.YP.rotationDegrees(rotation));
		
		matrix.translate(x, y, z);
		
		float scale = 100f * getScale();
		scale *= 0.7f;
		if(liveModel) {
			Player p;
			if(username.equals("{Player}"))
				p = Minecraft.getInstance().player;
			else 
				p = Utils.getPlayer(te.getLevel(), username);
			
			if(p == null) {
				matrix.popPose();
				matrix.pushPose();
				String text = "Unable To Find Player!";
				RenderSystem.disableCull();
				RenderUtils.rotateMatrixForBlock(matrix, te.getBlockState().getValue(BlockHorizontal.FACING));
				matrix.mulPose(Vector3f.ZP.rotationDegrees(180f));
				matrix.translate(-0.5f - text.length()/2f * 0.015f*5f, -1.1 - (0.015f*5f/2f) - .3f*2.2f, 0.5f);
				matrix.scale(0.015f, 0.015f, 0.005f);
				RenderUtils.renderString(matrix, text, 0, 0, 0, 1.0f, new float[] {.6f, 0.0f, 0.0f, 1.0f}, false);
				RenderSystem.enableCull();
			}else {
				scale *= 1.05f;
				matrix.scale(scale, scale, scale);
				RenderUtils.renderPlayer(matrix, p, 0, 0, 0, 1f, new float[] {1.0f, 1.0f, 1.0f, getAlpha(te)}, context.getBuffer(), context.getCombinedLight(), context.getCombinedOverlay(), isChild);
			}
		}else
			if(username.equals("{Player}"))
				RenderUtils.renderPlayerGameProfile(matrix, Minecraft.getInstance().player.getGameProfile(), 0, scale*1.5f, 0, scale, new float[] {1.0f, 1.0f, 1.0f, getAlpha(te)},  context.getBuffer(), context.getCombinedLight(), context.getCombinedOverlay(), isChild);
			else
				if(profile != null && profile.getStored() != null)
					RenderUtils.renderPlayerGameProfile(matrix, profile.getStored(), 0, scale*1.5f, 0, scale, new float[] {1.0f, 1.0f, 1.0f, getAlpha(te)}, context.getBuffer(), context.getCombinedLight(), context.getCombinedOverlay(), isChild);
		
//		System.out.println(profile);
		matrix.popPose();
	}
	
	@Override
	public float getX() {
		return - getWidth()/2f;
	}
	
	@Override
	public float getY() {
		return -1;
	}
	
	@Override
	public float getZ() {
		return - getDepth()/2f;
	}
	
	@Override
	public float getWidth() {
		return 70 * getScale() - ((isChild && !liveModel) ? getScale()*34 : 0);
	}
	
	@Override
	public float getHeight() {
		return 143 * getScale() - ((isChild && !liveModel) ? getScale()*62 : 0);
	}
	
	@Override
	public float getDepth() {
		return 35 * getScale() - ((isChild && !liveModel) ? getScale()*7 : 0);
	}
	
}
