package KOWI2003.LaserMod.init;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum ModRecipeTypes {
    INFUSER(InfuserRecipeSerializer::new);
    
    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    private ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = name().toLowerCase(Locale.ROOT);
        id = new ResourceLocation(Reference.MODID, name);
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        if(registerType) {
			typeObject = Registers.TYPE_REGISTER.register(name, typeSupplier);
			type = typeObject;
		}else {
			typeObject = null;
			type = typeSupplier;
		}
    }

    private ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
		String name = name().toLowerCase(Locale.ROOT);
		id = new ResourceLocation(Reference.MODID, name);
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
		typeObject = Registers.TYPE_REGISTER.register(name, () -> simpleType(id));
		type = typeObject;
	}

	public ResourceLocation getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public <T extends RecipeSerializer<?>> T getSerializer() {
		return (T) serializerObject.get();
	}

	@SuppressWarnings("unchecked")
	public <T extends RecipeType<?>> T getType() {
		return (T) type.get();
	}

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
		return world.getRecipeManager()
			.getRecipeFor(getType(), inv, world);
	}

    /**
     * Creates a simple recipe type based on the given resource location
     * @param <T> The type of recipe
     * @param id The resource location
     * @return The recipe type
     */
	public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
		String stringId = id.toString();
		return new RecipeType<T>() {
			@Override
			public String toString() {
				return stringId;
			}
		};
	}

    public static void register(IEventBus modEventBus) {
		Registers.SERIALIZER_REGISTER.register(modEventBus);
		Registers.TYPE_REGISTER.register(modEventBus);
	}
    
	public static class Registers {
		public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MODID);
		public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MODID);
	}
}
