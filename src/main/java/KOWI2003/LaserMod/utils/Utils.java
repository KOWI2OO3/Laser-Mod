package KOWI2003.LaserMod.utils;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import KOWI2003.LaserMod.MainMod;
import KOWI2003.LaserMod.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

public class Utils {
	
	private static Logger logger;
	private static Lang lang;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MODID);
			
		}
		return logger;
	}
	
	public static Lang getlang() {
		if(lang == null) {
			lang = new Lang(Reference.MODID);
		}
		
		return lang;
	}
	
	public static String readFile(File file) {
		try {
			return Files.readString(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean isClient;
	
	public static int calculateRedstone(IItemHandler handler) {
		int i = 0;
		float f = 0.0F;
		for (int j = 0; j < handler.getSlots(); j++) {
			ItemStack stack = handler.getStackInSlot(j);
			if (!stack.isEmpty()) {
				f += (float) stack.getCount() / (float) Math.min(handler.getSlotLimit(j), stack.getMaxStackSize());
				i++;
			}
		}
		f = f / (float) handler.getSlots();
		return (int)(Math.floor(f * 14.0F) + (i > 0 ? 1 : 0));
	}
	
	public static ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
		ItemStack remainder = stack;
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			remainder = handler.insertItem(slot, stack, simulate);
			if (remainder == ItemStack.EMPTY)
				break;
		}
		return remainder;
	}
	
	public static ItemStack addStackToInventory(IItemHandler handler, int maxSlot, ItemStack stack, boolean simulate) {
		ItemStack remainder = stack;
		for (int slot = 0; slot < maxSlot; slot++) {
			remainder = handler.insertItem(slot, stack, simulate);
			if (remainder == ItemStack.EMPTY)
				break;
		}
		return remainder;
	}
	
	public static boolean isInventoryFull(IItemHandler handler) {
		int filledSlots = 0;
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			if (handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot))
				filledSlots++;
		}
		return filledSlots == handler.getSlots();
	}
	
	public static boolean isInventoryFull(IItemHandler handler, int maxSlot) {
		int filledSlots = 0;
		for (int slot = 0; slot < maxSlot; slot++) {
			if (handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot))
				filledSlots++;
		}
		return filledSlots == maxSlot;
	}
	
	public static int getValueOf(boolean bool) {
		if(bool) 
			return 1;
		else if(!bool)
			return 0;
		else
			return 0;
	}
	
	public static boolean getBooleanOf(int value) {
		if(value == 1) 
			return true;
		else if(value == 0)
			return false;
		else
			return false;
	}
	
	public static String getHexStringFromRGB(float Red, float Green, float Blue) {
		try {
			Color hC = new Color(Red, Green, Blue);
		    String hex = Integer.toHexString(hC.getRGB() & 0xffffff);
		    return hex;
		}catch (Exception e) {
			return "FFFFFF";
		}
	}
	
	public static String getHexStringFromRGBA(float Red, float Green, float Blue, float Alpha) {
		Color hC = new Color(Red, Green, Blue);
		String alphaHex = Integer.toHexString(Math.round(Alpha*255) & 0xff);
	    String hex = Integer.toHexString(hC.getRGB() & 0xffffff);
	    return alphaHex + hex;
	}
	
	public static String getHexStringFromRGB(int[] RGB) {
		Color hC = new Color(RGB[0], RGB[1], RGB[2]);
	    String hex = Integer.toHexString(hC.getRGB() & 0xffffff);
	    return hex;
	}
	
	public static String getHexStringFromRGBA(int[] RGBA) {
		Color hC = new Color(RGBA[0], RGBA[1], RGBA[2]);
		String alphaHex = Integer.toHexString(Math.round(RGBA[3]*255) & 0xff);
	    String hex = Integer.toHexString(hC.getRGB() & 0xffffff);
	    return alphaHex + hex;
	}
	
	public static int getHexIntFromRGB(float Red, float Green, float Blue) {
		String hex = getHexStringFromRGB(Red, Green, Blue);
		return Integer.parseInt(hex, 16);
	}
	
	public static int getHexIntFromRGBA(float Red, float Green, float Blue, float Alpha) {
		return Math.round(Red*255f) << 16 | Math.round(Green*255f) << 8 | Math.round(Blue*255f) | Math.round(Alpha*255f) << 24;
	}
	
	public static int getHexIntFromRGBA(float[] RGBA) {
		return getHexIntFromRGBA(
				RGBA.length > 0 ? RGBA[0] : 1.0f, 
				RGBA.length > 1 ? RGBA[1] : 1.0f, 
				RGBA.length > 2 ? RGBA[2] : 1.0f, 
				RGBA.length > 3 ? RGBA[3] : 1.0f);
	}
	
	public static int getHexIntFromRGB(float[] RGBA) {
		return getHexIntFromRGB(
				RGBA.length > 0 ? RGBA[0] : 1.0f, 
				RGBA.length > 1 ? RGBA[1] : 1.0f, 
				RGBA.length > 2 ? RGBA[2] : 1.0f);
	}
	
	public static float[] getFloatRGBAFromHexInt(int hex) {
		return new float[] {
				(float)(hex >> 16 & 255) / 255.0F,
				(float)(hex >> 8 & 255) / 255.0F,
				(float)(hex & 255) / 255.0F,
				(float)(hex >> 24 & 255) / 255.0F};
	}
	
	public static int[] getIntRGBAFromHexInt(int hex) {
		return new int[] {
				(hex >> 16 & 255),
				(hex >> 8 & 255),
				(hex & 255),
				(hex >> 24 & 255)};
	}
	
	public static int getHexFromInt(int value) {
		return Integer.parseInt(Integer.toHexString(Math.round(value*255) & 0xff), 16);
	}
//	public static EnumDyeColor getColourFromDye(ItemStack stack) {
//		for(int id : OreDictionary.getOreIDs(stack)) {
//			if(id == OreDictionary.getOreID("dyeBlack")) return EnumDyeColor.BLACK;
//			if(id == OreDictionary.getOreID("dyeRed")) return EnumDyeColor.RED;
//			if(id == OreDictionary.getOreID("dyeGreen")) return EnumDyeColor.GREEN;
//			if(id == OreDictionary.getOreID("dyeBrown")) return EnumDyeColor.BROWN;
//			if(id == OreDictionary.getOreID("dyeBlue")) return EnumDyeColor.BLUE;
//			if(id == OreDictionary.getOreID("dyePurple")) return EnumDyeColor.PURPLE;
//			if(id == OreDictionary.getOreID("dyeCyan")) return EnumDyeColor.CYAN;
//			if(id == OreDictionary.getOreID("dyeLightGray")) return EnumDyeColor.SILVER;
//			if(id == OreDictionary.getOreID("dyeGray")) return EnumDyeColor.GRAY;
//			if(id == OreDictionary.getOreID("dyePink")) return EnumDyeColor.PINK;
//			if(id == OreDictionary.getOreID("dyeLime")) return EnumDyeColor.LIME;
//			if(id == OreDictionary.getOreID("dyeYellow")) return EnumDyeColor.YELLOW;
//			if(id == OreDictionary.getOreID("dyeLightBlue")) return EnumDyeColor.LIGHT_BLUE;
//			if(id == OreDictionary.getOreID("dyeMagenta")) return EnumDyeColor.MAGENTA;
//			if(id == OreDictionary.getOreID("dyeOrange")) return EnumDyeColor.ORANGE;
//			if(id == OreDictionary.getOreID("dyeWhite")) return EnumDyeColor.WHITE;
//		}
//		return EnumDyeColor.WHITE;
//	}
	
	/**
	 * gets the latest Version of the mod from the github update json
	 * 
	 * @return String
	 */
	public static String getLatestVersion() {
		try {
			JsonObject jObj = WebUtils.getJsonObj(Reference.UPDATE_URL);
			JsonObject versions = JsonUtils.getJsonObject(jObj, "version");
			String s = JsonUtils.getValue(versions, Reference.MCVERSION + "-latest");
			return s;
		}catch (NullPointerException e) {
			return Reference.VESRION;
		}
	}
	
	/**
	 * gets the recommended Version of the mod from the github update json
	 * 
	 * @return String
	 */

	public static String getRecommendedVersion() {
		try {
			JsonObject jObj = WebUtils.getJsonObj(Reference.UPDATE_URL);
			JsonObject versions = JsonUtils.getJsonObject(jObj, "version");
			String s = JsonUtils.getValue(versions, Reference.MCVERSION + "-recommended");
			return s;
		}catch (Exception e) {
			return Reference.VESRION;
		}
	}
	
	/**
	 * tells you wether or not the mod is up to date
	 * @return boolean
	 */
	public static boolean isNewestVersion() {
		String s = getLatestVersion();
		return s.equals(Reference.VESRION);
	}
	
	/**
	 * tells you wether or not the mod is up to date To the recommended version
	 * @return boolean
	 */
	public static boolean isRecommendedVersion(boolean checkRecommended) {
		String s = getLatestVersion();
		if(checkRecommended) {
			String sr = getRecommendedVersion();
			if(s.equals(Reference.VESRION)) {
				return true;
			}else
				return sr.equals(Reference.VESRION);
		}else
			return s.equals(Reference.VESRION);
	}
	
	/**
	 * this methode rotates the aabb 180 degree Horizontaly
	 * 
	 * @param AABB
	 * @return
	 */
	public static AABB oppositeAABB(AABB AABB) {
		double x1,x2,y1,y2,z1,z2;
		x1 = AABB.minX * -1d;
		x2 = AABB.maxX * -1d;
		z1 = AABB.minZ * -1d;
		z2 = AABB.maxZ * -1d;
		y1 = AABB.minY;
		y2 = AABB.maxY;
		return new AABB(x2, y1, z2, x1, y2, z1).move(1, 0, 1);
	}
	
	public static AABB rotateAABB(AABB AABB, Direction facing) {
		double  x1,x2,z1,z2;
		x1 = AABB.minX;
		x2 = AABB.maxX;
		z1 = AABB.minZ;
		z2 = AABB.maxZ;
		double[] bounds = fixRotation(facing, x1, z1, x2, z2);
        return new AABB(bounds[0], AABB.minY, bounds[1], bounds[2], AABB.maxY, bounds[3]);
	}
	
	public static List<AABB> rotateAABB(List<AABB> AABB, Direction facing) {
		List<AABB> boxes = new ArrayList<AABB>();
		for(AABB box: AABB) {
			boxes.add(rotateAABB(box, facing));
		}
		return boxes;
	}
	
	/**
	 * this methode rotates All of the AABBs in a List 180 degree Horizontaly
	 * 
	 * @param AABB
	 * @return
	 */
	public static List<AABB> oppositeAABB(List<AABB> AABB) {
		List<AABB> boxes = new ArrayList<AABB>();
		for(AABB box: AABB) {
			boxes.add(oppositeAABB(box));
		}
		return boxes;
	}
	
	private static double[] fixRotation(Direction facing, double var1, double var2, double var3, double var4)
    {
        switch(facing)
        {
            case WEST:
                double var_temp_1 = var1;
                var1 = 1.0F - var3;
                double var_temp_2 = var2;
                var2 = 1.0F - var4;
                var3 = 1.0F - var_temp_1;
                var4 = 1.0F - var_temp_2;
                break;
            case NORTH:
                double var_temp_3 = var1;
                var1 = var2;
                var2 = 1.0F - var3;
                var3 = var4;
                var4 = 1.0F - var_temp_3;
                break;
            case SOUTH:
                double var_temp_4 = var1;
                var1 = 1.0F - var4;
                double var_temp_5 = var2;
                var2 = var_temp_4;
                double var_temp_6 = var3;
                var3 = 1.0F - var_temp_5;
                var4 = var_temp_6;
                break;
            default:
                break;
        }
        return new double[]{var1, var2, var3, var4};
    }
	
	public static VoxelShape getShapeFromAABB(List<AABB> aabbs) {
		List<VoxelShape> shapes = new ArrayList<>();
		for(AABB aabb : aabbs) {
			shapes.add(Shapes.create(aabb));
		}
		VoxelShape result = Shapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
	    }
	    return result.optimize();
	}
	
	public static VoxelShape rotateVoxelShape(VoxelShape shape, Direction facing) {
		List<AABB> aabbs = shape.toAabbs();
		List<AABB> newAabbs = Utils.rotateAABB(aabbs, facing);
		return getShapeFromAABB(newAabbs);
	}
	
	public static VoxelShape getShapeFromAABB(AABB AABB) {
		return Shapes.create(AABB);
	}
	
	public static Object getPrivateMember(Object object, String fieldName) {
		
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for(Field field: fields) {
				if(field.getName() == fieldName) {
					field.setAccessible(true);
						return field.get(object);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static void setPrivateMember(Object object, String fieldName, Object value) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for(Field field: fields) {
				if(field.getName() == fieldName) {
					field.setAccessible(true);
					field.set(object, value);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void usePrivateMethod(Object object, String methodName, Object... args) {
		try {
			Method[] methods = object.getClass().getDeclaredMethods();
			for(Method method: methods) {
				method.setAccessible(true);
				if(method.getName() == methodName) {
					method.invoke(object, args);
				}
			}
		}catch(Exception e) {}
	}
	
	public static int getOpposite(int value) {
		if(value == 1)
			return 0;
		if(value == 0)
			return 1;
		return value;
	}
	
	public static BlockPos offset(BlockPos pos, Direction facing, float distance) {
		if(facing == null)
			return pos;
		return pos.offset(facing.getStepX() * distance, facing.getStepY() * distance, facing.getStepZ() * distance);
	}
	
	public static boolean isBlockPowered(BlockPos pos, Level world) { 
		return world.hasNeighborSignal(pos);
	}
	
	public static String getFormalText(String input) {
		if(input.contains("_")) {
			String[] ss = input.split("_");
			if(ss.length > 0) {
				String s = "";
				for (int i = 0; i < ss.length; i++) {
					s += makeFirstCaps(ss[i]) + " ";
				}
				if(s.length() > 0)
					s = s.substring(0, s.length()-1);
				return s;
			}
			return makeFirstCaps(input);
		}
		return makeFirstCaps(input);
	}
	
	public static String fromFormalText(String input) {
		String s = input.replace(" ", "_");
		return s.toUpperCase();
	}
	
	public static String makeFirstCaps(String input) {
		if(input.length() <= 0)
			return input;
		String s = input.toLowerCase();
		s = s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toUpperCase());
		return s;
	}
	
	public static GameProfile getProfile(String username) {
		if(username.isEmpty())
			return null;
		GameProfile gameprofile = new GameProfile((UUID)null, username);
		GenericConsumer<GameProfile> profile = new GenericConsumer<GameProfile>();
        SkullBlockEntity.updateGameprofile(gameprofile, profile);
        return profile.getStored();
	}
	
	public static GenericConsumer<GameProfile> updateProfileConsumer(String username, GenericConsumer<GameProfile> consumer) {
		if(username.isEmpty())
			return null;
		GameProfile gameprofile = new GameProfile((UUID)null, username);
		consumer.accept(gameprofile);
		try {
			SkullBlockEntity.updateGameprofile(gameprofile, consumer);
		}catch(Exception ex) {}
        return consumer;
	}
	
	public static Player getPlayer(Level world, String username)  {
		Player player = null;
		for (Player pl : world.players()) {
			if(pl.getName().getString().equals(username)) {
				player = pl;
				break;
			}
		}
		return player;
	}
	
	public static CompoundTag putObjectArray(Object[] array) {
		return putObjectArray(new CompoundTag(), array);
	}
	
	public static CompoundTag putObjectArray(CompoundTag nbt, Object[] array) {
		for (int i = 0; i < array.length; i ++) {
			Object ob = array[i];
			if(ob instanceof Boolean)
				nbt.putBoolean(i + "",(boolean)ob);
			else if(ob instanceof String)
				nbt.putString(i + "", (String)ob);
			else if(ob instanceof Integer)
				nbt.putInt(i + "", (int)ob);
			else if(ob instanceof Float)
				nbt.putFloat(i + "", (float)ob);
			else if(ob instanceof Double)
				nbt.putDouble(i + "", (double)ob);
			else if(ob instanceof int[])
				nbt.putIntArray(i +"", (int[])ob);
			else if(ob instanceof INBTSerializable<?>)
				nbt.put(i + "", ((INBTSerializable<?>)ob).serializeNBT());
		}
		return nbt;
	}
	
	public static Object[] getObjectArray(CompoundTag nbt) {
		List<Object> objs = new ArrayList<Object>();
		for (String key : nbt.getAllKeys()){
			String type = nbt.get(key).getType().getName();
			if(type.equals("STRING")) {
				objs.add(nbt.getString(key));
			}else if(type.equals("BYTE")) {
				objs.add(nbt.getBoolean(key));
			}else if(type.equals("DOUBLE")) {
				objs.add(nbt.getDouble(key));
			}else if(type.equals("FLOAT")) {
				objs.add(nbt.getFloat(key));
			}else if(type.equals("INT")) {
				objs.add(nbt.getInt(key));
			}else if(type.equals("INT[]")) {
				objs.add(nbt.getIntArray(key));
			}else if(type.equals("COMPOUND"))
				objs.add(nbt.getCompound(key));
		}
		return objs.toArray();
	}
	
	public static void printFields(Object obj, boolean includeFieldName) {
		for(Field field : obj.getClass().getFields()) {
			try {
				String s = "null";
				if(field.get(obj) != null)
					s = field.get(obj).toString();
				System.out.println((includeFieldName ? field.getName() : "") + ": " + s);
			} catch (Exception e) {
				System.err.println("Failed to read field '" + field.getName() + "'");
			}
		}
	}
	
	public static float[] parseColor(float[] color) {
		return color == null ? new float[] {1f, 1f, 1f} : new float[] {
				color.length > 0 ? color[0] : 1.0f,
				color.length > 1 ? color[1] : 1.0f,
				color.length > 2 ? color[2] : 1.0f,
				color.length > 3 ? color[3] : 1.0f
		};
	}
	
	public static File getFolder(String localFile) {
		try {
			if(Minecraft.getInstance() != null)
				return getFolderThroughMC(localFile);
			File folder = new File(new Utils().getClass().getClassLoader().getResource(localFile).toURI());
			return folder;
		}catch(URISyntaxException e) {}
		return null;
	}
	
	static File getFolderThroughMC(String localFile) {
		URL url = MainMod.class.getResource("");
		String s = (url.getPath().replace("KOWI2003/LaserMod/gui/manual", localFile)).replace("union:/", "").replace("KOWI2003/LaserMod", localFile).replace("%20", " ");
//		int indexBegin = s.indexOf("%");
//		int indexEnd = s.indexOf("!/");
		String[] splits = s.split("%\\s*[0-9]+!/");
		s = "";
		for (String string : splits)
			s += string;
		return new File(s);
	}
	
	public static Collection<File> getFilesInFolder(File folder, String[] extentions, boolean useRecursion) {
		return FileUtils.listFiles(folder, extentions, useRecursion);
	}
	
	public static String fixeLocalFileName(File file, File RelativeFolder) {
		return file.getPath().replace(RelativeFolder.getAbsolutePath() + "\\", "").replace("\\", "/");
	}
	
	public static String[] splitToFitWidth(String s, int width) {
		List<String> list = new LinkedList<>();
		try {
			int textWidth = Minecraft.getInstance().font.width(s);
			int splitStep = (s.length()-1) / (int) Math.ceil((double)textWidth / (double)(width));
//			int estLineCount = (int)Math.floor((double)textWidth / (double)width);
//			int splitStep = (s.length() / estLineCount)-1;
			boolean isDone = false;
			int offset = 0;
//			int line = 0;
			s += " ";
			while(!isDone) {
				int end = 0;
				if(offset + splitStep >= s.length()) {
					end = s.length()-1;
					isDone = true;
				}
				end = s.lastIndexOf(" ", offset + (int)(splitStep*1.2));
				if(offset >= s.length() || end >= s.length())
					break;
				String str = (String) s.substring(offset, end);
				list.add(str);
				offset += str.length()+1;
//				line++;
				if(isDone)
					break;
			}
		}catch(Exception ex) {}
		return list.toArray(new String[] {});
	}
	
	public static float conditionalGetFloat(String key, CompoundTag tag, float backup) {
		return tag.contains(key, 5) ? tag.getFloat(key) :  backup;
	}
	
	public static int conditionalGetInt(String key, CompoundTag tag, int backup) {
		return tag.contains(key, 3) ? tag.getInt(key) : backup;
	}
	
	public static double conditionalGetDouble(String key, CompoundTag tag, double backup) {
		return tag.contains(key, 6) ? tag.getDouble(key) :  backup;
	}
	
	public static String conditionalGetString(String key, CompoundTag tag, String backup) {
		return tag.contains(key, 8) ? tag.getString(key) : backup;
	}
	
	public static byte conditionalGetByte(String key, CompoundTag tag, byte backup) {
		return tag.contains(key, 1) ? tag.getByte(key) : backup;
	}
	
	public static short conditionalGetShort(String key, CompoundTag tag, short backup) {
		return tag.contains(key, 2) ? tag.getShort(key) : backup;
	}
	
	public static long conditionalGetLong(String key, CompoundTag tag, long backup) {
		return tag.contains(key, 4) ? tag.getLong(key) : backup;
	}
	
	public static boolean conditionalGetBoolean(String key, CompoundTag tag, boolean backup) {
		return tag.contains(key) ? tag.getBoolean(key) : backup;
	}
	
	public static int[] conditionalGetIntArray(String key, CompoundTag tag, int[] backup) {
		return tag.contains(key, 11) ? tag.getIntArray(key) : backup;
	}
	
	public static long[] conditionalGetLongArray(String key, CompoundTag tag, long[] backup) {
		return tag.contains(key, 12) ? tag.getLongArray(key) : backup;
	}
	
	public static CompoundTag conditionalGetCompound(String key, CompoundTag tag) {
		return tag.contains(key, 10) ? tag.getCompound(key) : new CompoundTag();
	}
	
	public static ListTag conditionalGetListTag(String key, CompoundTag tag, int elementType) {
		return tag.contains(key, 9) ? tag.getList(key, elementType) : new ListTag();
	}
	
	public static float[] conditionalGetColor(String key, CompoundTag tag) {
		return conditionalGetColor(key, tag, new float[] {1, 1, 1, 1});
	}
	
	public static float[] conditionalGetColor(String key, CompoundTag tag, float[] backup) {
		float[] color = new float[4];
		CompoundTag colorTag = conditionalGetCompound(key, tag);
		color[0] = conditionalGetFloat("Red", colorTag, backup[0]);
		color[1] = conditionalGetFloat("Green", colorTag, backup[1]);
		color[2] = conditionalGetFloat("Blue", colorTag, backup[2]);
		color[3] = conditionalGetFloat("Alpha", colorTag, backup[3]);
		return Utils.parseColor(color);
	}
	
	public static void putColor(CompoundTag tag, String name, float[] rgba) {
		rgba = Utils.parseColor(rgba);
		CompoundTag colorTag = new CompoundTag();
		colorTag.putFloat("Red", rgba[0]);
		colorTag.putFloat("Green", rgba[1]);
		colorTag.putFloat("Blue", rgba[2]);
		colorTag.putFloat("Alpha", rgba[3]);
		tag.put(name, colorTag);
	}
	
	public static ItemStack conditionalGetItemStack(String key, CompoundTag tag) {
		CompoundTag itemTag = conditionalGetCompound(key, tag);
		return ItemStack.of(itemTag);
	}
	
	public static void putItemStack(CompoundTag tag, String name, ItemStack stack) {
		if(!stack.isEmpty()) {
			CompoundTag itemTag = new CompoundTag();
			stack.save(itemTag);
			tag.put(name, itemTag);
		}
	}
	
	public static float min(float... values) {
		float lowest = Float.POSITIVE_INFINITY;
		for (float f : values) {
			if(f < lowest)
				lowest = f;
		}
		return lowest;
	}
	
	public static float max(float... values) {
		float highest = Float.NEGATIVE_INFINITY;
		for (float f : values) {
			if(f > highest)
				highest = f;
		}
		return highest;
	}
	
	public static String SpaceOnUpperCase(String sample) {
		if(sample.length() > 1) {
			String result = sample.charAt(0) + "";
			result = result.toUpperCase();

			for(int i = 1; i < sample.length(); i++) {
				if(Character.isUpperCase(sample.charAt(i)))
					result += " ";
				result += sample.charAt(i);
			}
		
			return result;
		}else
			return sample;
	}
	
	public static boolean isShiftDown() {
		return isLShiftDown() || isRShiftDown();
	}
	
	public static boolean isLShiftDown() {
		return isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);
	}
	
	public static boolean isRShiftDown() {
		return isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);
	}
	
	public static boolean isCtrlDown() {
		return isLCtrlDown() || isRCtrlDown();
	}
	
	public static boolean isLCtrlDown() {
		return isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL);
	}
	
	public static boolean isRCtrlDown() {
		return isKeyDown(GLFW.GLFW_KEY_RIGHT_CONTROL);
	}
	
	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_TRUE;
	}
	
	public static class GenericConsumer<T> implements Consumer<T> {
		T stored;
		
		@Override
		public void accept(T t) {
			stored = t;
		}
		
		public T getStored() {
			return stored;
		}
	}
}
