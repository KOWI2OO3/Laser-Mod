package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.ProjectorTemplates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PacketTemplateProjector {

	public BlockPos pos;
	public int ordinal;
	
	public PacketTemplateProjector(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()); 
		this.ordinal = buf.readInt();
	}
	
	public PacketTemplateProjector(BlockPos pos, ProjectorTemplates ordinal) {
		this.pos = pos;
		this.ordinal = ordinal.ordinal();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(ordinal);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        
	        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
	        if(tileentity instanceof TileEntityLaserProjector) {
	        	TileEntityLaserProjector te = (TileEntityLaserProjector)tileentity;
	        	if(ordinal < ProjectorTemplates.values().length && ordinal >= 0)
	        	te.setTemplate(ProjectorTemplates.values()[ordinal]);
//	    		te.sync();
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
