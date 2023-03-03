package KOWI2003.LaserMod.items;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemCrafting extends ItemDefault {

	public ItemCrafting() {}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(MutableComponent.create(new LiteralContents("Crafting Item")));
		super.appendHoverText(stack, world, tooltip, flag);
	}
}
