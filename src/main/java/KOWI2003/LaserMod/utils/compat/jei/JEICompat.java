package KOWI2003.LaserMod.utils.compat.jei;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.gui.GuiInfuser;
import KOWI2003.LaserMod.gui.GuiLaser;
import KOWI2003.LaserMod.gui.GuiModStation;
import KOWI2003.LaserMod.gui.GuiPrecisionAssembler;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.recipes.precisionAssembler.PrecisionAssemblerRecipeHandler;
import KOWI2003.LaserMod.utils.compat.jei.infuser.InfuserRecipeCategory;
import KOWI2003.LaserMod.utils.compat.jei.infuser.InfuserRecipeMaker;
import KOWI2003.LaserMod.utils.compat.jei.precisionAssembler.PrecisionRecipeCategory;
import KOWI2003.LaserMod.utils.compat.jei.slotmovers.LaserGuiSlotMover;
import KOWI2003.LaserMod.utils.compat.jei.slotmovers.ModStationGuiSlotMover;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEICompat implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Reference.MODID);
	}
	
	@Override
	public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {
		final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
		
		registry.addRecipeCategories(new PrecisionRecipeCategory(gui), new InfuserRecipeCategory(gui));
		
		IModPlugin.super.registerCategories(registry);
	}
	
	@Override
	public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.PrecisionAssembler.get()), RecipeCategories.PRECISION_ASSEMBLER);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.Infuser.get()), RecipeCategories.INFUSER);
		
		IModPlugin.super.registerRecipeCatalysts(registration);
	}
	
	@Override
	public void registerRecipes(@Nonnull IRecipeRegistration registry) {
		final IJeiHelpers helpers = registry.getJeiHelpers();

		registry.addRecipes(RecipeCategories.PRECISION_ASSEMBLER, PrecisionAssemblerRecipeHandler.getAllRecipes());
		registry.addRecipes(RecipeCategories.INFUSER, InfuserRecipeMaker.getRecipes(helpers));
		
		IModPlugin.super.registerRecipes(registry);
	}
	
	@Override
	public void registerGuiHandlers(@Nonnull IGuiHandlerRegistration registry) {
		
		registry.addGuiContainerHandler(GuiLaser.class, new LaserGuiSlotMover());
		registry.addGuiContainerHandler(GuiModStation.class, new ModStationGuiSlotMover());

		registry.addRecipeClickArea(GuiPrecisionAssembler.class, 76, 35, 45, 9, RecipeCategories.PRECISION_ASSEMBLER);
		registry.addRecipeClickArea(GuiInfuser.class, 64, 60, 45, 9, RecipeCategories.INFUSER);
		
		IModPlugin.super.registerGuiHandlers(registry);
	}
	
	@Override
	public void registerRecipeTransferHandlers(@Nonnull IRecipeTransferRegistration registration) {
		
		IModPlugin.super.registerRecipeTransferHandlers(registration);
	}

}
