package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PacketDataRemoved {

	public BlockPos pos;
	public int ordinal;
	public ProjectorWidgetData data;
	
	public PacketDataRemoved(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()); 
		this.ordinal = buf.readInt();
		data = ProjectorWidgetData.deserializeFromNBT(buf.readNbt());
//		this.value = buf.readFloat();
	}
	
	public PacketDataRemoved(BlockPos pos, int ordinal, ProjectorWidgetData data) {
		this.pos = pos;
		this.data = data;
		this.ordinal = ordinal;
	}
	
	public PacketDataRemoved(TileEntityLaserProjector tileentity, ProjectorWidgetData data) {
		this.pos = tileentity.getBlockPos();
		this.ordinal = tileentity.getContext().getWidgets().indexOf(data);
		this.data = data;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(ordinal);
		buf.writeNbt(data.serializeNBT());
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        
	        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
	        if(tileentity instanceof TileEntityLaserProjector) {
	        	TileEntityLaserProjector te = (TileEntityLaserProjector)tileentity;
	        	if(te.getContext().getWidgets().contains(data))
	        		te.getContext().getWidgets().remove(data);
	    		te.sync();
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
