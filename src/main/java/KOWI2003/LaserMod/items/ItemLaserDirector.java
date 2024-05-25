package KOWI2003.LaserMod.items;

import javax.annotation.Nonnull;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemLaserDirector extends ItemDefault {

	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		Block block = context.getLevel().getBlockState(pos).getBlock();
		if(block == ModBlocks.AdvancedLaser.get() && hasPos(stack)) {
			BlockEntity tile = context.getLevel().getBlockEntity(pos);
			if(tile instanceof TileEntityAdvancedLaser) {
				((TileEntityAdvancedLaser) tile).setDirection(getPos(stack));
				return InteractionResult.SUCCESS;
			}
		}
		
		if(player == null)
			return InteractionResult.FAIL;

		if(hasPos(stack) && player.isShiftKeyDown()) {
			stack = setPos(stack, pos);
			player.setItemInHand(context.getHand(), stack);
			return InteractionResult.SUCCESS;
		}
		
		stack = setPos(stack, pos);
		player.setItemInHand(context.getHand(), stack);
		return InteractionResult.SUCCESS;
	}
	
	public static ItemStack setPos(ItemStack stack, BlockPos pos) {
		return setPos(stack, new Vector3f(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f));
	}
	
	public static ItemStack setPos(ItemStack stack, Vector3f pos) {
		CompoundTag nbt = stack.getOrCreateTag();
		if(pos == null) {
			if(nbt.contains("pos"))
				nbt.remove("pos");
			return stack;
		}
		CompoundTag posNbt = new CompoundTag();
		posNbt.putDouble("x", pos.x());
		posNbt.putDouble("y", pos.y());
		posNbt.putDouble("z", pos.z());
		nbt.put("pos", posNbt);
		stack.setTag(nbt);
		return stack;
	}
	
	public static Vector3f getPos(ItemStack stack) {
		CompoundTag nbt = stack.getOrCreateTag();
		if(nbt.contains("pos")) {
			CompoundTag posNbt = nbt.getCompound("pos");
			if(posNbt.contains("x") && posNbt.contains("y") && posNbt.contains("z")) 
				return new Vector3f((float)posNbt.getDouble("x"), (float)posNbt.getDouble("y"), (float)posNbt.getDouble("z"));	
		}
		return null;
	}
	
	public static boolean hasPos(ItemStack stack) {
		return getPos(stack) != null;
	}
	
}
