package KOWI2003.LaserMod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

	public static SimpleChannel INSTANCE;

	private static int ID = 0;

	private static final String PROTOCOL_VERSION = "1";
	
	private static int nextID() {
		return ID++;
	}

	/**
	 * Register all of our network messages on their appropriate side
	 * 
	 * @param channelName
	 *            The name of the network channel
	 */
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(channelName), 
				() -> PROTOCOL_VERSION,
			    PROTOCOL_VERSION::equals,
			    PROTOCOL_VERSION::equals);

		INSTANCE.messageBuilder(PacketLaser.class, nextID())
			.encoder(PacketLaser::toBytes)
			.decoder(PacketLaser::new)
			.consumer(PacketLaser::handle)
			.add();
		
		INSTANCE.messageBuilder(PacketLaserMode.class, nextID())
		.encoder(PacketLaserMode::toBytes)
		.decoder(PacketLaserMode::new)
		.consumer(PacketLaserMode::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketModStation.class, nextID())
		.encoder(PacketModStation::toBytes)
		.decoder(PacketModStation::new)
		.consumer(PacketModStation::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketComputerToMainThread.class, nextID())
		.encoder(PacketComputerToMainThread::toBytes)
		.decoder(PacketComputerToMainThread::new)
		.consumer(PacketComputerToMainThread::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketLaserDirection.class, nextID())
		.encoder(PacketLaserDirection::toBytes)
		.decoder(PacketLaserDirection::new)
		.consumer(PacketLaserDirection::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDeltaMovment.class, nextID())
		.encoder(PacketDeltaMovment::toBytes)
		.decoder(PacketDeltaMovment::new)
		.consumer(PacketDeltaMovment::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncIColor.class, nextID())
		.encoder(PacketSyncIColor::toBytes)
		.decoder(PacketSyncIColor::new)
		.consumer(PacketSyncIColor::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncConfig.class, nextID())
		.encoder(PacketSyncConfig::toBytes)
		.decoder(PacketSyncConfig::new)
		.consumer(PacketSyncConfig::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncArmor.class, nextID())
		.encoder(PacketSyncArmor::toBytes)
		.decoder(PacketSyncArmor::new)
		.consumer(PacketSyncArmor::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketTemplateProjector.class, nextID())
		.encoder(PacketTemplateProjector::toBytes)
		.decoder(PacketTemplateProjector::new)
		.consumer(PacketTemplateProjector::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDataChanged.class, nextID())
		.encoder(PacketDataChanged::toBytes)
		.decoder(PacketDataChanged::new)
		.consumer(PacketDataChanged::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDataRemoved.class, nextID())
		.encoder(PacketDataRemoved::toBytes)
		.decoder(PacketDataRemoved::new)
		.consumer(PacketDataRemoved::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncItemProperty.class, nextID())
		.encoder(PacketSyncItemProperty::toBytes)
		.decoder(PacketSyncItemProperty::new)
		.consumer(PacketSyncItemProperty::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketOpenItemPropertyMenu.class, nextID())
		.encoder(PacketOpenItemPropertyMenu::toBytes)
		.decoder(PacketOpenItemPropertyMenu::new)
		.consumer(PacketOpenItemPropertyMenu::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketMultiToolLaserBreakBlock.class, nextID())
		.encoder(PacketMultiToolLaserBreakBlock::toBytes)
		.decoder(PacketMultiToolLaserBreakBlock::new)
		.consumer(PacketMultiToolLaserBreakBlock::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketFireLaserBullet.class, nextID())
		.encoder(PacketFireLaserBullet::toBytes)
		.decoder(PacketFireLaserBullet::new)
		.consumer(PacketFireLaserBullet::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketLaserToolTagUpdate.class, nextID())
		.encoder(PacketLaserToolTagUpdate::toBytes)
		.decoder(PacketLaserToolTagUpdate::new)
		.consumer(PacketLaserToolTagUpdate::handle)
		.add();
	}
	
	public static void sendToClient(Object packet, ServerPlayer player) {
    	if(INSTANCE != null && packet != null)
    		INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
    	if(INSTANCE != null && packet != null)
    		INSTANCE.sendToServer(packet);
    }
	
}
