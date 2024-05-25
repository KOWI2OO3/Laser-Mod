package KOWI2003.LaserMod.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	@SuppressWarnings("deprecation")
	public InteractionResult use(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Player player,
			@Nonnull InteractionHand hand, @Nonnull BlockHitResult raytraceResult) {
		return super.use(state, world, pos, player, hand, raytraceResult);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos,
			@Nonnull CollisionContext context) {
		return super.getShape(state, world, pos, context);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos,
			@Nonnull CollisionContext context) {
		return super.getCollisionShape(state, world, pos, context);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getVisualShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos,
			@Nonnull CollisionContext context) {
		return super.getVisualShape(state, world, pos, context);
	}
	
	@Override
	public abstract BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state);
	
	@Override
	public void playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
		if(!player.isCreative())
			popResource(world, pos, new ItemStack(this));
		super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public void playerDestroy(@Nonnull Level world, @Nonnull Player player, @Nonnull BlockPos pos, @Nonnull BlockState state,
			@Nullable BlockEntity blockentity, @Nonnull ItemStack stack) {
		super.playerDestroy(world, player, pos, state, blockentity, stack);
	}
}
