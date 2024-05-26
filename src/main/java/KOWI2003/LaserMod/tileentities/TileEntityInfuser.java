package KOWI2003.LaserMod.tileentities;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.container.ContainerInfuser;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityInfuser extends SyncableBlockEntity implements BlockEntityTicker<TileEntityInfuser>, MenuProvider {

	public ItemStackHandler handler;
	LaserProperties properties;
	private IInfuserRecipe currentRecipe = null;
	
	private float maxTick = 120;
	private float tick;
	
	public TileEntityInfuser(BlockPos pos, BlockState state) {
		super(ModTileTypes.INFUSER, pos, state);
		handler = new ItemStackHandler(3);
		tick = maxTick;
		properties = new LaserProperties();
		properties.setProperty(Properties.SPEED, 1f);
	}
	
	
	@Override
	public void tick(Level level, BlockPos pos, BlockState state,
			TileEntityInfuser te) {
		if(currentRecipe == null || !currentRecipe.isRecipeValid(this)) {
			IInfuserRecipe recipe = InfuserRecipeHandler.getRecipe(this);
			if(recipe != currentRecipe) {
				tick = maxTick;
				currentRecipe = recipe;
			}
		}
		
		if(currentRecipe != null) {
			tick -= currentRecipe.getRecipeSpeed() * properties.getProperty(Properties.SPEED);
			
			if(tick <= 0) {
				InfuserRecipeHandler.handleRecipeEnd(currentRecipe, this);
				tick = maxTick;
				currentRecipe = null;
			}
		}
		sync();
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inv", handler.serializeNBT());
		nbt.putFloat("tick", tick);
		properties.save(nbt);
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		handler.deserializeNBT(nbt.getCompound("inv"));
		tick = nbt.getFloat("tick");
		properties.load(nbt);
		super.load(nbt);
	}

	public float getProgress() {
		return tick/maxTick;
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return new ContainerInfuser(windowId, playerInv, this);
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("container.lasermod.infuser");
	}
	
	public ItemStackHandler getUpgradeInv() {
		return properties.createHandler(1);
	}
	
	public boolean acceptsItem(ItemUpgradeBase upgrade, boolean simulate) {
		return upgrade.isUsefullForMachine(getBlockState().getBlock()) && (simulate ? properties.doesAllow(upgrade) : properties.addUpgrade(upgrade));
	}
	
	public boolean remove(ItemUpgradeBase upgarde, boolean simulate) {
		return simulate ? properties.hasUpgarde(upgarde) : properties.removeUpgrade(upgarde) != null;
	}

}
