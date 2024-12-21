package KOWI2003.LaserMod.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerInfuser extends AbstractContainerMenu {

	private TileEntityInfuser te;
	
	public ContainerInfuser(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	public ContainerInfuser(int windowId, Inventory playerInv, TileEntityInfuser te) {
		super(ModContainerTypes.INFUSER_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		
		// Upgrade Slot
		this.addSlot(new SlotItemHandler(te.getUpgradeInv(), 0, 153, 7) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				if(stack.getItem() instanceof ItemUpgradeBase)
					return te.acceptsItem((ItemUpgradeBase)stack.getItem(), true);
				return false;
			}
			
			@Override
			public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
				if(stack.getItem() instanceof ItemUpgradeBase upgrade && !te.remove(upgrade, false))
					return;
				
				super.onTake(player, stack);
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

		//(Block) Inventory Slots
		this.addSlot(new SlotItemHandler(te.handler, 0, 13, 12));
		this.addSlot(new SlotItemHandler(te.handler, 1, 41, 56));
		this.addSlot(new SlotItemHandler(te.handler, 2, 116, 56) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});
	    
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
	
	private static TileEntityInfuser getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null!");
		Objects.requireNonNull(data, "Packet Buffer cannot be null!");
		final BlockEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if(te instanceof TileEntityInfuser)
			return (TileEntityInfuser) te;
		throw new IllegalStateException("Tile Entity is not a instance of a Infuser Tile Entity");
	}
	
	public TileEntityInfuser getTileEntity() {
		return te;
	}
	
	public boolean stillValid(@Nonnull Player player) {
		return true;
	}
}
