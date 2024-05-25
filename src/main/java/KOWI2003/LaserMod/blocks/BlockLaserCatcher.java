package KOWI2003.LaserMod.blocks;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.tileentities.TileEntityLaserCatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockLaserCatcher extends BlockRotatable {
	
	public BlockLaserCatcher() {
		super(Material.STONE);
		setModelPlacement(false);
	}
	
	public BlockLaserCatcher(Properties properties) {
		super(properties);
		setModelPlacement(false);
	}
	
	@Override
	public int getSignal(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull Direction direction) {
		BlockEntity te = world.getBlockEntity(pos);
		if(te instanceof TileEntityLaserCatcher)
			return ((TileEntityLaserCatcher) te).isHit ? 15 : 0;
		return 0;
	}

	@Override
	public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
		return true;
	}
	
	@Override
	public boolean isSignalSource(@Nonnull BlockState state) {
		return true;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityLaserCatcher(pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState p_49232_) {
		return RenderShape.MODEL;
	}
}
