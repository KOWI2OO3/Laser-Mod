package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketLaserToolTagUpdate {

	public int slot;
	public CompoundTag tag;
	
	public PacketLaserToolTagUpdate(FriendlyByteBuf buf) {
		slot = buf.readInt();
		tag = buf.readNbt();
	}
	
	public PacketLaserToolTagUpdate(int slot, CompoundTag tag) {
		this.slot = slot;
		this.tag = tag;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(slot);
		buf.writeNbt(tag);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	ItemStack stack = sender.getInventory().getItem(slot);
	        	stack.setTag(tag);
	        	sender.getInventory().setItem(slot, stack);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
}
