package KOWI2003.LaserMod.items.upgrades;

import java.util.List;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.config.Config;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.network.PacketDeltaMovment;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.ILaserAccess;
import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class UpgradeMove extends ItemUpgradeBase {

	private boolean isPush = true;
	
	public UpgradeMove(String name, int tier, boolean isPush) {
		super(name, tier);
		this.isPush = isPush;
		setCanBeUsedForLaser(true);
		setCanBeUsedForLaserTools(LaserTools.ALL);
		AbilityNames = isPush ? new String[] {"Knockback"} : new String[] {"Attract"};
		abilityNameColor = isPush ? new float[] {0.35f, 0.5f, 0.35f} : new float[] {0.5f,  1f, 0.5f};
	}
	
	public UpgradeMove(String name, boolean isPush) {
		super(name);
		this.isPush = isPush;
		setCanBeUsedForLaser(true);
		setCanBeUsedForLaserTools(LaserTools.ALL);
		AbilityNames = isPush ? new String[] {"Knockback"} : new String[] {"Attract"};
		abilityNameColor = isPush ? new float[] {0.35f, 0.5f, 0.35f} : new float[] {0.5f,  1f, 0.5f};
	}
	
	@Override
	public void runLaserToolHitEnemy(ItemStack item, LivingEntity enemy, LivingEntity player) {
		float strenght = Config.getInstance().upgradeSettings.pushPullUpgradeKnockback;
		if(!isPush)
			enemy.knockback(strenght, player.getDirection().getStepX(), player.getDirection().getStepZ());
		else
			enemy.knockback(strenght, -player.getDirection().getStepX(), -player.getDirection().getStepZ());
		super.runLaserToolHitEnemy(item, enemy, player);
	}
	
	@Override
	public void runLaserBlock(ILaserAccess te, BlockPos pos) {
		Direction dire = te.getDirection();
		List<Entity> entities = te.getEntitiesInLaser(Entity.class);
		
		for (Entity entity : entities) {
			float speed = Config.getInstance().upgradeSettings.pushPullUpgradeMultiplier;
			
			if(!isPush)
				speed *= -1;
			if(hasTier())
				speed *= getTier();
			
			if(dire == Direction.UP || dire == Direction.DOWN || te.getLaser() instanceof TileEntityAdvancedLaser)
				entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y + 0.05f, entity.getDeltaMovement().z);
			
			Vector3f dir = te.getForward();
			dir.normalize();
			dir.mul(speed);
			Vec3 vec = new Vec3(dir);
			entity.setDeltaMovement(entity.getDeltaMovement().add(vec));
			if(entity instanceof Player)
			{
				Player player = (Player) entity;
				Vec3 playerPos = player.position();
				Vec3 movement = new Vec3(dire.getStepX() * speed, dire.getStepY() * speed, dire.getStepZ() * speed);
				playerPos = playerPos.add(movement);
				
				if(player instanceof ServerPlayer) {
					ServerPlayer sPlayer = (ServerPlayer)player;
					PacketHandler.sendToClient(new PacketDeltaMovment(movement), sPlayer);
				}
			}
		}
		super.runLaserBlock(te, pos);
	}
	
	
}
