package KOWI2003.LaserMod.utils;

import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketSyncIColor;
import KOWI2003.LaserMod.tileentities.IColorable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TileEntityUtils {
	
	public static void updateColor(Level world, BlockPos pos, int index) {
		BlockEntity tile = world.getBlockEntity(pos);
		if(!(tile instanceof IColorable))
			return;
		IColorable colorable = (IColorable)tile;
		float[] color = colorable.getColor(index);
		
		if(world.isClientSide)
			PacketHandler.sendToServer(new PacketSyncIColor(pos, color, index));
		else
			for(Player player : world.players())
				if(player instanceof ServerPlayer)
					PacketHandler.sendToClient(new PacketSyncIColor(pos, color, index), (ServerPlayer)player);
			
	}
	
	public static void markBlockForUpdate(Level world, BlockPos pos)
	{
		world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}

	public static void syncColorToClient(BlockEntity tileEntity, int index) {
		syncToClient(tileEntity);
		Level world = tileEntity.getLevel();
        if(world != null)
        {
            BlockPos pos = tileEntity.getBlockPos();
            updateColor(world, pos, index);
        }
	}
	
	public static void syncColorToClient(BlockEntity tileEntity) {
		syncColorToClient(tileEntity, 0);
	}
	
	public static void syncToClient(BlockEntity tileEntity)
    {
        Level world = tileEntity.getLevel();
        if(world != null)
        {
            BlockPos pos = tileEntity.getBlockPos();
            markBlockForUpdate(world, pos);
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos), world.getBlockState(pos), 4, 4);
        }
    }
	
	public static String StringCommands(Player player, String s) {
		if(s.contains("{Player}")) {
        	s = s.replace("{Player}", player.getName().getString());
        }
        
        if(s.contains("{HP}")) {
        	s = s.replace("{HP}", (int)player.getHealth() + "");
        }
        
        if(s.contains("{Hunger}")) {
        	s = s.replace("{Hunger}", player.getFoodData().getFoodLevel() + "");
        }
        return s;
	}
}
