package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityDeviceHub;
import KOWI2003.LaserMod.tileentities.TileEntityRemoteCC;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PacketComputerToMainThread {

	public BlockPos pos;
	public int index;
	public Object[] args;
	public boolean isDevice;
	
	public PacketComputerToMainThread(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		index = buf.readInt();
		isDevice = buf.readBoolean();
		args = Utils.getObjectArray(buf.readNbt());
	}
	
	public PacketComputerToMainThread(BlockPos pos, int index, Object[] args) {
		this(pos, index, args, false);
	}
	
	public PacketComputerToMainThread(BlockPos pos, int index, Object[] args, boolean isDevice) {
		this.pos = pos;
		this.index = index;
		this.args = args;
		this.isDevice = isDevice;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(index);
		buf.writeBoolean(isDevice);
		buf.writeNbt(Utils.putObjectArray(args));
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        
	        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
	        if(tileentity instanceof TileEntityRemoteCC) {
	        	TileEntityRemoteCC te = (TileEntityRemoteCC)tileentity;
	        	te.callMethode(index, args);
	        }
	        if(tileentity instanceof TileEntityDeviceHub) {
	        	TileEntityDeviceHub te = (TileEntityDeviceHub)tileentity;
	        	te.callMethod(index, args);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
