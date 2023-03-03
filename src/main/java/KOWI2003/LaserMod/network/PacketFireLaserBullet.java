package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.entities.EntityLaserBullet;
import KOWI2003.LaserMod.init.ModEntities;
import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.LaserItem;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketFireLaserBullet {
	
	ItemStack stack;
	
	public PacketFireLaserBullet(FriendlyByteBuf buf) {
		stack = buf.readItem();
	}
	
	public PacketFireLaserBullet(ItemStack tool) {
		stack = tool;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeItem(stack);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	
	        	float damage = 6;
	        	float knockback = .5f;
	        	float fireDamage = 0f;
	        	
	        	if(stack.getItem() instanceof LaserItem) {
	        		LaserProperties properties = LaserItemUtils.getProperties(stack);
	        		damage = properties.getProperty(Properties.DAMAGE);
	        		if(properties.hasUpgarde((ItemUpgradeBase) ModUpgrades.Push.get()))
	        			knockback += .8f;
	        		if(properties.hasUpgarde((ItemUpgradeBase) ModUpgrades.Pull.get()))
	        			knockback -= .8f;
	        		if(properties.hasUpgarde((ItemUpgradeBase) ModUpgrades.Fire.get()))
	        			fireDamage += 5;
	        	}
	        	
	    		EntityLaserBullet bullet = new EntityLaserBullet(ModEntities.LASER_BULLET.get(), sender, sender.level);
	    		bullet.setColor(LaserItemUtils.getColor(stack));
	    		bullet.setDamageProperties(damage, knockback, fireDamage);
	    		bullet.pickup = Pickup.DISALLOWED;
                bullet.shootFromRotation(sender, sender.getXRot(), sender.getYRot(), 0.0F, 1 * 3.0F, 1.0F);
	    		bullet.setDeltaMovement(sender.getForward().scale(1));
	    		bullet.setNoGravity(true);
	    		
	    		sender.level.addFreshEntity(bullet);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
