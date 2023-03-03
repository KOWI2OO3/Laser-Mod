package KOWI2003.LaserMod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ContainerBlockDefault extends BaseEntityBlock {
	
	protected ContainerBlockDefault(Properties builder) {
		super(builder);
	}

	public ContainerBlockDefault(Material materialIn) {
		this(Properties.of(materialIn));	//This is Changed
	}
	
	public ContainerBlockDefault() {
		this(Material.STONE);				//This is Changed
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult raytraceResult) {
		return super.use(state, world, pos, player, hand, raytraceResult);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return super.getShape(state, world, pos, context);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return super.getCollisionShape(state, world, pos, context);
	}
	
	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return super.getVisualShape(state, world, pos, context);
	}
	
	@Override
	public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
	
	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state,
			BlockEntity blockentity, ItemStack stack) {
		super.playerDestroy(world, player, pos, state, blockentity, stack);
	}
	
//	@Override
//	public boolean isToolEffective(BlockState state, ToolType tool) {
//		return tool == ToolType.PICKAXE;
//	}
}
