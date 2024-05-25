package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.events.LaserBlockBreakEvent;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.LaserItem;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketMultiToolLaserBreakBlock {

	public BlockPos pos;
	public InteractionHand hand;
	
	public PacketMultiToolLaserBreakBlock(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		hand = InteractionHand.values()[buf.readInt()];
	}
	
	public PacketMultiToolLaserBreakBlock(BlockPos position, InteractionHand hand) {
		this.pos = position;
		this.hand = hand;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(hand.ordinal());
	}
	
	@SuppressWarnings({ "resource", "null" })
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
				Minecraft.getInstance().level.destroyBlock(pos, false);
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	Level level = sender.level;
	        	BlockState state = level.getBlockState(pos);
				
	        	if(!sender.isCreative() && !LaserBlockBreakEvent.onLaserToolBreakBlock(new BreakEvent(level, pos, state, sender))) {
		        	state.getBlock().playerWillDestroy(level, pos, state, sender);
					state.getBlock().playerDestroy(level, sender, pos, state, level.getBlockEntity(pos), sender.getItemInHand(hand));
	        	}
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
				
				if(!sender.isCreative() && !sender.isSpectator()) {
					ItemStack stack = sender.getItemInHand(hand);
					stack = LaserItemUtils.setCharge(stack, LaserItemUtils.getCharge(stack)-1);
					
					if(stack.getItem() instanceof LaserItem) {
						LaserItem item = (LaserItem)stack.getItem();
						for(ItemUpgradeBase upgrade : item.getProperties(stack).getUpgrades())
							upgrade.runLaserToolBlockBreak(stack, pos, state, sender);
					}
					
					if(LaserItemUtils.getCharge(stack) <= 0)
						stack = LaserItemUtils.setExtended(stack, false);
					
					sender.setItemInHand(hand, stack);
				}
				
				for(ServerPlayer pl : sender.server.getPlayerList().getPlayers())
					PacketHandler.sendToClient(new PacketMultiToolLaserBreakBlock(pos, hand), pl);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	}
	
}
