package KOWI2003.LaserMod.init;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import KOWI2003.LaserMod.items.upgrades.UpgardeAirtightSeal;
import KOWI2003.LaserMod.items.upgrades.UpgradeCapacity;
import KOWI2003.LaserMod.items.upgrades.UpgradeDamage;
import KOWI2003.LaserMod.items.upgrades.UpgradeDistance;
import KOWI2003.LaserMod.items.upgrades.UpgradeFire;
import KOWI2003.LaserMod.items.upgrades.UpgradeMining;
import KOWI2003.LaserMod.items.upgrades.UpgradeMove;
import KOWI2003.LaserMod.items.upgrades.UpgradeSpeed;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModUpgrades {

	private static final List<RegistryObject<Item>> UPGARDES = new ArrayList<>();
	public static final LinkedList<RegistryObject<Item>> tabStacks = new LinkedList<>();
	
	public static final RegistryObject<Item> Speed = registerItem("speed", () -> new UpgradeSpeed("speed"));
	public static final RegistryObject<Item> Mining = registerItem("mining", () -> new UpgradeMining("mining"));
	public static final RegistryObject<Item> Fire = registerItem("fire", () -> new UpgradeFire("fire"));
	public static final RegistryObject<Item> Color = registerItem("color", () -> new ItemUpgradeBase(new LaserTools[] {LaserTools.ALL, LaserTools.OMNI}, new boolean[] {true, true, true, true},"color", new String[] {"Colorable"}, new float[] {0.5f,  0.0f, 0.5f}) {
		public boolean isUsefullForLaser() {return true;};
		public boolean isUsefullForLaserTool() { return true; };
	});
	public static final RegistryObject<Item> Mode = registerItem("mode", () -> new ItemUpgradeBase("mode") {
		public boolean isUsefullForLaser() {return true;};
	});
	
	public static final RegistryObject<Item> Damage1 = registerItem("damage_1", () -> new UpgradeDamage("damage", 1));
	public static final RegistryObject<Item> Damage2 = registerItem("damage_2", () -> new UpgradeDamage("damage", 2));
	public static final RegistryObject<Item> Damage3 = registerItem("damage_3", () -> new UpgradeDamage("damage", 3));
	public static final RegistryObject<Item> Damage4 = registerItem("damage_4", () -> new UpgradeDamage("damage", 4));
	public static final RegistryObject<Item> Damage5 = registerItem("damage_5", () -> new UpgradeDamage("damage", 5) {
		public float getMultiplier(KOWI2003.LaserMod.LaserProperties.Properties property) {
			if(property == KOWI2003.LaserMod.LaserProperties.Properties.DAMAGE)
				return 22.5f;
			return super.getMultiplier(property);
		};
	});
	
	public static final RegistryObject<Item> NoDamage = registerItem("no_damage", () -> new ItemUpgradeBase("no_damage") {
		public boolean isUsefullForLaser() { return true; };
		public float getMultiplier(KOWI2003.LaserMod.LaserProperties.Properties property) {
			if(property == KOWI2003.LaserMod.LaserProperties.Properties.DAMAGE)
				return 0;
			return super.getMultiplier(property);
		};
	});
	
	public static final RegistryObject<Item> Silence = registerItem("silence", () -> new ItemUpgradeBase("silence") {
		public boolean isUsefullForLaser() {return true;};
	});
	
	public static final RegistryObject<Item> Push = registerItem("push", () -> new UpgradeMove("push", true));
	public static final RegistryObject<Item> Pull = registerItem("pull", () -> new UpgradeMove("pull", false));
	public static final RegistryObject<Item> Distance = registerItem("distance_1", () -> new UpgradeDistance("distance", 1));
	public static final RegistryObject<Item> Distance2 = registerItem("distance_2", () -> new UpgradeDistance("distance", 2));
	public static final RegistryObject<Item> Distance3 = registerItem("distance_3", () -> new UpgradeDistance("distance", 3));
	
	public static final RegistryObject<Item> AirtightSeal = registerItem("airtight_seal", () -> new UpgardeAirtightSeal("airtight_seal", new String[] {"Airtight Seal"}, new float[] {0.58f,  0.0f, 0.827f}));

	public static final RegistryObject<Item> Capacity = registerItem("capacity_1", () -> new UpgradeCapacity("capacity", 1));
	public static final RegistryObject<Item> Capacity2 = registerItem("capacity_2", () -> new UpgradeCapacity("capacity", 2));
	public static final RegistryObject<Item> Capacity3 = registerItem("capacity_3", () -> new UpgradeCapacity("capacity", 3));
	
	public static List<String> getAllNames() {
		List<String> list = new ArrayList<String>();
		for (RegistryObject<Item> upgrade : UPGARDES) {
			list.add(((ItemUpgradeBase)upgrade.get()).getUpgradeName());
		}
		return list;
	}
	
	public static ItemUpgradeBase getUpgradeByName(String upgradeName) {
		for (RegistryObject<Item> upgrade : UPGARDES) {
			if(((ItemUpgradeBase)upgrade.get()).getUpgradeName().equals(upgradeName))
				return ((ItemUpgradeBase)upgrade.get());
		}
		return null;
	}
	
	public static ItemUpgradeBase getUpgradeByBaseName(String upgradeName) {
		for (RegistryObject<Item> upgrade : UPGARDES) {
			if(((ItemUpgradeBase)upgrade.get()).getUpgradeBaseName().equals(upgradeName))
				return ((ItemUpgradeBase)upgrade.get());
		}
		return null;
	}
	
	private static RegistryObject<Item> registerItemHidden(String name, Supplier<? extends ItemUpgradeBase> item)
    {
		RegistryObject<Item> i = registerItem(name, item);
        ModItems.hidenItems.add(i);
    	return i;
    }
    
    public static RegistryObject<Item> registerItem(String name, Supplier<? extends ItemUpgradeBase> item)
    {
    	RegistryObject<Item> i = ModItems.ITEMS.register("upgrade_" + name, item);
    	UPGARDES.add(i);
    	tabStacks.add(i);
        return i;
    }
}
