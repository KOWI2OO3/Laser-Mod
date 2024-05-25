package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.container.ContainerItemStackProperty;
import KOWI2003.LaserMod.gui.widgets.properties.ItemProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
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
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
				NetworkHooks.openScreen(sender, (new MenuProvider() {
	    			
	    			@Override
	    			public AbstractContainerMenu createMenu(int menuId, @Nonnull Inventory inventory, @Nonnull Player player) {
	    				return new ContainerItemStackProperty(menuId, player.getInventory(), handler);
	    			}
	    			
	    			@Override
	    			public Component getDisplayName() {
	    				return MutableComponent.create(new LiteralContents(title));
	    			}
	    		}), new BlockPos(0, 0, 0));
	        }
	    });
	    ctx.get().setPacketHandled(true);
	}
	
}
