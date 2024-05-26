package KOWI2003.LaserMod.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemLaserToolBase extends LaserItem {
	
	protected final TagKey<Block> blocks;
	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public ItemLaserToolBase(Properties properties, TagKey<Block> blocks, float speed, float damageBaseline, int maxCharge) {
		super(properties, maxCharge);
		this.blocks = blocks;
		this.defaultProperties.setProperty(LaserProperties.Properties.DAMAGE, damageBaseline);
		this.defaultProperties.setProperty(LaserProperties.Properties.SPEED, speed);
		this.defaultProperties.setProperty(LaserProperties.Properties.DURABILITY, 400);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)damageBaseline, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)speed, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		ItemStack stack = entity.getItemInHand(hand);
		if(getCharge(stack) > 0)
			stack = setExtended(stack, !isExtended(stack));
		else
			return super.use(world, entity, hand);
		entity.setItemInHand(hand, stack);
		return InteractionResultHolder.pass(entity.getItemInHand(hand));
	}
	
	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getToolAbilityNames(LaserTools.ALL);
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return upgrade.isUsefullForLaserTool(LaserTools.ALL);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if(isExtended(stack) && slot == EquipmentSlot.MAINHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)getProperties(stack).getProperty(LaserProperties.Properties.DAMAGE), AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)getProperties(stack).getProperty(LaserProperties.Properties.SPEED), AttributeModifier.Operation.ADDITION));
			this.defaultModifiers = builder.build();
			return defaultModifiers;
		}
		return super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos,
			LivingEntity entity) {
		if(isExtended(stack)) {
			if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
				LaserProperties properties = getProperties(stack);
				properties.getUpgrades().forEach((upgrade) -> {
					upgrade.runLaserToolBlockBreak(stack, pos, state, entity);
				});
				damage(stack, 1, entity, (p_220039_0_) -> {
					  p_220039_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND); });
		      }
		    return true;
		}
		return false;
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity enemy, LivingEntity player) {
		if(isExtended(stack)) {
			LaserProperties properties = getProperties(stack);
			properties.getUpgrades().forEach((upgrade) -> {
				upgrade.runLaserToolHitEnemy(stack, enemy, player);
			});
			
			damage(stack, 2, player, (entity) -> {
			  entity.broadcastBreakEvent(EquipmentSlot.MAINHAND); });
		    return true;
		}
		return false;
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
	  if(isExtended(stack)) {
	      return state.is(this.blocks) ? getProperties(stack).getProperty(LaserProperties.Properties.SPEED) * 4 : 1.0F;
	  }
	  return state.requiresCorrectToolForDrops() ? 0f : 1f;
	}
	
	@Override
	public float[] getRGB(ItemStack stack, int tintindex) {
		return getColor(stack);
	}
}
