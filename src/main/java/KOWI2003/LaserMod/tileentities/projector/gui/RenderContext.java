package KOWI2003.LaserMod.tileentities.projector.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RenderContext<T extends BlockEntity> {

	T tileentity;
	float partialTicks;
	PoseStack matrix;
	MultiBufferSource bufferIn;
	int combinedLightIn; 
	int combinedOverlayIn;
	
	public RenderContext(T tileentity, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		this.tileentity = tileentity;
		this.partialTicks = partialTicks;
		this.matrix = matrix;
		this.bufferIn = bufferIn;
		this.combinedLightIn = combinedLightIn;
		this.combinedOverlayIn = combinedOverlayIn;
	}
	
	public T getTileentity() {
		return tileentity;
	}
	
	public float getPartialTicks() {
		return partialTicks;
	}
	
	public PoseStack getMatrix() {
		return matrix;
	}
	
	public MultiBufferSource getBuffer() {
		return bufferIn;
	}
	
	public int getCombinedLight() {
		return combinedLightIn;
	}
	
	public int getCombinedOverlay() {
		return combinedOverlayIn;
	}
	
}
