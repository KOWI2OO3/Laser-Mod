package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.container.ContainerItemStackProperty;
import KOWI2003.LaserMod.gui.widgets.properties.ItemProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

public class PacketOpenItemPropertyMenu {

	public ItemStackHandler handler;
	public String title;
	
	public PacketOpenItemPropertyMenu(FriendlyByteBuf buf) {
		handler = new ItemStackHandler(1);
		handler.deserializeNBT(buf.readNbt());
		title = buf.readUtf();
	}
	
	public PacketOpenItemPropertyMenu(ItemProperty property) {
		this.handler = property.getItemStackHandler();
		this.title = property.getDisplayName();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeNbt(handler.serializeNBT());
		buf.writeUtf(title);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
				NetworkHooks.openGui(sender, (new MenuProvider() {
	    			
	    			@Override
	    			public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
	    				return new ContainerItemStackProperty(p_39954_, p_39956_.getInventory(), handler);
	    			}
	    			
	    			@Override
	    			public Component getDisplayName() {
	    				return new TextComponent(title);
	    			}
	    		}), new BlockPos(0, 0, 0));
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
