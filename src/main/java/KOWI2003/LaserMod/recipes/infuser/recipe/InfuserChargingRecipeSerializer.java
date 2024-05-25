package KOWI2003.LaserMod.recipes.infuser.recipe;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Just a simple dummy serializer class for the InfuserRecipeChargingTool
 */
public class InfuserChargingRecipeSerializer implements RecipeSerializer<InfuserRecipeChargingTool> {

    @Override
    public InfuserRecipeChargingTool fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        throw new UnsupportedOperationException("Unimplemented method 'fromJson'");
    }

    @Override
    public @Nullable InfuserRecipeChargingTool fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        return new InfuserRecipeChargingTool();
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull InfuserRecipeChargingTool recipe) {}
    
}
