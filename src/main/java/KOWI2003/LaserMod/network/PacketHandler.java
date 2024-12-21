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
			.consumerMainThread(PacketLaser::handle)
			.add();
		
		INSTANCE.messageBuilder(PacketLaserMode.class, nextID())
		.encoder(PacketLaserMode::toBytes)
		.decoder(PacketLaserMode::new)
		.consumerMainThread(PacketLaserMode::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketModStation.class, nextID())
		.encoder(PacketModStation::toBytes)
		.decoder(PacketModStation::new)
		.consumerMainThread(PacketModStation::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketComputerToMainThread.class, nextID())
		.encoder(PacketComputerToMainThread::toBytes)
		.decoder(PacketComputerToMainThread::new)
		.consumerMainThread(PacketComputerToMainThread::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketLaserDirection.class, nextID())
		.encoder(PacketLaserDirection::toBytes)
		.decoder(PacketLaserDirection::new)
		.consumerMainThread(PacketLaserDirection::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDeltaMovment.class, nextID())
		.encoder(PacketDeltaMovment::toBytes)
		.decoder(PacketDeltaMovment::new)
		.consumerMainThread(PacketDeltaMovment::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncIColor.class, nextID())
		.encoder(PacketSyncIColor::toBytes)
		.decoder(PacketSyncIColor::new)
		.consumerMainThread(PacketSyncIColor::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncArmor.class, nextID())
		.encoder(PacketSyncArmor::toBytes)
		.decoder(PacketSyncArmor::new)
		.consumerMainThread(PacketSyncArmor::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketTemplateProjector.class, nextID())
		.encoder(PacketTemplateProjector::toBytes)
		.decoder(PacketTemplateProjector::new)
		.consumerMainThread(PacketTemplateProjector::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDataChanged.class, nextID())
		.encoder(PacketDataChanged::toBytes)
		.decoder(PacketDataChanged::new)
		.consumerMainThread(PacketDataChanged::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketDataRemoved.class, nextID())
		.encoder(PacketDataRemoved::toBytes)
		.decoder(PacketDataRemoved::new)
		.consumerMainThread(PacketDataRemoved::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketSyncItemProperty.class, nextID())
		.encoder(PacketSyncItemProperty::toBytes)
		.decoder(PacketSyncItemProperty::new)
		.consumerMainThread(PacketSyncItemProperty::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketOpenItemPropertyMenu.class, nextID())
		.encoder(PacketOpenItemPropertyMenu::toBytes)
		.decoder(PacketOpenItemPropertyMenu::new)
		.consumerMainThread(PacketOpenItemPropertyMenu::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketMultiToolLaserBreakBlock.class, nextID())
		.encoder(PacketMultiToolLaserBreakBlock::toBytes)
		.decoder(PacketMultiToolLaserBreakBlock::new)
		.consumerMainThread(PacketMultiToolLaserBreakBlock::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketFireLaserBullet.class, nextID())
		.encoder(PacketFireLaserBullet::toBytes)
		.decoder(PacketFireLaserBullet::new)
		.consumerMainThread(PacketFireLaserBullet::handle)
		.add();
		
		INSTANCE.messageBuilder(PacketLaserToolTagUpdate.class, nextID())
		.encoder(PacketLaserToolTagUpdate::toBytes)
		.decoder(PacketLaserToolTagUpdate::new)
		.consumerMainThread(PacketLaserToolTagUpdate::handle)
		.add();

		INSTANCE.messageBuilder(PacketSyncTileEntity.class, nextID())
		.encoder(PacketSyncTileEntity::toBytes)
		.decoder(PacketSyncTileEntity::new)
		.consumerMainThread(PacketSyncTileEntity::handle)
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
