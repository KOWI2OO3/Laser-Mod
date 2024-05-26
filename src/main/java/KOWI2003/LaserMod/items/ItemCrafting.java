package KOWI2003.LaserMod.items;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemCrafting extends ItemDefault {

	public ItemCrafting() {}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(new TextComponent("Crafting Item"));
		super.appendHoverText(stack, world, tooltip, flag);
	}
}
