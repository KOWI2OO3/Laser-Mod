package KOWI2003.LaserMod.tileentities;

import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.DamageSourceLaser;
import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.utils.MathUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityMirror extends SyncableBlockEntity implements ILaserInteractable, BlockEntityTicker<TileEntityMirror>, ILaserAccess {

	public double distance = 10;
	private double stuckDistance = 10;
	TileEntityLaser laser;
	Direction redirectDirection = Direction.NORTH;
	boolean clockwise = true;
	
	public TileEntityMirror(BlockPos pos, BlockState state) {
		super(ModTileTypes.MIRROR, pos, state);
	}

	@Override
	protected void saveAdditional(@Nonnull CompoundTag nbt) {
		nbt.putLong("Laser", laser == null ? -1 : laser.getBlockPos().asLong());
		nbt.putDouble("Distance", distance);
		nbt.putInt("Direction", redirectDirection.ordinal());
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		if(nbt.contains("Distance"))
			distance = nbt.getDouble("Distance");
		if(nbt.contains("Direction"))
			redirectDirection = Direction.values()[nbt.getInt("Direction")];
		if(nbt.contains("Laser")) {
			long i = nbt.getLong("Laser");
			if(i == -1)
				laser = null;
			else {
				BlockPos pos = BlockPos.of(i);
				if(pos != null && level != null) {
					BlockEntity blockentity = level.getBlockEntity(pos);
					if(blockentity instanceof TileEntityLaser)
						laser = (TileEntityLaser)blockentity;
					else
						laser = null;
				}
			}
		}
		super.load(nbt);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	public void tick(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull TileEntityMirror tile) {
		sync();
		if(isActive()) {
			updateLaserAbilities();
			updateLaser(redirectDirection);
		}
	}
	
	public TileEntityLaser getLaser() {
		return laser;
	}
	
	public Direction getDirection() {
		return redirectDirection;
	}
	
	@Override
	public void interactWithLaser(ILaserAccess te) {
		if(te.getDirection().getOpposite() == getBlockState().getValue(BlockHorizontal.FACING) || 
				te.getDirection().getOpposite() == getBlockState().getValue(BlockHorizontal.FACING).getCounterClockWise()) {
			if(laser != te)
				laser = te.getLaser();
			redirectDirection = te.getDirection().getOpposite() == getBlockState().getValue(BlockHorizontal.FACING) ? te.getDirection().getClockWise() : te.getDirection().getCounterClockWise();
		}
	}

	@Override
	public void onLaserInteractStop(ILaserAccess te) {
		if(te.getDirection().getOpposite() == getBlockState().getValue(BlockHorizontal.FACING) || 
				te.getDirection().getOpposite() == getBlockState().getValue(BlockHorizontal.FACING).getCounterClockWise()) {
			handleTurnOffForInteractable();
		}
		laser = null;
		redirectDirection = Direction.NORTH;
	}
	
	public boolean isActive() {
		return hasLaser() && laser.active;
	}
	
	public boolean hasLaser() {
		return laser != null;
	}
	
/*	=====================================================================
 * 						LASER STUFF
 * 	===================================================================== */
	public List<LivingEntity> getLivingEntitiesInLaser(Direction direction) {
		return getEntitiesInLaser(LivingEntity.class, direction); 
	}
	
	@SuppressWarnings("null")
	public <T extends Entity> List<T> getEntitiesInLaser(Class<T> entityType, Direction direction) {
		AABB aabb = new AABB(getBlockPos());
		Vector3f dir = MathUtils.mulVector(MathUtils.getVectorFromDir(direction),(float) distance-1);
		aabb = aabb.expandTowards(new Vec3(dir.x(), dir.y(), dir.z()));
		return getLevel().getEntitiesOfClass(entityType, aabb);
	}
	
	@SuppressWarnings("null")
	public void updateLaser(Direction direction) {
		for(int i = 1; i <= Math.round(distance); i++) {
			Direction facing = direction;
			BlockPos newPos = Utils.offset(getBlockPos(), facing, i);
			BlockState newState = getLevel().getBlockState(newPos);
			if(!canPassThrough(newPos, newState, facing)) {
				updateLaserInteractables(i, newPos);
				distance = getRenderDistance(newPos, newState, i, facing);
				stuckDistance = i;
				break;
			}
			if(i == stuckDistance && canPassThrough(newPos, newState, facing)) {
				distance = laser.getProperties().getProperty(Properties.MAX_DISTANCE);
			}
		}
	}
	
	@SuppressWarnings("null")
	private void updateLaserInteractables(int distance, BlockPos newPos) {
		if(distance < stuckDistance)
			handleTurnOffForInteractable();
		
		BlockEntity te = getLevel().getBlockEntity(newPos);
		if(te != null)
			if(te instanceof ILaserInteractable)
				((ILaserInteractable)te).interactWithLaser(this);
	}
	
	@SuppressWarnings("null")
	public void handleTurnOffForInteractable() {
		BlockPos pos = Utils.offset(getBlockPos(), redirectDirection, (float) stuckDistance);
		BlockEntity te = getLevel().getBlockEntity(pos);
		if(te != null)
			if(te instanceof ILaserInteractable)
				((ILaserInteractable)te).onLaserInteractStop(this);
	}
	
	@SuppressWarnings("null")
	public boolean canPassThrough(BlockPos pos, BlockState newState, Direction direction) {
		Direction facing = direction;
		
		double bbX = facing.getStepX();
		double bbY = facing.getStepY();
		double bbZ = facing.getStepZ();
		
		BlockPos newPos = Utils.offset(pos, facing, 1);
		VoxelShape AABB = newState.getCollisionShape(getLevel(), newPos);
		HitResult RTR = AABB.clip(new Vec3(pos.getX() + .5 - (.5 * bbX), pos.getY()+.5 - (.5 * bbY), pos.getZ()+ .5 - (.5 * bbZ)), new Vec3(newPos.getX() + .5 + (.5 * bbX), newPos.getY() + .5 + (.5 * bbY), newPos.getZ() + .5 + (.5 * bbZ)), newPos);
		
		if(newState.getBlock() == Blocks.AIR || newState.getMaterial() == Material.GLASS || newState.getBlock() == Blocks.FIRE || newState.getBlock() == Blocks.VOID_AIR)
			return true;
		
		try {
			if(AABB.bounds().intersects(0.5 - (.5 * Math.abs(bbX)), 0.5 - (.5 * Math.abs(bbY)), 0.5 - (.5 * Math.abs(bbZ)), 0.5 + (.5 * Math.abs(bbX)), 0.5 + (.5 * Math.abs(bbY)), 0.5 + (.5 * Math.abs(bbZ)))) {
				if(RTR == null)
					return true;
				return RTR.getLocation() != Vec3.ZERO && RTR.getType() == Type.MISS;
			}
		}catch (Exception e) {}
		
		return true;
	}
	
	@SuppressWarnings("null")
	public double getRenderDistance(BlockPos pos, BlockState newState, double distance, Direction direction) {
		Direction facing = direction;
		
		double value = 0;
		
		double bbX = facing.getStepX();
		double bbY = facing.getStepY();
		double bbZ = facing.getStepZ();
		
		BlockPos newPos = Utils.offset(pos, facing, 1);
		VoxelShape AABB = newState.getCollisionShape(getLevel(), newPos);
		HitResult RTR = AABB.clip(new Vec3(pos.getX() + .5 - (.5 * bbX), pos.getY()+.5 - (.5 * bbY), pos.getZ()+ .5 - (.5 * bbZ)), new Vec3(newPos.getX() + .5 + (.5 * bbX), newPos.getY() + .5 + (.5 * bbY), newPos.getZ() + .5 + (.5 * bbZ)), newPos);
		
		try {
			if(AABB != null)
				if(AABB.bounds().intersects(0.5 - (.5 * Math.abs(bbX)), 0.5 - (.5 * Math.abs(bbY)), 0.5 - (.5 * Math.abs(bbZ)), 0.5 + (.5 * Math.abs(bbX)), 0.5 + (.5 * Math.abs(bbY)), 0.5 + (.5 * Math.abs(bbZ)))) {
					if(RTR == null)
						return distance;
					if(RTR.getLocation() != Vec3.ZERO) {
						Vec3 vector = new Vec3(RTR.getLocation().x * bbX, RTR.getLocation().y * bbY, RTR.getLocation().z * bbZ);
						if(vector.x() != 0)
							value = vector.x();
						if(vector.y() != 0)
							value = vector.y();
						if(vector.z() != 0)
							value = vector.z();
					}
				}
		}catch (Exception e) {}
		return Math.abs(value - (Math.floor(value))) + distance;
	}
	
	@SuppressWarnings("null")
	public void updateLaserAbilities() {
		AABB aabb = new AABB(getBlockPos());
		Vector3f dir = MathUtils.getVectorFromDir(redirectDirection);
		dir = MathUtils.mulVector(dir, (float) distance-1);
		aabb = aabb.expandTowards(new Vec3(dir.x(), dir.y(), dir.z()));
		List<LivingEntity> entities = getLevel().getEntitiesOfClass(LivingEntity.class, aabb);
		for (LivingEntity entity : entities) {
			entity.hurt(new DamageSourceLaser("laser", laser.getProperties().hasUpgarde("fire")), laser.getProperties().getProperty(Properties.DAMAGE));
		}
		
		updateLaserUpgrades();
	}
	
	public void updateLaserUpgrades() {
		List<ItemUpgradeBase> upgrades = laser.getProperties().getUpgrades();
		if(upgrades.size() > 0)
			for(ItemUpgradeBase upgrade : upgrades) {
				if(upgrade != null && getBlockPos() != getStuckPos())
					upgrade.runLaserBlock(this, getStuckPos());
			}
	}
	
	public BlockPos getStuckPos() {
		return Utils.offset(getBlockPos(), redirectDirection, (float) stuckDistance);
	}
	
/*	=====================================================================
 * 						LASER STUFF END
 * 	===================================================================== */

	@Override
	public LaserProperties getProperties() {
		return laser.getProperties();
	}

	@Override
	public BlockPos getPos() {
		return getBlockPos();
	}

	@Override
	public BlockEntity getTileEntity() {
		return this;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesInLaser(Class<T> entityType) {
		return getEntitiesInLaser(entityType, redirectDirection);
	}

	@Override
	public Vector3f getForward() {
		return getDirection().step();
	}
	
}
