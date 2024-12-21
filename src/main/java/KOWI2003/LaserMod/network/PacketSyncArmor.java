package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.items.ItemLaserArmorBase;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncArmor {

	public boolean active;
	public EquipmentSlot slot;
	
	public PacketSyncArmor(FriendlyByteBuf buf) {
		active = buf.readBoolean();
		slot = EquipmentSlot.values()[buf.readInt()];
	}
	
	public PacketSyncArmor(boolean active, EquipmentSlot slot) {
		this.active = active;
		this.slot = slot;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeBoolean(active);
		buf.writeInt(slot.ordinal());
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	for (ItemStack armor: sender.getArmorSlots()) {
					if(armor.getEquipmentSlot() == slot) 
					{
						if(armor.getItem() instanceof ItemLaserArmorBase) {
							armor = LaserItemUtils.setExtended(armor, active);
						}
					}
				}
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
