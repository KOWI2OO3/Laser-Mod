package KOWI2003.LaserMod.events;

import java.util.LinkedList;
import java.util.List;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.LaserItem;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LaserBlockBreakEvent {

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		onLaserToolBreakBlock(event);
	}
	
	public static boolean onLaserToolBreakBlock(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockPos pos = event.getPos();
		BlockState state = event.getState();
		LevelAccessor level = event.getLevel();
		
		ItemStack item = player.getInventory().getSelected();
		
		if(item.getItem() instanceof LaserItem) {
			LaserProperties props = LaserItemUtils.getProperties(item);
			if(props.hasUpgarde("fire")) {
				if(player.level instanceof ServerLevel) {
					List<ItemStack> newDrops = new LinkedList<>();
					List<ItemStack> drops = Block.getDrops(state, (ServerLevel)player.level, pos, player.level.getBlockEntity(pos));
					List<SmeltingRecipe> recipes = player.level.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING);
					for (ItemStack stack : drops) {
						boolean found = false;
						for (SmeltingRecipe recipe : recipes) {
							for(Ingredient ing : recipe.getIngredients()) {
								for(ItemStack input : ing.getItems()) {
									if(input.getItem() == stack.getItem()) {
										ItemStack temp = recipe.getResultItem().copy();
										temp.setCount(stack.getCount());
										newDrops.add(temp);
										found = true;
										break;
									}
								}
								if(found)
									break;
							}
							if(found)
								break;
						}
						if(!found) {
							newDrops.add(stack);
						}
					}
					event.setCanceled(false);
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
					if(!player.isCreative())
					for (ItemStack drop : newDrops) {
						Block.popResource(player.level, pos, drop);
					}
					return true;
				}
			}
		}
		return false;
	}
	
}
