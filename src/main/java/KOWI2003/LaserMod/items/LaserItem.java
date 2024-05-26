package KOWI2003.LaserMod.items;

import java.util.List;
import java.util.function.Consumer;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.items.interfaces.IExtendable;
import KOWI2003.LaserMod.items.interfaces.ILaserUpgradable;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class LaserItem extends ItemDefault implements ILaserUpgradable, IChargable, IExtendable {

	protected LaserProperties defaultProperties;
	
	int maxCharge = 1000;
	
	public LaserItem(Properties properties, int maxCharge) {
		this(properties);
		this.maxCharge = maxCharge;
	}
	
	public LaserItem(Properties properties) {
		super(properties);
		defaultProperties = new LaserProperties();
	}

	@Override
	public int getMaxCharge(ItemStack stack) {
		return (int) (maxCharge + LaserItemUtils.getDurabilityAddition(stack));
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round((1d - LaserItemUtils.getDurabilityForDisplay(stack))*13);
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getCharge(stack) < getMaxCharge(stack);
	}
	
	@Override
	public int getBarColor(ItemStack stack) {
		return Mth.hsvToRgb(Math.max(0.0F, (float) (1.0F - LaserItemUtils.getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
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
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		TextComponent charge = new TextComponent("Charge: " + getCharge(stack) + "/" + getMaxCharge(stack));
		charge.setStyle(charge.getStyle().withColor(TextColor.fromRgb(Utils.getHexIntFromRGB(0.35f, 0.35f, 0.35f))));
		tooltip.add(charge);
		
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
	public ItemStack getDefaultInstance() {
		ItemStack stack = new ItemStack(this);
		stack = setExtended(stack, false);
		stack = setProperties(stack, defaultProperties);
		stack = setCharge(stack, getMaxCharge(stack));
		stack = setColor(stack, 1.0f, 0f, 0f);
		return stack;
	}
	
	@Override
	public float[] getRGB(ItemStack stack, int tintindex) {
		return getColor(stack);
	}

}
