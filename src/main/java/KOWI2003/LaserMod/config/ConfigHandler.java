package KOWI2003.LaserMod.config;

import java.lang.reflect.Field;
import java.util.List;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * Handles object based configuration for forge config of the mod.
 * *Adapted version from the KOWI-Core library*
 * 
 * @author KOWI2003
 */
@Mod.EventBusSubscriber(bus = Bus.MOD, modid = Reference.MODID)
public class ConfigHandler {
    
    static Config instance = new Config();
    static ClientConfig client = new ClientConfig();

    public static void register() {
		ModLoadingContext context = ModLoadingContext.get();
        registerConfig(instance, context, Type.COMMON);
        registerConfig(client, context, Type.CLIENT);
    }

    private static <T> void constructBuiler(T config, Builder builder) {
		for(Field field : config.getClass().getFields()) {
			String name = field.getName();
			
			var customName = field.getAnnotation(ConfigName.class);
			name = customName != null ? customName.value() : name;

			ConfigDesc desc = field.getAnnotation(ConfigDesc.class);
			
			if(desc != null)
				builder.comment(desc.value());
			
			ConfigRange range = null;
			try {
				range = field.getAnnotation(ConfigRange.class);
			}catch(NullPointerException ex) {} 
			
			Class<?> type = field.getType();
			
			try {
				if(field.get(config) == null)
					continue;
				
				if(range != null && type == float.class) {
					builder.defineInRange(name, (double)(float)field.get(config), range.min(), range.max());
				}else if(range != null && type == double.class) {
					builder.defineInRange(name, (double)field.get(config), range.min(), range.max());
				}else if(range != null && (type == int.class || type == Integer.class)) {
					builder.defineInRange(name, (int)field.get(config), (int)range.min(), (int)range.max());
				}else if(type == float.class) {
					builder.define(name, (double)(float)field.get(config));
				}else if(Utils.isPrimitiveType(type)) {
					builder.define(name, field.get(config));
				}else if(type == List.class) 
					builder.defineList(name, (List<?>)field.get(config), (e) -> true);
				else if(type.isEnum())
					buildEnum(builder, field, config);
				else {
					builder.push(name);
					constructBuiler(field.get(config), builder);
					builder.pop();
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> void registerConfig(T config, ModLoadingContext context, Type configType) {
		ForgeConfigSpec spec = createForgeConfigSpec(config);
		context.registerConfig(configType, spec);
	}

    @SuppressWarnings("unchecked")
	private static <V extends Enum<V>> void buildEnum(Builder builder, Field field, Object obj) {
		try {
			builder.defineEnum(field.getName(), (V)field.get(obj));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> ForgeConfigSpec createForgeConfigSpec(T config) {
		Builder builder = new ForgeConfigSpec.Builder();
		constructBuiler(config, builder);
		return builder.build();
	}
    
    private static <T> T getConfig(T config, ModConfig cnf, String path) {
		for(Field field : config.getClass().getFields()) {
			String name = (path.isEmpty() ? "" : path + ".") + field.getName();
			
			var customName = field.getAnnotation(ConfigName.class);
			name = customName != null ? customName.value() : name;

			Object value = cnf.getConfigData().get(name);
			if(value != null) {
				try {
					Class<?> type = field.getType();
					if(type == Float.class || type == float.class) {
						field.set(config, (float)(double)value);
					}else if(type == Integer.class || type == int.class) {
						field.set(config, (int)value);
					}else if(type == Double.class || type == double.class) {
						if(value instanceof Float)
							field.set(config, (double)(float)value);
						else
							field.set(config, (double)value);
					}else if(type == String.class) {
						field.set(config, (String)value);
					}else if(type == Boolean.class || type == boolean.class) {
						field.set(config, (boolean)value);
					}else if(type == Short.class || type == short.class) {
						field.set(config, (short)value);
					}else if(type == Long.class || type == long.class) {
						field.set(config, (long)value);
					}else if(type == Character.class || type == char.class) {
						field.set(config, (char)value);
					}else if(type.isEnum()){
						field.set(config, Utils.getEnum(type, (String) value));
					}else if(type == List.class) {
						List<?> list = (List<?>)value;
						field.set(config, list);
					}else if(type.isArray()) {
						List<?> list = (List<?>)value;
						try {
							field.set(config, list.toArray());
						}catch(Exception ex) {
							System.err.println("Failed to load " + name + " from " + cnf.getFileName());
						}
					}else
						field.set(config, getConfig(field.get(config), cnf, name));
				}catch(ClassCastException | IllegalArgumentException | IllegalAccessException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return config;
	}

    
	private static <T> T getConfig(T config, ModConfig cnf) {
		return getConfig(config, cnf, "");
	}
	

    private static void handleConfigLoad(ModConfig config) {
        switch (config.getType()) {
            case COMMON:
                instance = getConfig(instance, config);
                break;
            case CLIENT:
                client = getConfig(client, config);
                break;
        
            default:
                break;
        }
	}

    @SubscribeEvent
	public static void onConfigLoad(ModConfigEvent.Loading load) {
		handleConfigLoad(load.getConfig());
	}
	
	@SubscribeEvent
	public static void onConfigReload(ModConfigEvent.Reloading reload) {
		handleConfigLoad(reload.getConfig());
	}
	

	@Mod.EventBusSubscriber(bus = Bus.FORGE, modid = Reference.MODID)
	public static class ForgeEventHook {
		@SubscribeEvent
		public static void onConfigLoad(ModConfigEvent.Loading load) {
			handleConfigLoad(load.getConfig());
		}
		
		@SubscribeEvent
		public static void onConfigReload(ModConfigEvent.Reloading reload) {
			handleConfigLoad(reload.getConfig());
		}
	}
}
