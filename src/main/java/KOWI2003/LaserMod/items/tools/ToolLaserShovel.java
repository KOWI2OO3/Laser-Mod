package KOWI2003.LaserMod.items.tools;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

public class ToolLaserShovel extends ItemLaserToolBase {

	public ToolLaserShovel(Properties properties, float speed, float damageBaseline, int maxCharge) {
		super(properties, BlockTags.MINEABLE_WITH_SHOVEL, speed, damageBaseline, maxCharge);
	}
	
	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
		if(!isExtended(stack))
			return state.requiresCorrectToolForDrops() ? 0f : 1f;
		return state.is(BlockTags.MINEABLE_WITH_SHOVEL) ? getProperties(stack).getProperty(LaserProperties.Properties.SPEED) : super.getDestroySpeed(stack, state);
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return isExtended(stack) ? state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK) || state.is(BlockTags.MINEABLE_WITH_SHOVEL) : false;
	}

	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		if(!isExtended(context.getItemInHand()))
			return InteractionResult.PASS;
		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		if(context.getClickedFace() == Direction.DOWN)
			return InteractionResult.PASS;
		Player player = context.getPlayer();
		BlockState state = blockstate.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, true);
		if(state != null & world.isEmptyBlock(blockpos.above()))
			world.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
		else if(blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT))
		{
			if(!world.isClientSide())
				world.levelEvent((Player)null, 1009, blockpos, 0);
			CampfireBlock.dowse(player, world, blockpos, blockstate);
			state = blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false));
		}
		
		if(state != null)
		{
			if(!world.isClientSide()) 
			{
				world.setBlock(blockpos, state, 11);
				if(player !=  null)
				{
					context.getItemInHand().hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(context.getHand()));
				}
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return super.canBeUsed(upgrade) || upgrade.isUsefullForLaserTool(LaserTools.SHOVEL);
	}
	
	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getToolAbilityNames(LaserTools.SHOVEL);
	}
}
