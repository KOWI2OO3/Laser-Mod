package KOWI2003.LaserMod.blocks;

import KOWI2003.LaserMod.tileentities.TileEntityDeviceHub;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDeviceHub extends BlockHorizontal {

	public BlockDeviceHub(Properties properties) {
		super(properties);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityDeviceHub(pos, state);
	}

}
