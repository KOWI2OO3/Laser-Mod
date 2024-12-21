package KOWI2003.LaserMod.events;

import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GeneralEvents {

	@SubscribeEvent
	public void onItemGiven(ItemPickupEvent event) {
//		System.out.println("dd");
//		if(event.getStack().getItem() instanceof HiddenItem) {
//			event.setCanceled(true);
//		}
	}
	
}
