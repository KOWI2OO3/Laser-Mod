package KOWI2003.LaserMod.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLaser extends AbstractContainerMenu {

	private TileEntityLaser te;
	
	public ContainerLaser(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	public ContainerLaser(int windowId, Inventory playerInv, final TileEntityLaser te) {
		this(windowId, playerInv, te.createHandler(), te);
		this.te = te;
	}
	
	public ContainerLaser(int windowId, Inventory playerInv, ItemStackHandler inventory, TileEntityLaser te) {
		super(ModContainerTypes.LASER_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		
		//(Block) Inventory Slots
	    for(int i = 0; i < 3; ++i) {
	    	for(int j = 0; j < 3; ++j) {
	    		this.addSlot(new SlotItemHandler(inventory, j + i * 3, 62 + j * 18, 17 + i * 18) {
	    			
	    			@Override
	    			public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
	    				if(stack.getItem() instanceof ItemUpgradeBase) {
	    					if(!te.remove((ItemUpgradeBase)stack.getItem(), false))
	    						return;
//	    					return removed ? stack : ItemStack.EMPTY;
	    				}
	    				super.onTake(player, stack);
	    			}
	    			
	    			@Override
	    			public boolean mayPlace(ItemStack stack) {
	    				if(stack.getItem() instanceof ItemUpgradeBase)
	    					return te.acceptsItem((ItemUpgradeBase)stack.getItem(), true);
	    				return false;
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
	      return ItemStack.EMPTY;
	}
	
	private static TileEntityLaser getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null!");
		Objects.requireNonNull(data, "Packet Buffer cannot be null!");
		final BlockEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if(te instanceof TileEntityLaser)
			return (TileEntityLaser) te;
		throw new IllegalStateException("Tile Entity is not a instance of a Laser Tile Entity");
	}
	
	public TileEntityLaser getTileEntity() {
		return te;
	}

	public boolean stillValid(@Nonnull Player player) {
		return true;
	}
	
}
