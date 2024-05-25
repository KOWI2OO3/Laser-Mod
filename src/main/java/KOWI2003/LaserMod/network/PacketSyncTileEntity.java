package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncTileEntity {
    
    public BlockPos pos;
    public CompoundTag tag;
	
	public PacketSyncTileEntity(FriendlyByteBuf buf) {
		pos = buf.readBlockPos();
        tag = buf.readNbt();
	}
	
	public PacketSyncTileEntity(BlockEntity tile) {
		this.pos = tile.getBlockPos();
        tag = tile.serializeNBT();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeNbt(tag);
	}
	
	@SuppressWarnings("null")
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packets
	        
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                BlockEntity tile = sender.getLevel().getBlockEntity(pos);
				if(tile != null)
                	tile.load(tag);
				
				for(ServerPlayer player : sender.getServer().getPlayerList().getPlayers())
					PacketHandler.sendToClient(this, player);
	        }else if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	handleClient();
	    	}
	    });
	    ctx.get().setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("resource")
	private void handleClient() {
		Level level = Minecraft.getInstance().level;
		if(level == null) return;

		BlockEntity tile = level.getBlockEntity(pos);
		if(tile != null)
			tile.load(tag);
	}

}
