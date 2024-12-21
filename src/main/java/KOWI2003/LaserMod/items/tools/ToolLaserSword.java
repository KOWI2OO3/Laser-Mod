package KOWI2003.LaserMod.items.tools;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ToolLaserSword extends ItemLaserToolBase {

	public ToolLaserSword(Properties properties, float speed, float damageBaseline, int maxCharge) {
		super(properties, null, speed, damageBaseline, maxCharge);
	}
	
	@Override
	public boolean canAttackBlock(@Nonnull BlockState state, @Nonnull Level level,
			@Nonnull BlockPos blockpos, @Nonnull Player player) {
		return !player.isCreative();
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
		if(isExtended(stack)) {
	      if (state.is(Blocks.COBWEB)) {
	         return 15.0F;
	      } else {
	         Material material = state.getMaterial();
	         return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.WATER_PLANT
	        		 && material != Material.REPLACEABLE_FIREPROOF_PLANT && material != Material.REPLACEABLE_WATER_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
	      }
		}
		return state.requiresCorrectToolForDrops() ? 0f : 1f;
    }
	
	public boolean isCorrectToolForDrops(@Nonnull BlockState state) {
	      return state.is(Blocks.COBWEB);
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return super.canBeUsed(upgrade) || upgrade.isUsefullForLaserTool(LaserTools.SWORD);
	}
	
	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getToolAbilityNames(LaserTools.SWORD);
	}
	
}
