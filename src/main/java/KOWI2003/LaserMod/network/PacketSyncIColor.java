package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.IColorable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncIColor {

	public BlockPos pos;
	public float[] color;
	public int index;
	
	public PacketSyncIColor(FriendlyByteBuf buf) {
		color = new float[] {buf.readFloat(), buf.readFloat(), buf.readFloat()};
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		index = buf.readInt();
	}
	
	public PacketSyncIColor(BlockPos pos, float[] color) {
		this(pos, color, 0);
	}
	
	public PacketSyncIColor(BlockPos pos, float[] color, int index) {
		this.color = color;
		this.pos = pos;
		this.index = index;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeFloat(color[0]);
		buf.writeFloat(color[1]);
		buf.writeFloat(color[2]);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(index);
	}
	
	@SuppressWarnings({ "resource", "null" })
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
	        {
	        	Level level = Minecraft.getInstance().player.getLevel();
	        	BlockEntity tile = level.getBlockEntity(pos);
	        	if(tile instanceof IColorable)
	        		((IColorable)tile).setColor(index, color);
	        }else {
	        	ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	        	ServerLevel level = sender.getLevel();
	        	BlockEntity tile = level.getBlockEntity(pos);
	        	if(tile instanceof IColorable)
	        		((IColorable)tile).setColor(index, color);
	        	
	        	for(Player player : level.players())
					if(player instanceof ServerPlayer)
						PacketHandler.sendToClient(new PacketSyncIColor(pos, color, index), (ServerPlayer)player);
	        }
	        
	    });
	    ctx.get().setPacketHandled(true);
	}
}
