package KOWI2003.LaserMod.tileentities;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.container.ContainerModStation;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.interfaces.ILaserUpgradable;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityModStation extends SyncableBlockEntity implements MenuProvider {

	public ItemStackHandler handler;
	
	public TileEntityModStation(BlockPos pos, BlockState state) {
		super(ModTileTypes.MOD_STATION, pos, state);
		handler = new ItemStackHandler(2);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		handler.deserializeNBT(nbt.getCompound("inv"));
		super.load(nbt);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inv", handler.serializeNBT());
		super.saveAdditional(nbt);
	}

	public void addUpgrade() {
		ItemStack stack = handler.getStackInSlot(0);
		if(!stack.isEmpty() && stack.getItem() instanceof ILaserUpgradable) {
			ItemStack upgradeStack = handler.getStackInSlot(1);
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			if(!upgradeStack.isEmpty() && upgradeStack.getItem() instanceof ItemUpgradeBase) {
				if(!((ILaserUpgradable)stack.getItem()).canBeUsed(((ItemUpgradeBase) upgradeStack.getItem())))
					return;
				if(properties.addUpgrade((ItemUpgradeBase) upgradeStack.getItem())) {
					handler.extractItem(1, 1, false);
					stack = LaserItemUtils.setProperties(stack, properties);
					handler.setStackInSlot(0, stack);
				}
			}
		}
		sync();
	}
	
	public void removeUpgrade(String upgradeName) {
		ItemStack stack = handler.getStackInSlot(0);
		if(!stack.isEmpty() && stack.getItem() instanceof ILaserUpgradable) {
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			ItemUpgradeBase item = properties.removeUpgrade(upgradeName);
			if(item != null) {
				if(handler.getStackInSlot(1).getItem() == item || handler.getStackInSlot(1).isEmpty()) {
					ItemStack returnStack = handler.insertItem(1, new ItemStack(item), false);
					if(!returnStack.isEmpty())
						return;
					stack = LaserItemUtils.setProperties(stack, properties);
					handler.setStackInSlot(0, stack);
				}
			}
		}
		sync();
	}
	
	public float[] getColor() {
		ItemStack stack = handler.getStackInSlot(0);
		if(!stack.isEmpty() && stack.getItem() instanceof ILaserUpgradable) {
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			if(properties.hasUpgarde("color")) {
				return LaserItemUtils.getColor(stack);
			}
		}
		return new float[] {1.0f, 0.0f, 0.0f};
	}
	
	public void setColor(float red, float green, float blue) {
		ItemStack stack = handler.getStackInSlot(0);
		if(!stack.isEmpty() && stack.getItem() instanceof ILaserUpgradable) {
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			if(properties.hasUpgarde("color")) {
				stack = LaserItemUtils.setColor(stack, red, green, blue);
			}else
				stack = LaserItemUtils.setColor(stack, 1.0f, 0.0f, 0.0f);
			handler.setStackInSlot(0, stack);
		}
		sync();
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return new ContainerModStation(windowId, playerInv, this);
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("block.lasermod.mod_station");
	}

}
