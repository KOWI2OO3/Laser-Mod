package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PacketLaserMode  {

	public BlockPos pos;
	public boolean dir;
	public int value = -1;
	
	public PacketLaserMode(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()); 
		dir = buf.readBoolean();
		value = buf.readInt();
	}
	
	public PacketLaserMode(BlockPos pos, boolean forward) {
		this.pos = pos;
		this.dir = forward;
	}
	
	public PacketLaserMode(BlockPos pos, int value) {
		this.pos = pos;
		this.dir = true;
		this.value = value;
	}
	
	public PacketLaserMode(BlockPos pos) {
		this.pos = pos;
		this.dir = true;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(dir);
		buf.writeInt(value);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        
	        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
	        if(tileentity instanceof TileEntityLaser) {
	        	TileEntityLaser te = (TileEntityLaser)tileentity;
	        	if(value == -1) {
		        	if(dir) {
			        	int ordinal = te.mode.ordinal() + 1;
			        	if(ordinal >= MODE.values().length) {
			        		ordinal = 0;
			        	}
			        	te.mode = MODE.values()[ordinal];
		        	}else {
		        		int ordinal = te.mode.ordinal() - 1;
			        	if(ordinal < 0) {
			        		ordinal = MODE.values().length - 1;
			        	}
			        	te.mode = MODE.values()[ordinal];
		        	}
	        	}else {
	        		if(value >= 0 && value < MODE.values().length)
	        			te.mode = MODE.values()[value];
	        	}
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
