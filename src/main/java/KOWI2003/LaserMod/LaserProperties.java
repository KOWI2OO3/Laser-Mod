package KOWI2003.LaserMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class LaserProperties {
	Map<String, Float> properties;
	List<ItemUpgradeBase> upgrades;
	
	public LaserProperties() {
		upgrades = new ArrayList<ItemUpgradeBase>();
		properties = new HashMap<String, Float>();
	}
	
	public void setProperty(Properties property, float value) {
		properties.put(property.name(), value);
	}
	
	public boolean hasProperty(Properties property) {
		return properties.containsKey(property.name());
	}
	
	public float getProperty(Properties property) {
		float value = getPropertyBase(property);
		if(value < 0)
			return -1;
		if(upgrades.size() > 0)
		for (ItemUpgradeBase upgarde : upgrades) {
			if(upgarde != null) {
				float mult = upgarde.getMultiplier(property);
				if(mult >= 0) {
					value *= mult;
				}
			}
		}
		return value;
	}
	
	public float getPropertyBase(Properties property) {
		if(properties.containsKey(property.name())) {
			return properties.get(property.name());
		}
		return -1;
	}
	
	public boolean hasUpgarde(ItemUpgradeBase upgrade) {
		return hasUpgarde(upgrade.getUpgradeBaseName());
	}
	
	public boolean hasUpgarde(String upgradeName) {
		if(upgrades.size() > 0)
		for (ItemUpgradeBase upgarde : upgrades) {
			if(upgarde != null) {
				if(upgradeName.contains("_")) {
					String[] ss = upgradeName.split("_");
					if(ss.length > 0)
						upgradeName = ss[0];
				}
				if(upgarde.getUpgradeBaseName().equals(upgradeName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ItemUpgradeBase getUpgarde(ItemUpgradeBase upgrade) {
		return getUpgarde(upgrade.getUpgradeName());
	}
	
	public ItemUpgradeBase getUpgarde(String upgradeName) {
		if(!upgradeName.contains("_")) {
			return getUpgardeBase(upgradeName);
		}
		for (ItemUpgradeBase upgarde : upgrades) {
			if(upgarde.getUpgradeName().equals(upgradeName)) {
				return upgarde;
			}
		}
		return null;
	}
	
	private ItemUpgradeBase getUpgardeBase(String upgradeBaseName) {
		for (ItemUpgradeBase upgarde : upgrades) {
			if(upgarde.getUpgradeBaseName().equals(upgradeBaseName)) {
				return upgarde;
			}
		}
		return null;
	}
	
	public ItemUpgradeBase removeUpgrade(String upgradeName) {
		ItemUpgradeBase upgrade;
		if(upgradeName.contains("_"))
			upgrade = getUpgarde(upgradeName);
		else 
			upgrade = getUpgardeBase(upgradeName);
		return removeUpgrade(upgrade);
	}
	
	public ItemUpgradeBase removeUpgrade(ItemUpgradeBase upgrade) {
		if(upgrade != null)
			upgrades.remove(upgrade);
		return upgrade;
	}
	
	public boolean addUpgrade(ItemUpgradeBase upgrade) {
		if(!hasUpgarde(upgrade.getUpgradeBaseName())) {
			upgrades.add(upgrade);
			return true;
		}else
		return false;
	}
	
	public List<String> getUpgardeNames() {
		List<String> list = new ArrayList<String>();
		for (ItemUpgradeBase upgarde : upgrades) {
			list.add(upgarde.getUpgradeName());
		}
		return list;
	}
	
	public List<ItemUpgradeBase> getUpgrades() {
		return upgrades;
	}
	
	public boolean doesAllow(ItemUpgradeBase upgrade) {
		if(!hasUpgarde(upgrade.getUpgradeName())) {
			return true;
		}
		return false;
	}
	
	public CompoundTag save(CompoundTag nbt) {
		CompoundTag propNBT = new CompoundTag();
		for (String key : properties.keySet()) {
			propNBT.putFloat(key, properties.get(key));
		}
		nbt.put("Properties", propNBT);
		String upgradesString = "";
		if(upgrades.size() > 0)
		for (ItemUpgradeBase upgrade : upgrades) {
			if(upgrade != null)
				upgradesString += upgrade.getUpgradeName() + ", ";
		}
		if(upgrades.size() > 0 && upgradesString.length() > 2)
			upgradesString = upgradesString.substring(0, upgradesString.length()-2);
		nbt.putString("Upgrades", upgradesString);
		return nbt;
	}
	
	public void load(CompoundTag nbt) {
		if(nbt.contains("Properties")) {
			CompoundTag propNBT = nbt.getCompound("Properties");
			for (String key : propNBT.getAllKeys()) {
				properties.put(key, propNBT.getFloat(key));
			}
		}
		if(nbt.contains("Upgrades")) {
			String upgradesString = nbt.getString("Upgrades");
			upgrades = new ArrayList<ItemUpgradeBase>();
			for (String name : upgradesString.split(", ")) {
				ItemUpgradeBase item = ModUpgrades.getUpgradeByName(name);
				if(item != null)
					upgrades.add(item);
			}
		}
	}
	
	public ItemStackHandler createHandler(int size) {
		ItemStackHandler inv = new ItemStackHandler(size) {
			@Override
			public void setStackInSlot(int slot, ItemStack stack) {
				if(stack.getItem() instanceof ItemUpgradeBase)
					addUpgrade((ItemUpgradeBase)stack.getItem());
				super.setStackInSlot(slot, stack);
			}
		};
		List<ItemUpgradeBase> items = getUpgrades();
		for(int i = 0; i < (int)Math.min(items.size(), size); i++) {
			inv.setStackInSlot(i, new ItemStack(items.get(i)));
		}
		return inv;
	}
	
	public boolean acceptsItem(ItemUpgradeBase upgrade, boolean simulate) {
		return simulate ? doesAllow(upgrade) : addUpgrade(upgrade);
	}
	
	public boolean remove(ItemUpgradeBase upgarde, boolean simulate) {
		return simulate ? hasUpgarde(upgarde) : removeUpgrade(upgarde) != null;
	}
	
	public boolean hasUpgradeWithAbilityName() {
		for (ItemUpgradeBase upgrade : upgrades) {
			if(upgrade.getAbilityNames().length > 0)
				return true;
		}
		return false;
	}
	
	public static enum Properties {
		DAMAGE,
		MAX_DISTANCE,
		SPEED,
		DURABILITY,
		DEFENCE,
		TOUGHNESS;
	}
}
