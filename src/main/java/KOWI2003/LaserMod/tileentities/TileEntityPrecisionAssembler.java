package KOWI2003.LaserMod.tileentities;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.config.Config;
import KOWI2003.LaserMod.container.ContainerPrecisionAssembler;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.recipes.precisionAssembler.IPrecisionAssemblerRecipe;
import KOWI2003.LaserMod.recipes.precisionAssembler.PrecisionAssemblerRecipeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class TileEntityPrecisionAssembler extends SyncableBlockEntity implements BlockEntityTicker<TileEntityPrecisionAssembler>, MenuProvider {

	IPrecisionAssemblerRecipe currentRecipe = null;
	public ItemStackHandler handler;

	public LaserProperties properties;
	
	private float maxTick = 120;
	private float tick;
	
	public TileEntityPrecisionAssembler(BlockPos pos, BlockState state) {
		super(ModTileTypes.PRECISION_ASSEMBLER, pos, state);
		handler = new ItemStackHandler(5);
		properties = new LaserProperties();
		properties.setProperty(Properties.SPEED, 1f);
	}
	
	@Override
	protected void saveAdditional(@Nonnull CompoundTag nbt) {
		nbt.put("inv", handler.serializeNBT());
		nbt.putFloat("tick", tick);
		properties.save(nbt);
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		if(nbt.contains("inv"))
			handler.deserializeNBT(nbt.getCompound("inv"));
		if(nbt.contains("tick"))
			tick = nbt.getFloat("tick");
		properties.load(nbt);
		super.load(nbt);
	}

	@Override
	@SuppressWarnings("null")
	public void tick(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state,
			@Nonnull TileEntityPrecisionAssembler tile) {
		if(currentRecipe == null || !currentRecipe.matches(new RecipeWrapper(this.getHandler()), getLevel())) {
			IPrecisionAssemblerRecipe recipe = PrecisionAssemblerRecipeHandler.getRecipe(this);
			if(recipe != currentRecipe) {
				tick = maxTick;
				currentRecipe = recipe;
			}
		}
		
		if(currentRecipe != null) {
			tick -= currentRecipe.getRecipeSpeed() * properties.getProperty(Properties.SPEED) * Config.GetInstance().machineSettings.precisionAssemblerSpeed;
			
			if(tick <= 0) {
				PrecisionAssemblerRecipeHandler.handleRecipeEnd(currentRecipe, this);
				tick = maxTick;
				currentRecipe = null;
			}
		}
		sync();
	}
	
	public float getProgress() {
		return tick/maxTick;
	}
	
	public ItemStackHandler getHandler() {
		return handler;
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInv, @Nonnull Player player) {
		return new ContainerPrecisionAssembler(windowId, playerInv, this);
	}

	@Override
	public Component getDisplayName() {
		return MutableComponent.create(new TranslatableContents("container.lasermod.precision_assembler"));
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
