package KOWI2003.LaserMod.items.tools;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

public class ToolLaserAxe extends ItemLaserToolBase {

	public ToolLaserAxe(Properties properties, float speed, float damageBaseline, int maxCharge) {
		super(properties, BlockTags.MINEABLE_WITH_AXE, speed, damageBaseline, maxCharge);
	}
	
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if(!isExtended(stack))
			return state.requiresCorrectToolForDrops() ? 0f : 1f;
		return state.is(BlockTags.MINEABLE_WITH_AXE) ? getProperties(stack).getProperty(LaserProperties.Properties.SPEED) : super.getDestroySpeed(stack, state);
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return isExtended(stack) ? state.is(BlockTags.MINEABLE_WITH_AXE) : false;
	}
	
	static final SoundEvent[] SOUNDS = new SoundEvent[] {SoundEvents.AXE_STRIP, SoundEvents.AXE_WAX_OFF, SoundEvents.AXE_SCRAPE};
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if(!isExtended(context.getItemInHand()))
			return InteractionResult.PASS;
		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		int index = 0;
		
		BlockState state = world.getBlockState(blockpos).getToolModifiedState(context, ToolActions.AXE_STRIP, true);
		if(state == null) {state = world.getBlockState(blockpos).getToolModifiedState(context, ToolActions.AXE_WAX_OFF, true); index = 1;}
		if(state == null) {state = world.getBlockState(blockpos).getToolModifiedState(context, ToolActions.AXE_SCRAPE, true); index = 2;}
		if(state != null)
		{
			Player player = context.getPlayer();
			world.playSound(player, blockpos, SOUNDS[index], SoundSource.BLOCKS, 1.0F, 1.0F);
			if(!world.isClientSide)
			{
				world.setBlock(blockpos, state, 11);
				if(player != null)
					context.getItemInHand().hurtAndBreak(1, player, (entity) -> {
						entity.broadcastBreakEvent(context.getHand());
					});
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return super.canBeUsed(upgrade) || upgrade.isUsefullForLaserTool(LaserTools.AXE);
	}

	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getToolAbilityNames(LaserTools.AXE);
	}
}
