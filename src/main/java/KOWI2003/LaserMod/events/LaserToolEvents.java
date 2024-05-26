package KOWI2003.LaserMod.events;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketMultiToolLaserBreakBlock;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.ClickInputEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LaserToolEvents {

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onClickInput(ClickInputEvent event) 
	{
		Player player = Minecraft.getInstance().player;
		if(player.getItemInHand(event.getHand()).getItem() instanceof ItemLaserToolBase && 
				((ItemLaserToolBase)player.getItemInHand(event.getHand()).getItem()).getProperties(player.getItemInHand(event.getHand())).hasUpgarde("mining") && !player.isShiftKeyDown()) {
			if(event.isAttack())
				onAttack(event, player.getItemInHand(event.getHand()), player);
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onBlockBreak(BreakEvent event) {
		Player player = event.getPlayer();
		if(player.getMainHandItem().getItem() instanceof ItemLaserToolBase && 
				((ItemLaserToolBase)player.getMainHandItem().getItem()).getProperties(player.getMainHandItem()).hasUpgarde("mining") && !player.isShiftKeyDown() && LaserItemUtils.getCharge(player.getMainHandItem()) > 0) {
			
			if(!LaserItemUtils.isExtended(player.getMainHandItem()))
				return;
			
			if(side == Direction.UP)
				side = Direction.getNearest(player.getForward().x, player.getForward().y, player.getForward().z);

			if(currentPosition == null)
				currentPosition = event.getPos();
			
			List<Direction> dirs = new ArrayList<>();
			
			for (Direction dir : Direction.values()) {
				if(dir != side && dir != side.getOpposite()) {
					dirs.add(dir);
//					player.level.destroyBlock(Utils.offset(currentPosition, dir, 1), false);
//					PacketHandler.sendToServer(new PacketMultiToolLaserBreakBlock(Utils.offset(currentPosition, dir, 1), InteractionHand.MAIN_HAND));
				}
			}
			
			BlockPos topLeft = Utils.offset(Utils.offset(new BlockPos(0, 0, 0), dirs.get(0), 1), dirs.get(2), 1);
			BlockPos bottomRight = Utils.offset(Utils.offset(new BlockPos(0, 0, 0), dirs.get(1), 1), dirs.get(3), 1);
			
			int charge = LaserItemUtils.getCharge(event.getPlayer().getMainHandItem());
			
			blockBreak:
			for(int i = topLeft.getX(); i <= bottomRight.getX(); i++) {
				for(int j = topLeft.getY(); j <= bottomRight.getY(); j++) {
					for(int k = topLeft.getZ(); k <= bottomRight.getZ(); k++) {
						BlockPos pos = currentPosition.offset(new BlockPos(i, j, k));
						ItemLaserToolBase tool = (ItemLaserToolBase)player.getMainHandItem().getItem();
						if(tool.getDestroySpeed(player.getMainHandItem(), player.level.getBlockState(pos)) > 1.0f) {
							PacketHandler.sendToServer(new PacketMultiToolLaserBreakBlock(pos, InteractionHand.MAIN_HAND));
							charge--;
						}
						if(charge <= 0)
							break blockBreak;
					}
				}
			}
			
			side = Direction.UP;
			progress = 0;
			currentPosition = null;
		}
	}
	
	static BlockPos currentPosition = null;
	static float progress = 0;
	static Direction side = Direction.UP;

	@OnlyIn(Dist.CLIENT)
	private void onAttack(ClickInputEvent event, ItemStack stack, Player player) {
		
	}
	
}
