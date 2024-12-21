package KOWI2003.LaserMod.recipes.infuser;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import KOWI2003.LaserMod.utils.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import oshi.util.tuples.Pair;

public final class InfuserRecipeSerializer implements RecipeSerializer<InfuserRecipeBase> {

    /**
     * example of the json format:
     * {
     *   "input1": {
     *      "item": "minecraft:redstone"
     *      "count": 1
     *   },
     *   "input2": {},
     *   "output": "minecraft:stone",
     *   "speed": 1
     * }
     */

    @Override
    public InfuserRecipeBase fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        if(!(json.has("inputs") && json.has("output")))
            throw new JsonParseException("Infuser recipe requires input and output tags to be present");
        if(!json.get("inputs").isJsonArray())
            throw new JsonParseException("Infuser recipe requires inputs to be an array");
        
        var inputs = json.get("inputs").getAsJsonArray();
        if(inputs.size() != 2)
            throw new JsonParseException("Infuser recipe requires exactly 2 inputs");

            
        if(!inputs.get(0).isJsonObject() || !inputs.get(1).isJsonObject())
            throw new JsonParseException("Infuser recipe inputs must be an object");
            
        var jsonInput1 = inputs.get(0).getAsJsonObject();
        var jsonInput2 = inputs.get(1).getAsJsonObject();


        Ingredient ingredient = Ingredient.fromJson(jsonInput1);
        int count = 1;
        if(jsonInput1.has("count"))
            count = jsonInput1.get("count").getAsInt();
        
        var input1 = new Pair<>(ingredient, count);

        ingredient = Ingredient.fromJson(jsonInput2);
        count = 1;
        if(jsonInput2.has("count"))
            count = jsonInput2.get("count").getAsInt();
        
        var input2 = new Pair<>(ingredient, count);

        var output = JsonUtils.deserializeItemStack(json.get("output"))
            .orElseThrow(() -> new JsonParseException("Failed to parse output item"));

        float speed = 1;
        if(json.has("speed"))
            speed = json.get("speed").getAsFloat();
        
        return new InfuserRecipeBase(input1, input2, output, speed, recipeId);
    }
    
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public InfuserRecipeBase fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        var inputs = new Pair[buffer.readInt()];
        for(int i = 0; i < inputs.length; i++)
            inputs[i] = new Pair<>(Ingredient.fromNetwork(buffer), buffer.readInt());

        var output = buffer.readItem();
        var speed = buffer.readFloat();

        return new InfuserRecipeBase(inputs[0], inputs[1], output, speed, recipeId);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull InfuserRecipeBase recipe) {
        var inputs = recipe.getInputs(null);
        buffer.writeInt(inputs.length);
        for(var input : inputs) {
            input.getA().toNetwork(buffer);
            buffer.writeInt(input.getB());
        }

        buffer.writeItemStack(recipe.getOutput(), false);
        buffer.writeFloat(recipe.getRecipeSpeed());
    }
    
}
