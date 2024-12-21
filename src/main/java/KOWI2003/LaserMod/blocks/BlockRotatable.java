package KOWI2003.LaserMod.blocks;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;

public abstract class BlockRotatable extends ContainerBlockDefault {

	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	private boolean ModelPlacement = false;
	
	public BlockRotatable(Material materialIn) {
		super(Properties.of(materialIn));
	}
	
	public BlockRotatable(Properties properties) {
		super(properties);
	}
	
	@Override
	protected void createBlockStateDefinition(@Nonnull Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	@SuppressWarnings("null")
	public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
		if(!this.ModelPlacement) {
			float rot = context.getPlayer().getRotationVector().x;
			if(rot > 90 - 45.5f)
				return super.getStateForPlacement(context).setValue(FACING, Direction.DOWN);
			else if(rot < -90 + 45.5f)
					return super.getStateForPlacement(context).setValue(FACING, Direction.UP);
			return super.getStateForPlacement(context).setValue(FACING, context.getPlayer().getDirection());
		}else if(this.ModelPlacement)
			return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
		else
			return super.getStateForPlacement(context);
	}
	
	public void setModelPlacement(boolean modelPlacement) {
		ModelPlacement = modelPlacement;
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos,
			Player player) {
		return new ItemStack(this, 1);
	}
	
}
