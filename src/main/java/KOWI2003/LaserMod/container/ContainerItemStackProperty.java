package KOWI2003.LaserMod.container;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketSyncItemProperty;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerItemStackProperty extends AbstractContainerMenu {

	private ItemStackHandler inventory;
	
	public ContainerItemStackProperty(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, new ItemStackHandler());
	}
	
	public ContainerItemStackProperty(int windowId, Inventory playerInv, ItemStackHandler handler) {
		super(ModContainerTypes.ITEM_PROPERTY_TYPE.get(), windowId);
		boolean storeItem = false;
		if(handler != null) {
			this.inventory = handler;
			//(Block) Inventory Slots
			this.addSlot(new SlotItemHandler(inventory, 0, 80, 52) {
				
				@Override
				public boolean mayPickup(Player player) {
					if(!storeItem) {
						inventory.setStackInSlot(0, ItemStack.EMPTY);
						if(player instanceof ServerPlayer)
							PacketHandler.sendToClient(new PacketSyncItemProperty(ItemStack.EMPTY), (ServerPlayer) player);
						return false;
					}else
						return super.mayPickup(player);
				}
				
				@Override
				public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
					if(player instanceof ServerPlayer)
						PacketHandler.sendToClient(new PacketSyncItemProperty(ItemStack.EMPTY), (ServerPlayer) player);
					super.onTake(player, stack);
				}
				
				@Override
				public boolean mayPlace(ItemStack stack) {
					Player player = playerInv.player;
					ItemStack place = stack.copy();
					place.setCount(1);
					if(player instanceof ServerPlayer)
						PacketHandler.sendToClient(new PacketSyncItemProperty(place), (ServerPlayer) player);
					if(!storeItem) {
						inventory.setStackInSlot(0, place);
						return false;
					}else
						return super.mayPlace(stack);
				}
				
				
				@Override
				public int getMaxStackSize() {
					return getMaxStackSize(null);
				}
				
				@Override
				public int getMaxStackSize(ItemStack stack) {
					return 1;
				}
			});
		}
	
	    //Player Inventory Slots
	    for(int k = 0; k < 3; ++k) {
	    	for(int i1 = 0; i1 < 9; ++i1) {
	    		this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
	    	}
	    }
	      
	    //Player Hotbar Slots
	    for(int l = 0; l < 9; ++l) {
	    	this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
	    }
	}
	
	@Override
	public ItemStack quickMoveStack(@Nonnull Player player, int index) {
		return Utils.handleQuickMove(this, player, index);
	}

	public boolean stillValid(@Nonnull Player p_38874_) {
		return true;
	}
	
}
