package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.gui.widgets.properties.ItemProperty;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncItemProperty {

	public ItemStack stack;
	
	public PacketSyncItemProperty(FriendlyByteBuf buf) {
		stack = buf.readItem();
	}
	
	public PacketSyncItemProperty(ItemStack stack) {
		this.stack = stack;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeItem(stack);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	ItemProperty.openInstance.setValue(stack);
				ItemProperty.openInstance.setHasChanged();
	        }else {
	        	
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
