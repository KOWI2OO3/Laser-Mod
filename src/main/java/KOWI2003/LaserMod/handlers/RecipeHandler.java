package KOWI2003.LaserMod.handlers;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeHandler;
import KOWI2003.LaserMod.recipes.precisionAssembler.PrecisionAssemblerRecipeHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeHandler {
	
	@SubscribeEvent
	public static void register(RegisterEvent event) {
		
		if(event.getForgeRegistry() != null)
		if(event.getForgeRegistry().getRegistryName().equals(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryName())) {
	    	InfuserRecipeHandler.register();
	    	PrecisionAssemblerRecipeHandler.registerRecipes();
		}
		
//    	InfuserRecipeHandler.registerOD();
	}
}
