package KOWI2003.LaserMod.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.interfaces.ILaserUpgradable;
import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerModStation extends AbstractContainerMenu {

	private TileEntityModStation te;
	
	public ContainerModStation(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	public ContainerModStation(int windowId, Inventory playerInv, TileEntityModStation te) {
		super(ModContainerTypes.MOD_STATION_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		
		//(Block) Inventory Slots
		this.addSlot(new SlotItemHandler(te.handler, 0, 134, 16) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ILaserUpgradable;
			}
			
			@Override
			public void setChanged() {
				te.sync();
				super.setChanged();
			}
		});
		this.addSlot(new SlotItemHandler(te.handler, 1, 134, 54) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ItemUpgradeBase;
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
	
	private static TileEntityModStation getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null!");
		Objects.requireNonNull(data, "Packet Buffer cannot be null!");
		final BlockEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if(te instanceof TileEntityModStation)
			return (TileEntityModStation) te;
		throw new IllegalStateException("Tile Entity is not a instance of a Modification Station Tile Entity");
	}
	
	public TileEntityModStation getTileEntity() {
		return te;
	}
	
	public boolean stillValid(@Nonnull Player player) {
		return true;
	}
}
