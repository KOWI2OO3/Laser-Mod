package KOWI2003.LaserMod.tileentities;

import java.util.List;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.LaserProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ILaserAccess {

	Direction getDirection();
	LaserProperties getProperties();
	BlockPos getPos();
	BlockEntity getTileEntity();
	TileEntityLaser getLaser();
	BlockPos getStuckPos();
	Vector3f getForward();
	<T extends Entity> List<T> getEntitiesInLaser(Class<T> entityType);
}
