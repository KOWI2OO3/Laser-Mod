package KOWI2003.LaserMod.handlers;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeHandler;
import KOWI2003.LaserMod.recipes.precisionAssembler.PrecisionAssemblerRecipeHandler;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeHandler {
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<RecipeSerializer<?>> event) {
		
    	InfuserRecipeHandler.register();
    	PrecisionAssemblerRecipeHandler.registerRecipes();
		
//    	InfuserRecipeHandler.registerOD();
	}
}
