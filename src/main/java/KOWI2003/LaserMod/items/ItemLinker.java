package KOWI2003.LaserMod.items;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.items.render.RenderLinker;
import KOWI2003.LaserMod.tileentities.TileEntityDeviceHub;
import KOWI2003.LaserMod.tileentities.TileEntityLaserController;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemLinker extends ItemDefault {
	
	public ItemLinker() {
		super(new Item.Properties());
	}
	
	@Override
	public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
	  consumer.accept(new IClientItemExtensions() {

		  @Override
		  public BlockEntityWithoutLevelRenderer getCustomRenderer() {
			  return RenderLinker.get();
		  }
	  });
	}
	
	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		Block block = context.getLevel().getBlockState(pos).getBlock();
		if(player == null)
			return InteractionResult.FAIL;
			
		if(block == ModBlocks.Laser.get() || block == ModBlocks.LaserProjector.get() || block == ModBlocks.AdvancedLaser.get()) {
			stack = setPos(stack, pos);
			player.setItemInHand(context.getHand(), stack);
			return InteractionResult.SUCCESS;
		}else if(block == ModBlocks.LaserController.get()) {
			BlockPos laserPos = getPos(stack);
			if(laserPos != null) {
				BlockEntity tile = context.getLevel().getBlockEntity(pos);
				if(tile instanceof TileEntityLaserController) {
					((TileEntityLaserController)tile).link(laserPos);
				
					stack = setPos(stack, null);
					player.setItemInHand(context.getHand(), stack);
					
					return InteractionResult.SUCCESS;
				}
			}
		}else if(block == ModBlocks.DeviceHub.get())
		{
			BlockPos devicePos = getPos(stack);
			if(devicePos != null) {
				BlockEntity tile = context.getLevel().getBlockEntity(pos);
				if(tile instanceof TileEntityDeviceHub) {
					((TileEntityDeviceHub)tile).link(devicePos);
				
					stack = setPos(stack, null);
					player.setItemInHand(context.getHand(), stack);
					
					return InteractionResult.SUCCESS;
				}
			}
		}else if(hasPos(stack) && player.isShiftKeyDown()) {
			stack = setPos(stack, null);
			player.setItemInHand(context.getHand(), stack);
			return InteractionResult.SUCCESS;
		}
		
		
		return super.useOn(context);
	}
	
	public static ItemStack setPos(ItemStack stack, BlockPos pos) {
		CompoundTag nbt = stack.getOrCreateTag();
		if(pos == null) {
			if(nbt.contains("pos"))
				nbt.remove("pos");
			return stack;
		}
		CompoundTag posNbt = new CompoundTag();
		posNbt.putInt("x", pos.getX());
		posNbt.putInt("y", pos.getY());
		posNbt.putInt("z", pos.getZ());
		nbt.put("pos", posNbt);
		stack.setTag(nbt);
		return stack;
	}
	
	public static BlockPos getPos(ItemStack stack) {
		CompoundTag nbt = stack.getOrCreateTag();
		if(nbt.contains("pos")) {
			CompoundTag posNbt = nbt.getCompound("pos");
			if(posNbt.contains("x") && posNbt.contains("y") && posNbt.contains("z")) {
				return new BlockPos(posNbt.getInt("x"), posNbt.getInt("y"), posNbt.getInt("z"));
			}
		}
		return null;
	}
	
	public static boolean hasPos(ItemStack stack) {
		return getPos(stack) != null;
	}
	
}
