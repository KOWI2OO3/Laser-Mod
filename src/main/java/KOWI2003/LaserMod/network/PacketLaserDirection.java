package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.network.NetworkEvent;

public class PacketLaserDirection {

	public BlockPos pos;
	public Vec2 eularRotation;
	
	public PacketLaserDirection(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()); 
		this.eularRotation = new Vec2(buf.readFloat(), buf.readFloat());
	}
	
	public PacketLaserDirection(BlockPos pos, Vec2 eularRotation) {
		this.pos = pos;
		this.eularRotation = eularRotation;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeFloat(eularRotation.x);
		buf.writeFloat(eularRotation.y);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        
	        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
	        if(tileentity instanceof TileEntityAdvancedLaser) {
	        	TileEntityAdvancedLaser te = (TileEntityAdvancedLaser)tileentity;
	        	te.setRotationEular(eularRotation);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}