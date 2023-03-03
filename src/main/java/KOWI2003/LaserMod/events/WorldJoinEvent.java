package KOWI2003.LaserMod.events;

import com.google.gson.JsonObject;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.config.ModConfig;
import KOWI2003.LaserMod.utils.JsonUtils;
import KOWI2003.LaserMod.utils.MultiVersionUtils;
import KOWI2003.LaserMod.utils.Utils;
import KOWI2003.LaserMod.utils.WebUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldJoinEvent {
	
    private final String PREFIX = ChatFormatting.GOLD + "-> ";
    
	@SubscribeEvent
	public void onEvent(PlayerLoggedInEvent event) {
		try {
			if(ModConfig.GetConfig().updateChecker.useUpdateChecker) {
				Player player = (Player) event.getEntity();
				
				String version = "";
				version = ModConfig.GetConfig().updateChecker.updateCheckerType.equals("latest") ? Utils.getLatestVersion() : Utils.getRecommendedVersion();

				System.out.println(Utils.getLatestVersion() + " -> " + Reference.VESRION);
				
				if(version == null)
					version = Reference.VESRION;
				
				
				String message = "";
				try {
					JsonObject messages = JsonUtils.getJsonObject(WebUtils.getJsonObj(Reference.UPDATE_URL), "message");
					message = messages.get(MultiVersionUtils.getMCVersionGroup()).getAsString();
				}catch(Exception e) {}
				
				if(!message.isEmpty()) {
					boolean useLink = message.contains("#link");
					
					if(message.contains("#check_version"))
						message = message.replace("#check_version", version);
					if(message.contains("#version"))
						message = message.replace("#version", Reference.VESRION);
					if(message.contains("#mc"))
						message = message.replace("#mc", MultiVersionUtils.getMCVersion());
					if(message.contains("#player"))
						message = message.replace("#player", player.getName().getString());
					if(useLink)
						message = message.replace("#link", "");
					
					
					
					Component mod = MutableComponent.create(new LiteralContents(ChatFormatting.DARK_AQUA  + "---Laser Mod---"));
					Component s = MutableComponent.create(new LiteralContents(PREFIX + message));
					player.sendSystemMessage(mod);
					player.sendSystemMessage(s);
					if(useLink) {
						MutableComponent link = MutableComponent.create(new LiteralContents(PREFIX + ChatFormatting.BLUE + ChatFormatting.UNDERLINE + "Click Here"));
						Style style = link.getStyle();
						style = link.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, JsonUtils.getValue(WebUtils.getJsonObj(Reference.UPDATE_URL), "homepage")));
						style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, MutableComponent.create(new LiteralContents("go to the mod page"))));
						link.setStyle(style);
						player.sendSystemMessage(link);
					}
				}else {
					if(!Reference.VESRION.equals(version)) {
						MutableComponent mod = MutableComponent.create(new LiteralContents(ChatFormatting.DARK_AQUA  + "---Laser Mod---"));
						MutableComponent s = MutableComponent.create(new LiteralContents(PREFIX + "version " + version + " is out, your client is out-dated, to update "));
						MutableComponent link = MutableComponent.create(new LiteralContents(PREFIX + ChatFormatting.BLUE + ChatFormatting.UNDERLINE + "Click Here"));
						Style style = link.getStyle();
						style = link.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, JsonUtils.getValue(WebUtils.getJsonObj(Reference.UPDATE_URL), "homepage")));
						style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, MutableComponent.create(new LiteralContents("go to the mod page"))));
						link.setStyle(style);
						player.sendSystemMessage(mod);
						player.sendSystemMessage(s);
						player.sendSystemMessage(link);
					}
				}
			}
		}catch(Exception ex) {}
	}
}
