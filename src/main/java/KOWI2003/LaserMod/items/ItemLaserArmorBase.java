package KOWI2003.LaserMod.items;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.init.ModKeybindings;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.items.interfaces.IExtendable;
import KOWI2003.LaserMod.items.interfaces.ILaserUpgradable;
import KOWI2003.LaserMod.items.render.model.LaserArmorModelHandler;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketSyncArmor;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.client.IItemRenderProperties;

public class ItemLaserArmorBase extends ArmorItem implements ILaserUpgradable, IChargable, IExtendable {

	private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
		protected ItemStack execute(BlockSource p_40408_, ItemStack p_40409_) {
			return ArmorItem.dispenseArmor(p_40408_, p_40409_) ? p_40409_ : super.execute(p_40408_, p_40409_);
		}
	};
   	protected final EquipmentSlot slot;
	ArmorMaterial material;
	protected LaserProperties defaultProperties;

	protected int maxCharge = 1000;
	
	public ItemLaserArmorBase(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
		super(material, slot, properties);
		this.slot = slot;
		this.material = material;
		DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
		this.defaultProperties = new LaserProperties();
		this.defaultProperties.setProperty(LaserProperties.Properties.DEFENCE, getDefense());;
		this.defaultProperties.setProperty(LaserProperties.Properties.TOUGHNESS, getToughness());
		this.defaultProperties.setProperty(LaserProperties.Properties.DURABILITY, 400);
	}
	
	static boolean isDown = false;
	
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		LaserProperties properties = getProperties(stack);
		for(ItemUpgradeBase upgrade : properties.getUpgrades())
			upgrade.runOnArmorTick(stack, level, player, slot);
		
		if(level.isClientSide)
		{
			boolean keyDown = ModKeybindings.ArmorToggle.isDown();
			if(keyDown && !isDown) {
				//Toggle Armor!
				boolean isAnyDeactive = false;
				for(ItemStack armor : player.getArmorSlots())
				{
					if(armor.getItem() instanceof IExtendable) {
						if(!isExtended(stack))
							isAnyDeactive = true;
					}
				}
				
//				boolean newState = isAnyActive == isAnyDeactive ? !isAnyActive : isAnyDeactive;
				boolean newState = isAnyDeactive;
				
				for(ItemStack armor : player.getArmorSlots())
				{
					if(armor.getItem() instanceof IExtendable) {
						armor = setExtended(armor, newState);
						PacketHandler.sendToServer(new PacketSyncArmor(newState, armor.getEquipmentSlot()));
					}
				}
				
				isDown = true;
			}else if(isDown && !keyDown)
				isDown = false;
		}
		
		super.onArmorTick(stack, level, player);
	}
	
	@Override
	public int getMaxCharge(ItemStack stack) {
		return (int) (maxCharge + LaserItemUtils.getDurabilityAddition(stack));
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return upgrade.isUsefullForLaserArmor(3-slot.getIndex());
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	public ArmorMaterial getMaterial() {
		return ArmorMaterials.IRON;
//		return material;
	}
	
	public EquipmentSlot getSlot() {
		return this.slot;
	}
	
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return slot;
	}
	
	@Override
	public int getEnchantmentValue() {
		return -1;
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
		return false;
	}

	public int getDefense() {
		return this.material.getDefenseForSlot(getSlot());
	}

	public float getToughness() {
		return this.material.getToughness();
	}
	
	@Nullable
	public SoundEvent getEquipSound() {
		return this.material.getEquipSound();
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = new ItemStack(this);
		stack = setExtended(stack, false);
		stack = setCharge(stack, maxCharge);
		stack = setColor(stack, 1.0f, 0f, 0f);
		stack = setProperties(stack, defaultProperties);
		return stack;
	}

	public InteractionResultHolder<ItemStack> use(Level p_40395_, Player p_40396_, InteractionHand p_40397_) {
		ItemStack itemstack = p_40396_.getItemInHand(p_40397_);
		EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = p_40396_.getItemBySlot(equipmentslot);
		if (itemstack1.isEmpty()) {
			p_40396_.setItemSlot(equipmentslot, itemstack.copy());
			if (!p_40395_.isClientSide()) {
				p_40396_.awardStat(Stats.ITEM_USED.get(this));
			}

			itemstack.setCount(0);
			return InteractionResultHolder.sidedSuccess(itemstack, p_40395_.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round((1d - getDurabilityForDisplay(stack))*13);
	}
	
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1d - (double)getCharge(stack)/(double)getMaxCharge(stack);
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getCharge(stack) < getMaxCharge(stack);
	}
	
	@Override
	public int getBarColor(ItemStack stack) {
		return Mth.hsvToRgb(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
	}
	
	public <T extends LivingEntity> void damage(ItemStack stack, int amount, T entity, Consumer<T> entityConsumer) {
		if (!entity.level.isClientSide && (!(entity instanceof Player) || !((Player)entity).getAbilities().instabuild)) {
			if(getCharge(stack) > 0 && isExtended(stack)) {
				stack = setCharge(stack, getCharge(stack) - amount);
				if(getCharge(stack) <= 0)
					setExtended(stack, false);
			}
	    }
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			
			@Override
			public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
					EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				return LaserArmorModelHandler.getModel(itemStack, _default);
			}
		});
	}
	
	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getArmorAbilityNames();
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		TextComponent charge = new TextComponent("Charge: " + getCharge(stack) + "/" + getMaxCharge(stack));
		charge.setStyle(charge.getStyle().withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(0.35f, 0.35f, 0.35f))));
		tooltip.add(new TextComponent("Press " + ModKeybindings.ArmorToggle.getKey().getDisplayName().getString().toUpperCase() + " to " + 
		(LaserItemUtils.isExtended(stack) ? "deactivate" : "activate")).withStyle(charge.getStyle().withColor(Utils.getHexIntFromRGB(.6f, .6f, .6f))));
		tooltip.add(charge);

		boolean debug = false;
		if(debug) {
			tooltip.add(new TextComponent(isExtended(stack)+""));
			float[] color = getColor(stack);
			tooltip.add(new TextComponent(color[0]+", " + color[1] + ", " + color[2]));
		}
		
		LaserProperties properties = getProperties(stack);
		
		if(properties.hasUpgradeWithAbilityName()) {
			tooltip.add(new TextComponent(""));
			tooltip.add(new TextComponent("Abilities: ").setStyle(new TextComponent("").getStyle().withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(0.7f, 0.7f, 0.7f)))));
			for (ItemUpgradeBase upgrade : properties.getUpgrades()) {
				if(getAbilityNames(upgrade).length > 0) {
					float[] color = upgrade.getAbilityNameColor();
					for(String abilityName : getAbilityNames(upgrade)) {
						TextComponent comp = new TextComponent(abilityName);
						Style style = comp.getStyle();
						style = style.withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(
								color.length > 0 ? color[0] : 0.35f,
								color.length > 1 ? color[1] : 0.35f,
								color.length > 2 ? color[2] : 0.35f)));
						comp.setStyle(style);
						tooltip.add(comp);
					}
				}
			}
		}
		
		super.appendHoverText(stack, world, tooltip, flags);
	}
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		stack = LaserItemUtils.setCharge(stack, Math.max(LaserItemUtils.getCharge(stack)-amount, 0));
		if(LaserItemUtils.getCharge(stack) <= 0)
			stack = LaserItemUtils.setExtended(stack, false);
		EquipmentSlot slot = ((ItemLaserArmorBase)stack.getItem()).getEquipmentSlot(stack);
		entity.setItemSlot(slot, stack);
		return 0;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return slot == this.slot && isExtended(stack) ? getAttributeModifiers(getProperties(stack)) : getEmptyModifiers();
	}
	
	private Multimap<Attribute, AttributeModifier> getEmptyModifiers() {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		return builder.build();
	}
	
	private Multimap<Attribute, AttributeModifier> getAttributeModifiers(LaserProperties properties) {
		//TODO add chaching for the modifiers;
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[getSlot().getIndex()];
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", this.getDefense(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)this.getToughness(), AttributeModifier.Operation.ADDITION));
		double knockbackResistance = (double)this.getMaterial().getKnockbackResistance();
		if (knockbackResistance > 0) {
			builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", knockbackResistance, AttributeModifier.Operation.ADDITION));
		}
		return builder.build();
	}
	
	public float[] getRGB(ItemStack stack, int tintindex) {
		return tintindex==0 ? getColor(stack) : new float[] {1.0f, 1.0f, 1.0f};
	}
	
	public static class LaserArmorMaterial implements ArmorMaterial {
		
		private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
		
		int durability;
		int[] defense;
		float toughness;
		float knockbackResistance;
		String name;
		SoundEvent sound;
		
		public LaserArmorMaterial() {
			this("Laser", 15, new int[] {2, 5, 6, 2}, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f);
		}
		
		public LaserArmorMaterial(String name, int durability, int[] defense, SoundEvent sound, float toughness, float knockbackResistance) {
			this.name = name;
			this.sound = sound;
			this.durability = durability;
			this.defense = defense;
			this.toughness = toughness;
			this.knockbackResistance = knockbackResistance;
		}
		
		@Override
		public int getDurabilityForSlot(EquipmentSlot slot) { return HEALTH_PER_SLOT[slot.getIndex()] * durability; }
		@Override
		public int getDefenseForSlot(EquipmentSlot slot) { return defense[slot.getIndex()]; }
		@Override
		public int getEnchantmentValue() { return 0; }
		@Override
		public SoundEvent getEquipSound() { return null; }
		@Override
		public Ingredient getRepairIngredient() { return null; }
		@Override
		public String getName() { return Reference.MODID + ":" + name; }
		@Override
		public float getToughness() { return toughness; }
		@Override
		public float getKnockbackResistance() { return knockbackResistance; }
		
	}


	
}
