package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketLaser {

	public BlockPos pos;
	public float red,green,blue;
	
	public PacketLaser(FriendlyByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()); 
		this.red = buf.readFloat();
		this.green = buf.readFloat();
		this.blue = buf.readFloat();
	}
	
	public PacketLaser(BlockPos pos, float red, float green, float blue) {
		this.pos = pos;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeFloat(red);
		buf.writeFloat(green);
		buf.writeFloat(blue);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
		        ServerPlayer sender = ctx.get().getSender(); // the client that sent this packets
		        
		        BlockEntity tileentity = sender.getLevel().getBlockEntity(pos);
		        if(tileentity instanceof TileEntityLaser) {
		        	TileEntityLaser te = (TileEntityLaser)tileentity;
		        	te.red = red;
		        	te.green = green;
		        	te.blue = blue;
		        	te.setColor(red, green, blue);
		        }else if(tileentity instanceof TileEntityModStation) {
		        	((TileEntityModStation)tileentity).setColor(red, green, blue);
		        }
	    	}else if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	    		handleClient();
	    	}
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	private void handleClient() {
		Player player = Minecraft.getInstance().player;
		BlockEntity tileentity = player.getLevel().getBlockEntity(pos);
        if(tileentity instanceof TileEntityLaser) {
        	TileEntityLaser te = (TileEntityLaser)tileentity;
        	te.red = red;
        	te.green = green;
        	te.blue = blue;
        	te.setColor(red, green, blue);
        }else if(tileentity instanceof TileEntityModStation) {
        	((TileEntityModStation)tileentity).setColor(red, green, blue);
        }
	}
	
}
