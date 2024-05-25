package KOWI2003.LaserMod.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.tileentities.TileEntityPrecisionAssembler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPrecisionAssembler extends AbstractContainerMenu {

	private TileEntityPrecisionAssembler te;
	
	public ContainerPrecisionAssembler(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	public ContainerPrecisionAssembler(int windowId, Inventory playerInv, TileEntityPrecisionAssembler te) {
		super(ModContainerTypes.PRECISION_ASSEMBLER_TYPE.get(), windowId);
		this.te = te;
		
		//(Block) Inventory Slots
		this.addSlot(new SlotItemHandler(te.handler, 0, 14, 10));
		this.addSlot(new SlotItemHandler(te.handler, 1, 14, 31));
		this.addSlot(new SlotItemHandler(te.handler, 2, 14, 52));
		this.addSlot(new SlotItemHandler(te.handler, 3, 50, 31));
		this.addSlot(new SlotItemHandler(te.handler, 4, 132, 31) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});
		this.addSlot(new SlotItemHandler(te.getUpgradeInv(), 0, 153, 7) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				if(stack.getItem() instanceof ItemUpgradeBase)
					return te.acceptsItem((ItemUpgradeBase)stack.getItem(), true);
				return false;
			}
			
			@Override
			public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
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
	
	private static TileEntityPrecisionAssembler getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null!");
		Objects.requireNonNull(data, "Packet Buffer cannot be null!");
		final BlockEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if(te instanceof TileEntityPrecisionAssembler)
			return (TileEntityPrecisionAssembler) te;
		throw new IllegalStateException("Tile Entity is not a instance of a Precision Assembler Tile Entity");
	}
	
	public TileEntityPrecisionAssembler getTileEntity() {
		return te;
	}
	
	public boolean stillValid(@Nonnull Player player) {
		return true;
	}
}
