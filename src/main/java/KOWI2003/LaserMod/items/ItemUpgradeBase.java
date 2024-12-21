package KOWI2003.LaserMod.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.tileentities.ILaserAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUpgradeBase extends ItemDefault /*implements SubItems*/ {

	private String UpgradeName;
	protected boolean stackable = false;
	
	protected String[] AbilityNames = new String[] {};
	protected float[] abilityNameColor = new float[] {0.35f, 0.35f, 0.35f};
	
	protected List<Block> USEFULL_MACHINES = new ArrayList<Block>();
	
	private boolean CanBeUsedForLaser = false;
	private Set<LaserTools> CanBeUsedForLaserTools = new HashSet<>();
	private boolean[] CanBeUsedForLaserArmor = {false, false, false, false};
	
	private int tier = -1;
	
	public ItemUpgradeBase(LaserTools[] usefullForTools, Block... blocks) {
		this(blocks);
		setCanBeUsedForLaserTools(usefullForTools);
	}
	
	public ItemUpgradeBase(LaserTools usefullForTools, Block... blocks) {
		this(blocks);
		setCanBeUsedForLaserTool(usefullForTools);
	}
	
	public ItemUpgradeBase(LaserTools[] usefullForTools, boolean[] usefullForArmor, String name, String[] AbilityNames, float[] AbilityNameColor) {
		this(AbilityNames, AbilityNameColor);
		this.setName(name);
		CanBeUsedForLaserArmor = usefullForArmor;
		setCanBeUsedForLaserTools(usefullForTools);
	}
	
	public ItemUpgradeBase(LaserTools usefullForTools, boolean[] usefullForArmor, String name, String[] AbilityNames, float[] AbilityNameColor) {
		this(AbilityNames, AbilityNameColor);
		this.setName(name);
		CanBeUsedForLaserArmor = usefullForArmor;
		setCanBeUsedForLaserTool(usefullForTools);
	}
	
	public ItemUpgradeBase(LaserTools[] usefullForTools, String[] AbilityNames, float[] AbilityNameColor, Block... blocks) {
		this(AbilityNames, AbilityNameColor, blocks);
		setCanBeUsedForLaserTools(usefullForTools);
	}
	
	public ItemUpgradeBase(LaserTools usefullForTools, String[] AbilityNames, float[] AbilityNameColor, Block... blocks) {
		this(AbilityNames, AbilityNameColor, blocks);
		setCanBeUsedForLaserTool(usefullForTools);
	}
	
	public ItemUpgradeBase(Block... blocks) {
		super(new Item.Properties());
		for (Block block : blocks) {
			if(block == ModBlocks.AdvancedLaser.get() || block == ModBlocks.Laser.get()) {
				setCanBeUsedForLaser(true);
			}else 
				USEFULL_MACHINES.add(block);
		}
	}
	
	public ItemUpgradeBase(String[] AbilityNames, float[] AbilityNameColor, Block... blocks) {
		this(new Item.Properties(), AbilityNames, AbilityNameColor);
		for (Block block : blocks) {
			if(block == ModBlocks.AdvancedLaser.get() || block == ModBlocks.Laser.get()) {
				setCanBeUsedForLaser(true);
			}else 
				USEFULL_MACHINES.add(block);
		}
	}
	
	public ItemUpgradeBase(Properties properties) {
		super(properties);
	}
	
	public ItemUpgradeBase(Properties properties, String[] AbilityNames, float[] AbilityNameColor) {
		super(properties);
		this.AbilityNames = AbilityNames;
		this.abilityNameColor = AbilityNameColor;
	}
	
	public String[] getArmorAbilityNames() {
		return getAbilityNames();
	}
	
	public String[] getToolAbilityNames(LaserTools tool) {
		return getAbilityNames();
	}
	
	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}
	
	public ItemUpgradeBase(String name) {
		this();
		this.setName(name);
	}
	
	public ItemUpgradeBase(String name, String[] AbilityNames, float[] AbilityNameColor) {
		this(AbilityNames, AbilityNameColor);
		this.setName(name);
	}
	
	public ItemUpgradeBase(String name, int tier) {
		this();
		this.tier = tier;
		this.setName(name + "_" + tier);
	}
	
	public ItemUpgradeBase(String name, int tier, String[] AbilityNames, float[] AbilityNameColor) {
		this(AbilityNames, AbilityNameColor);
		this.tier = tier;
		this.setName(name + "_" + tier);
	}
	
	public ItemUpgradeBase(String name, int tier, boolean stackable) {
		this(name, tier);
		this.stackable = stackable;
	}
	
	public ItemUpgradeBase(String name, int tier, boolean stackable, String[] AbilityNames, float[] AbilityNameColor) {
		this(name, tier, AbilityNames, AbilityNameColor);
		this.stackable = stackable;
	}
	
	public ItemUpgradeBase(int tier) {
		this();
		this.tier = tier;
	}
	
	public ItemUpgradeBase(int tier, String[] AbilityNames, float[] AbilityNameColor) {
		this(AbilityNames, AbilityNameColor);
		this.tier = tier;
	}
	
	public ItemUpgradeBase(int tier, boolean stackable) {
		this(tier);
		this.stackable = stackable;
	}
	
	public ItemUpgradeBase(int tier, boolean stackable, String[] AbilityNames, float[] AbilityNameColor) {
		this(tier, AbilityNames, AbilityNameColor);
		this.stackable = stackable;
	}
	
	public boolean hasTier() {
		return tier >= 0;
	}
	
	public boolean isUsefullForLaser() {
		return CanBeUsedForLaser;
	}
	
	public boolean isUsefullForLaserArmor(int index) {
		return (index >= 0 && index <= 3) ? CanBeUsedForLaserArmor[index] : false;
	}
	
	public boolean isUsefullForLaserTool(LaserTools tool) {
		return CanBeUsedForLaserTools.contains(tool);
	}
	
	public boolean isUsefullForMachine(Block Machine) {
		return USEFULL_MACHINES.contains(Machine);
	}
	
	public String getUpgradeName() {
		return UpgradeName;
	}
	
	public void setName(String upgradeName) {
		UpgradeName = upgradeName;
	}
	
	public String getUpgradeBaseName() {
		if(UpgradeName.contains("_")) {
			String[] ss = UpgradeName.split("_");
			if(ss.length > 0)
				return ss[0];
		}
		return UpgradeName;
	}
	
	@Override
	public Component getName(@Nonnull ItemStack stack) {
		if(hasTier())
				return MutableComponent.create(new TranslatableContents("item." + Reference.MODID + ".upgrade_" + getUpgradeBaseName()));
		return super.getName(stack);
	}
	
	public int getTier() {
		return tier;
	}
	
	public int getTierOr(int other) {
		if(hasTier())
			return getTier();
		return other;
	}
	
	protected CompoundTag readFromNBT(ItemStack stack) {
		return stack.getTag();
	}
	
	public void writeToNBT(CompoundTag nbt, ItemStack stack) {
		stack.save(nbt);
	}
	
	public float getMultiplier(LaserProperties.Properties property) {
		return 1;
	}
	
	public void runLaserBlock(ILaserAccess te, BlockPos pos) {}
	
	public void runLaserToolBlockBreak(ItemStack item, BlockPos pos, BlockState state, LivingEntity player) {}
	public void runLaserToolHitEnemy(ItemStack item, LivingEntity enemy, LivingEntity player) {}
	
	public void runOnEntityHitArmor(ItemStack item, LivingEntity attacker, LivingEntity player, float damageAmount) {}
	public void runOnHurtWithArmor(ItemStack item, LivingEntity attacker, LivingEntity player) {}
	public void runOnArmorTick(ItemStack stack, Level level, Player player, EquipmentSlot slot) {}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
		Component text = MutableComponent.create(new TranslatableContents("r"));
		Style st = text.getStyle().applyFormat(ChatFormatting.GRAY);
		
		if(hasTier()) {
			tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.tier", tier)).plainCopy().setStyle(st));
		}
		
		tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip")).plainCopy().setStyle(st));
		if(this.isUsefullForLaser())
			tooltip.add(MutableComponent.create(new TranslatableContents("block.lasermod.laser")).plainCopy().setStyle(st));
		if(this.isUsefullForLaserTool(LaserTools.ALL))
			tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_tool")).plainCopy().setStyle(st));
		else {
			if(this.isUsefullForLaserTool(LaserTools.SWORD))
				tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_sword")).plainCopy().setStyle(st));
			if(this.isUsefullForLaserTool(LaserTools.PICKAXE))
				tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_pickaxe")).plainCopy().setStyle(st));
			if(this.isUsefullForLaserTool(LaserTools.AXE))
				tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_axe")).plainCopy().setStyle(st));
			if(this.isUsefullForLaserTool(LaserTools.SHOVEL))
				tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_shovel")).plainCopy().setStyle(st));
			if(this.isUsefullForLaserTool(LaserTools.HOE))
				tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_hoe")).plainCopy().setStyle(st));
		}
		if(this.isUsefullForLaserTool(LaserTools.OMNI))
			tooltip.add(MutableComponent.create(new TranslatableContents("item.lasermod.laser_multitool")).plainCopy().setStyle(st));
		
		if(canBeUsedForAnyArmor()) {
			if(canBeUsedOnAllArmor()) {
				tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_armor")).plainCopy().setStyle(st));
			}else {
				if(this.isUsefullForLaserArmor(0))
					tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_helmet")).plainCopy().setStyle(st));
				if(this.isUsefullForLaserArmor(1))
					tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_chestplate")).plainCopy().setStyle(st));
				if(this.isUsefullForLaserArmor(2))
					tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_leggings")).plainCopy().setStyle(st));
				if(this.isUsefullForLaserArmor(3))
					tooltip.add(MutableComponent.create(new TranslatableContents("item.upgrade.tooltip.laser_boots")).plainCopy().setStyle(st));
			}
		}
		
		if(!USEFULL_MACHINES.isEmpty()) {
			for(int i = 0; i < USEFULL_MACHINES.size(); i++) {
				tooltip.add(USEFULL_MACHINES.get(i).getName().plainCopy().setStyle(st));
			}
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	public void getMachineUse(Block Machine) {
		
	}
	
	public final String[] getAbilityNames() {
		return AbilityNames;
	}
	
	public final float[] getAbilityNameColor() {
		return abilityNameColor;
	}
	
	public boolean canBeUsedForAnyArmor() {
		return isUsefullForLaserArmor(0) || isUsefullForLaserArmor(1) || isUsefullForLaserArmor(2) || isUsefullForLaserArmor(3);
	}
	
	public boolean canBeUsedOnAllArmor() {
		for(int i = 0; i < 4; i++) 
			if(!isUsefullForLaserArmor(i))
				return false;
		return true;
	}
	
	final static String[] tierLevelDisplay = new String[] {"", "I", "II", "III", "IV", "V", "VI", "VII", "IIX", "IX", "X" };
	
	public String getTierLevelForAbilityName() {
		if(!hasTier())
			return "";
		if(getTier() >= tierLevelDisplay.length)
			return "" + getTier();
		return tierLevelDisplay[getTier()];
	}
	
	public void setUsefullForArmor(int index, boolean canbeUsed) { if(index >= 0 && index <= 3) CanBeUsedForLaserArmor[index] = canbeUsed; }
	public void setUsefullForArmor(boolean canbeUsedForHelmet, boolean canbeUsedForChestplate, boolean canbeUsedForLeggings, boolean canbeUsedForBoots) 
	{ CanBeUsedForLaserArmor = new boolean[] {canbeUsedForHelmet, canbeUsedForChestplate, canbeUsedForLeggings, canbeUsedForBoots}; }
	public void setCanBeUsedForLaserArmor(boolean canBeUsedForLaserArmor) { CanBeUsedForLaserArmor = new boolean[] {canBeUsedForLaserArmor, canBeUsedForLaserArmor, canBeUsedForLaserArmor, canBeUsedForLaserArmor}; }
	
	public void setCanBeUsedForLaser(boolean canBeUsedForLaser) { CanBeUsedForLaser = canBeUsedForLaser; }
	public void setCanBeUsedForLaserTool(LaserTools tool) { CanBeUsedForLaserTools.add(tool); }
	public void setCanBeUsedForLaserTools(LaserTools... tools) { for (LaserTools tool : tools) { CanBeUsedForLaserTools.add(tool); }  }
	
	public static enum LaserTools {
		SWORD, PICKAXE, AXE, SHOVEL, HOE, OMNI, ALL
	}
}
