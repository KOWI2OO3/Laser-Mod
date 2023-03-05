package KOWI2003.LaserMod.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonUtils {

	@SuppressWarnings("deprecation")
	public static JsonObject getJsonObj(File file) {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(file));
			JsonObject jObj = new JsonParser().parse(reader).getAsJsonObject();
			return jObj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static JsonElement getJsonElement(File file) {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(file));
			JsonObject jObj = new JsonParser().parse(reader).getAsJsonObject();
			return jObj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JsonObject getJsonObject(JsonObject jObj,String key) { 
		if(jObj.has(key)) {
			return jObj.get(key).getAsJsonObject();
		}
		return jObj;
	}
	
	public static String getValue(JsonObject jObj,String key) { 
		if(jObj.has(key)) {
			return jObj.get(key).getAsString();
		}
		return null;
	}
	
	public static JsonObject getJsonObject(JsonObject jObj, String... keys) {
		for (String key : keys) {
			jObj =  getJsonObject(jObj, key);
		}
		return jObj;
	}
	
	@Nullable
	public static JsonElement getJsonElement(JsonObject jObj, String key) {
		if(jObj.has(key)) {
			return jObj.get(key);
		}
		return null;
	}
	
	public static String getJsonString(JsonObject obj) {
		try {
		      StringWriter stringWriter = new StringWriter();
		      JsonWriter jsonWriter = new JsonWriter(stringWriter);
		      jsonWriter.setLenient(true);
		      jsonWriter.setIndent("	");
		      Streams.write(obj, jsonWriter);
		      return stringWriter.toString();
	    } catch (IOException e) {
	      throw new AssertionError(e);
	    }
	}
	
	public static String getJsonString(JsonElement obj) {
		try {
		      StringWriter stringWriter = new StringWriter();
		      JsonWriter jsonWriter = new JsonWriter(stringWriter);
		      jsonWriter.setLenient(true);
		      jsonWriter.setIndent("	");
		      Streams.write(obj, jsonWriter);
		      return stringWriter.toString();
	    } catch (IOException e) {
	      throw new AssertionError(e);
	    }
	}
	
	public static void writeToFile(JsonObject obj, File file) {
		try {
			String jsonString = getJsonString(obj);
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.flush();
			writer.write(jsonString);
			writer.close();
		}catch (Exception e) {}
	}
	
	public static void writeToFile(JsonElement obj, File file) {
		try {
			String jsonString = getJsonString(obj);
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.flush();
			writer.write(jsonString);
			writer.close();
		}catch (Exception e) {}
	}
}
