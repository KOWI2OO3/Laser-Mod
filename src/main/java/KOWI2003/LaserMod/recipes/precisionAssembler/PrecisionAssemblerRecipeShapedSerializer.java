package KOWI2003.LaserMod.recipes.precisionAssembler;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import KOWI2003.LaserMod.utils.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class PrecisionAssemblerRecipeShapedSerializer implements RecipeSerializer<PrecisionAssemblerRecipeShaped> {

    @Override
    public PrecisionAssemblerRecipeShaped fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        if(!(json.has("inputs") && json.has("base_input") && json.has("output")))
            throw new JsonParseException("precision assembler recipe requires input, base_input and output tags to be present");
        if(!json.get("inputs").isJsonArray())
            throw new JsonParseException("precision assembler requires inputs to be an array");

        var jsonInputs = json.get("inputs").getAsJsonArray();
        
        var inputs = new Ingredient[jsonInputs.size()];
        for (int i = 0; i < jsonInputs.size(); i++) {
            try {
                inputs[i] = Ingredient.fromJson(jsonInputs.get(i));
            }catch(JsonParseException ex) {
                inputs[i] = Ingredient.EMPTY;
            }
        }

        var inputBase = Ingredient.EMPTY;
        try {
            inputBase = Ingredient.fromJson(json.get("base_input"));
        }catch(JsonParseException ex) {}

        var output = JsonUtils.deserializeItemStack(json.get("output"))
            .orElseThrow(() -> new JsonParseException("Failed to parse output item"));

        float speed = 1;
        if(json.has("speed"))
            speed = json.get("speed").getAsFloat();

        return new PrecisionAssemblerRecipeShaped(recipeId, output, speed, inputBase, inputs);
    }

    @Override
    public @Nullable PrecisionAssemblerRecipeShaped fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        var inputs = new Ingredient[buffer.readVarInt()];
        for (int i = 0; i < inputs.length; i++)
            inputs[i] = Ingredient.fromNetwork(buffer);

        var inputBase = Ingredient.fromNetwork(buffer);
        var output = buffer.readItem();
        var speed = buffer.readFloat();

        return new PrecisionAssemblerRecipeShaped(recipeId, output, speed, inputBase, inputs);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull PrecisionAssemblerRecipeShaped recipe) {
        var inputs = recipe.getInputs();
        buffer.writeVarInt(inputs.length);
        for (var input : inputs)
            input.toNetwork(buffer);
        
        recipe.getInputBase().toNetwork(buffer);
        buffer.writeItem(recipe.getOutput());
        buffer.writeFloat(recipe.getRecipeSpeed());
    }
    
}
