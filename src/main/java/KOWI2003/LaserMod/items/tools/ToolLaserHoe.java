package KOWI2003.LaserMod.items.tools;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;

public class ToolLaserHoe extends ItemLaserToolBase {

	public ToolLaserHoe(Properties properties, float speed, float damageBaseline, int maxCharge) {
		super(properties, BlockTags.MINEABLE_WITH_HOE, speed, damageBaseline, maxCharge);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if(isExtended((context.getItemInHand())))
		{
			Level world = context.getLevel();
			BlockPos blockpos = context.getClickedPos();
			BlockState original = world.getBlockState(blockpos);
			@Nullable BlockState hook = ForgeEventFactory.onToolUse(original, context, ToolActions.HOE_TILL, false);
			if(hook != null && hook != original) return InteractionResult.SUCCESS;
			if(context.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above()))
			{
				BlockState state = world.getBlockState(blockpos).getToolModifiedState(context, ToolActions.HOE_TILL, true);
				if(state != null)
				{
					Player player = context.getPlayer();
					world.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
					if(!world.isClientSide)
					{
						world.setBlock(blockpos, state, 11);
						if(player != null)
						{
							context.getItemInHand().hurtAndBreak(1, player, 
									(entity) -> entity.broadcastBreakEvent(context.getHand()));
						}
					}
					return InteractionResult.sidedSuccess(world.isClientSide);
				}
			}
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return super.canBeUsed(upgrade) || upgrade.isUsefullForLaserTool(LaserTools.HOE);
	}
	
	@Override
	public String[] getAbilityNames(ItemUpgradeBase upgrade) {
		return upgrade.getToolAbilityNames(LaserTools.HOE);
	}
}
