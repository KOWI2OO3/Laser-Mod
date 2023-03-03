package KOWI2003.LaserMod.events;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.ItemLaserMutliTool;
import KOWI2003.LaserMod.network.PacketFireLaserBullet;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketLaserToolTagUpdate;
import KOWI2003.LaserMod.network.PacketMultiToolLaserBreakBlock;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LaserMultiToolEvents {

	static float totalOffset = 0; //the offset an head has after shooting
	ItemStack lastStack = ItemStack.EMPTY;
	
	@SubscribeEvent
	public void onClickInput(InputEvent.InteractionKeyMappingTriggered event) 
	{
		Player player = Minecraft.getInstance().player;
		
		if(player.getItemInHand(event.getHand()).getItem() == ModItems.LaserMultiTool.get()) {
			if(LaserItemUtils.getCharge(player.getItemInHand(event.getHand())) > 0) {
				if(event.isAttack()) 
					onAttack(event, player);
				else if(event.isUseItem())
					onItemUse(event, player);
			}
			lastStack = player.getItemInHand(event.getHand());
		}
	}
	
	private void onAttack(InputEvent.InteractionKeyMappingTriggered event, Player player) {
		event.setCanceled(true);
		event.setSwingHand(false);
		
		//Spawn Entity -- Cooldown?
		ItemStack stack = player.getMainHandItem();
		PacketHandler.sendToServer(new PacketFireLaserBullet(stack));
		
		if(!player.isCreative() && !player.isSpectator()) {
			int slot = player.getInventory().findSlotMatchingItem(stack);
			stack = LaserItemUtils.setCharge(stack, LaserItemUtils.getCharge(stack)-1);
			PacketHandler.sendToServer(new PacketLaserToolTagUpdate(slot, stack.getTag()));
		}
//		totalOffset -= 2;
	}
	
	public static BlockPos currentPos;
	static float destroyProgress = 0;
	static float destroyTicks = 0;
	
	private void onItemUse(InputEvent.InteractionKeyMappingTriggered event, Player player) {
		event.setSwingHand(false);
		event.setCanceled(true);
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		Player player = Minecraft.getInstance().player;
		if(player != null) {
			ItemStack riddanceStack = ItemStack.EMPTY;
			if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.LaserMultiTool.get()) {
				if(Minecraft.getInstance().options.keyUse.isDown()) {
					handleBreakBlock(player, InteractionHand.MAIN_HAND);
					return;
				}else 
					riddanceStack = player.getItemInHand(InteractionHand.MAIN_HAND);
			}else if(player.getItemInHand(InteractionHand.OFF_HAND).getItem() == ModItems.LaserMultiTool.get()) {
				if(Minecraft.getInstance().options.keyUse.isDown()) {
					handleBreakBlock(player, InteractionHand.OFF_HAND);
					return;
				}else 
					riddanceStack = player.getItemInHand(InteractionHand.OFF_HAND);
			}
			
			if(!riddanceStack.isEmpty()) {
				int slot = player.getInventory().findSlotMatchingItem(player.getItemInHand(InteractionHand.MAIN_HAND));
				riddanceStack = LaserItemUtils.setExtended(riddanceStack, false);
				PacketHandler.sendToServer(new PacketLaserToolTagUpdate(slot, riddanceStack.getTag()));
			}
			
			if(currentPos != null) {
				Minecraft.getInstance().levelRenderer.destroyBlockProgress(player.getId(), currentPos, (int)-1);
				currentPos = null;
				destroyProgress = 0;
				destroyTicks = 0;
				
				for(InteractionHand hand : InteractionHand.values()) {
					ItemStack stack = player.getItemInHand(hand);
					if(!lastStack.isEmpty() && lastStack != stack) {
						currentPos = null;
						destroyProgress = 0;
						destroyTicks = 0;
						lastStack = stack;
						
						if(stack.getItem() instanceof ItemLaserMutliTool) {
							if(LaserItemUtils.isExtended(stack)) {
								stack = LaserItemUtils.setExtended(stack, false);
								int slot = player.getInventory().findSlotMatchingItem(stack);
								
								CompoundTag tag = stack.getTag();
								tag.putFloat("distance", 10);
								stack.setTag(tag);
								player.setItemInHand(hand, stack);
								
								PacketHandler.sendToServer(new PacketLaserToolTagUpdate(slot, stack.getTag()));
							}
						}
					}
				}
			}
			
//			if(totalOffset != 0) {
//				totalOffset += .1f;
//				player.setXRot(player.getXRot() + (totalOffset+1f)/1f);
//				if(totalOffset > 0)
//					totalOffset = 0;
//			}
		}
	}
	
	private void handleBreakBlock(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Level level = player.getLevel();
		if(LaserItemUtils.getCharge(stack) > 0) {
			Vec3 pos = player.position().add(new Vec3(0, player.getEyeHeight(), 0)); 
			Vec3 direction = player.getForward();
			
			float max = 10;
			LaserProperties properties = LaserItemUtils.getProperties(stack);
			if(properties.hasUpgarde("capacity"))
				max *= properties.getUpgarde("capacity").getTier();
			
			for(float i = 0; i < max; i+=.2f) {
				Vec3 position = pos.add(direction.scale(i));
				BlockPos bpos = new BlockPos((int)Math.floor(position.x), (int)Math.floor(position.y), (int)Math.floor(position.z));
				BlockState state = level.getBlockState(bpos);
				if(!state.isAir()) {
					MultiPlayerGameMode game = Minecraft.getInstance().gameMode;
					if(currentPos == null || !(bpos.getX() == currentPos.getX() && bpos.getY() == currentPos.getY() && bpos.getZ() == currentPos.getZ())) {
						destroyProgress = 0;
						destroyTicks = 0;
						currentPos = bpos;
					}
					if (destroyTicks % 4.0F == 0.0F) {
						SoundType soundtype = state.getSoundType(Minecraft.getInstance().level, bpos, player);
						Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(soundtype.getHitSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 8.0F, soundtype.getPitch() * 0.5F, RandomSource.create(), bpos));
					}
					++destroyTicks;
					destroyProgress += state.getDestroyProgress(player, level, bpos);

					int slot = player.getInventory().findSlotMatchingItem(stack);
					stack = LaserItemUtils.setExtended(stack, true);
					player.setItemInHand(hand, stack);
					{
						CompoundTag tag = stack.getTag();
						tag.putFloat("distance", i);
						stack.setTag(tag);
						player.setItemInHand(hand, stack);
					}
					PacketHandler.sendToServer(new PacketLaserToolTagUpdate(slot, stack.getTag()));
					
		            Minecraft.getInstance().level.destroyBlockProgress(Minecraft.getInstance().player.getId(), currentPos, (int)(destroyProgress * 10.0F) - 1);
		            Minecraft.getInstance().levelRenderer.destroyBlockProgress(player.getId(), bpos, (int)(destroyProgress * 10.0F) - 1);
		            
		            if (destroyProgress >= 1.0F) {
		            	PacketHandler.sendToServer(new PacketMultiToolLaserBreakBlock(bpos, hand));
		                game.destroyBlock(bpos);
		                destroyProgress = 0.0F;
		                destroyTicks = 0.0F;
		             }
		            return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerHarvestCheck(PlayerEvent.HarvestCheck event) {
		if(event.getEntity().getMainHandItem().getItem() == ModItems.LaserMultiTool.get()) {
//			Player player = event.getPlayer();
			event.setCanHarvest(true);
		}
	}
	
	@SubscribeEvent
	public void onBreakSpeedCheck(PlayerEvent.BreakSpeed event) {
		if(event.getEntity().getMainHandItem().getItem() == ModItems.LaserMultiTool.get()) {
			LaserProperties properties = LaserItemUtils.getProperties(event.getEntity().getMainHandItem());
			event.setNewSpeed(properties.getProperty(Properties.SPEED));
//			event.setNewSpeed(1.4f);
		}
	}
	
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Pre event) {
		Player player = event.getEntity();
		InteractionHand hand = getHandWithItem(player);
		if(hand != null) {
			ItemStack stack = player.getItemInHand(hand);
			PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
			if(LaserItemUtils.isExtended(stack)) {
				HumanoidArm arm = hand == InteractionHand.OFF_HAND ? player.getMainArm().getOpposite() : player.getMainArm();
				if(arm == HumanoidArm.RIGHT)
					model.rightArmPose = ArmPose.BOW_AND_ARROW;
				else
					model.leftArmPose = ArmPose.BOW_AND_ARROW;
			}
		}
	}
	
	private InteractionHand getHandWithItem(Player player) {
		if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.LaserMultiTool.get())
			return InteractionHand.MAIN_HAND;
		else if(player.getItemInHand(InteractionHand.OFF_HAND).getItem() == ModItems.LaserMultiTool.get())
			return InteractionHand.OFF_HAND;
		return null;
	}
	
}
