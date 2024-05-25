package KOWI2003.LaserMod.tileentities;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.DamageSourceLaser;
import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.LaserProperties.Properties;
import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.config.ModConfig;
import KOWI2003.LaserMod.container.ContainerLaser;
import KOWI2003.LaserMod.init.ModSounds;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketLaser;
import KOWI2003.LaserMod.utils.MathUtils;
import KOWI2003.LaserMod.utils.TileEntityUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityLaser extends SyncableBlockEntity implements BlockEntityTicker<TileEntityLaser>, MenuProvider, IColorable, ILaserAccess {

	public boolean active = false;
	public float red = 1.0f;
	public float green, blue = 0.0f;
	public double distance = ModConfig.GetConfig().defaultLaserDistance;
	public int soundCooldown = 0;
	public MODE mode = MODE.NORMAL;
	
	LaserProperties properties;
	
	protected double stuckDistance = 10;
	public boolean isRemoteControlled = false;

	
	public TileEntityLaser(BlockEntityType<?> advancedLaser, BlockPos pos, BlockState state) {
		super(advancedLaser, pos, state);
		red = 1.0f;
		green = 0.0f;
		blue = 0.0f;
		properties = new LaserProperties();
		properties.setProperty(Properties.DAMAGE,ModConfig.GetConfig().defaultLaserDamage);
		properties.setProperty(Properties.MAX_DISTANCE, ModConfig.GetConfig().defaultLaserDistance);
	}
	public TileEntityLaser(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER, pos, state);
		red = 1.0f;
		green = 0.0f;
		blue = 0.0f;
		properties = new LaserProperties();
		properties.setProperty(Properties.DAMAGE,ModConfig.GetConfig().defaultLaserDamage);
		properties.setProperty(Properties.MAX_DISTANCE, ModConfig.GetConfig().defaultLaserDistance);
	}
	
	@Override
	public void tick(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull TileEntityLaser te) {
		if(!isRemoteControlled)
			if(active != Utils.isBlockPowered(pos, level)) {
				setActive(Utils.isBlockPowered(pos, level));
			}
			
		if(active) {
			if(ModConfig.useSounds) {
				soundCooldown--;
				if(soundCooldown <= 0) {
					soundCooldown = 29;
					if(!properties.hasUpgarde((ItemUpgradeBase)ModUpgrades.Silence.get()))
						level.playSound(null, getPos(), ModSounds.LASER_ACTIVE.get(), SoundSource.BLOCKS, 0.1F, 1F);
				}
			}
		}
		
		sync();
		if(active) {
			updateLaserAbilities();
			updateLaser();
		}
	}
	
	@SuppressWarnings("null")
	public void setActive(boolean value) {
		active = value;
		if(!active) {
			handleTurnOffForInteractable();
			if(ModConfig.useSounds && !properties.hasUpgarde((ItemUpgradeBase)ModUpgrades.Silence.get()))
				level.playSound(null, getBlockPos(), ModSounds.LASER_DEACTIVATE.get(), SoundSource.BLOCKS, 1.0f, 0.68f);
		}else
			if(ModConfig.useSounds && !properties.hasUpgarde((ItemUpgradeBase)ModUpgrades.Silence.get()))
				level.playSound(null, getBlockPos(), ModSounds.LASER_ACTIVATE.get(), SoundSource.BLOCKS, 1.0f, 0.68f);
		soundCooldown = 29;
		sync();
	}
	
//	@Override
	public double getViewDistance()
	{
		return 16384*2d;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	protected void saveAdditional(@Nonnull CompoundTag nbt) {
		nbt.putBoolean("Powered", active);
		nbt.putFloat("Red", red);
		nbt.putFloat("Green", green);
		nbt.putFloat("Blue", blue);
		nbt.putDouble("Distance", distance);
		nbt.putString("Mode", mode.name());
		nbt.putBoolean("isRemoteControlled", isRemoteControlled);
		nbt = properties.save(nbt);
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		active = nbt.getBoolean("Powered");
		red = nbt.getFloat("Red");
		green = nbt.getFloat("Green");
		blue = nbt.getFloat("Blue");
		distance = nbt.getDouble("Distance");
		mode = MODE.getMode(nbt.getString("Mode"));
		if(nbt.contains("isRemoteControlled"))
			isRemoteControlled = nbt.getBoolean("isRemoteControlled");
		properties.load(nbt);
		super.load(nbt);
	}
	
	@SuppressWarnings("null")
	public void setColor(float red, float green, float blue) {
		setColor(0, new float[] {red, green, blue});
		
		TileEntityUtils.syncColorToClient(this);
		
		if(!level.isClientSide) {
			List<? extends Player> entities = level.players();
			for (Player player : entities) {
				if(player instanceof ServerPlayer && player.level == level)
					PacketHandler.sendToClient(new PacketLaser(getPos(), red, green, blue), (ServerPlayer) player);
			}
		}
	}
	
	@Override
	public void setColor(int index, float[] color) {
		float red = color.length > 0 ? color[0] : 1.0f;
		float green = color.length > 1 ? color[1] : 1.0f;
		float blue = color.length > 2 ? color[2] : 1.0f;
		if(properties.hasUpgarde("color")) {
			this.red = red > 1.0f ? 1.0f : red < 0.0f ? 0.0f : red;
			this.green = green > 1.0f ? 1.0f : green < 0.0f ? 0.0f : green;
			this.blue = blue > 1.0f ? 1.0f : blue < 0.0f ? 0.0f : blue;
		}else {
			this.red = 1.0f;
			this.green = this.blue = 0f;
		}
	}
	
	public List<LivingEntity> getLivingEntitiesInLaser() {
		return getEntitiesInLaser(LivingEntity.class); 
	}
	
	@SuppressWarnings("null")
	public <T extends Entity> List<T> getEntitiesInLaser(Class<T> entityType) {
		AABB aabb = new AABB(getBlockPos());
		Vector3f dir = getDir();
		dir = MathUtils.mulVector(dir, (float) distance-1);
		aabb = aabb.expandTowards(new Vec3(dir.x(), dir.y(), dir.z()));
		return getLevel().getEntitiesOfClass(entityType, aabb);
	}
	
	public void updateLaserAbilities() {
		List<LivingEntity> entities = getEntitiesInLaser(LivingEntity.class);
		float damage = properties.getProperty(Properties.DAMAGE);
		if(damage > 0)
			for (LivingEntity entity : entities)
				entity.hurt(new DamageSourceLaser("laser", properties.hasUpgarde("fire")), damage);
		updateLaserUpgrades();
	}
	
	public Vector3f getDir() {
		return new Vector3f(getBlockState().getValue(BlockLaser.FACING).getStepX(), getBlockState().getValue(BlockLaser.FACING).getStepY(), getBlockState().getValue(BlockLaser.FACING).getStepZ());
	}
	
	public void updateLaserUpgrades() {
		List<ItemUpgradeBase> upgrades = properties.getUpgrades();
		if(upgrades.size() > 0)
			for(ItemUpgradeBase upgrade : upgrades) {
				if(upgrade != null && getBlockPos() != getStuckPos())
					upgrade.runLaserBlock(this, getStuckPos());
			}
	}
	
	public BlockPos getStuckPos() {
		return Utils.offset(getBlockPos(), getBlockState().getValue(BlockLaser.FACING), (float) stuckDistance);
	}
	
	@SuppressWarnings("null")
	public void updateLaser() {
		if(stuckDistance > properties.getProperty(Properties.MAX_DISTANCE))
			stuckDistance = properties.getProperty(Properties.MAX_DISTANCE);
		
		for(int i = 1; i <= Math.round(distance); i++) {
			BlockState state = getBlockState();
			Direction facing = state.getValue(BlockLaser.FACING);
			BlockPos newPos = Utils.offset(getBlockPos(), facing, i);
			BlockState newState = getLevel().getBlockState(newPos);
			if(!canPassThrough(newPos, newState)) {
				updateLaserInteractables(i, newPos);
				distance = getRenderDistance(newPos, newState, i);
				stuckDistance = i;
				break;
			}
			if(i == stuckDistance && canPassThrough(newPos, newState)) {
				distance = properties.getProperty(Properties.MAX_DISTANCE);
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
		BlockPos pos = Utils.offset(getBlockPos(), getBlockState().getValue(BlockLaser.FACING), (float) stuckDistance);
		BlockEntity te = getLevel().getBlockEntity(pos);
		if(te != null)
			if(te instanceof ILaserInteractable)
				((ILaserInteractable)te).onLaserInteractStop(this);
	}
	
	@SuppressWarnings("null")
	public boolean canPassThrough(BlockPos pos, BlockState newState) {
		Direction facing = getBlockState().getValue(BlockLaser.FACING);
		
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
	public double getRenderDistance(BlockPos pos, BlockState newState, double distance) {
		Direction facing = getBlockState().getValue(BlockLaser.FACING);
		
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
	
	public LaserProperties getProperties() {
		return properties;
	}
	
	//Inventory Stuff
	public ItemStackHandler createHandler() {
		ItemStackHandler inv = new ItemStackHandler(9) {
			@Override
			public void setStackInSlot(int slot, ItemStack stack) {
				if(stack.getItem() instanceof ItemUpgradeBase)
					acceptsItem((ItemUpgradeBase)stack.getItem(), false);
				super.setStackInSlot(slot, stack);
			}
		};
		List<ItemUpgradeBase> items = properties.getUpgrades();
		for(int i = 0; i < items.size(); i++) {
			inv.setStackInSlot(i, new ItemStack(items.get(i)));
		}
		return inv;
	}
	
	public boolean acceptsItem(ItemUpgradeBase upgrade, boolean simulate) {
		return upgrade.isUsefullForLaser() && (simulate ? properties.doesAllow(upgrade) : properties.addUpgrade(upgrade));
	}
	
	public boolean remove(ItemUpgradeBase upgarde, boolean simulate) {
		return simulate ? properties.hasUpgarde(upgarde) : properties.removeUpgrade(upgarde) != null;
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInv, @Nullable Player player) {
		return new ContainerLaser(windowId, playerInv, this);
	}
	
	@Override
	public Component getDisplayName() {
		return MutableComponent.create(new TranslatableContents("container.lasermod.laser"));
	}
	
public static enum MODE {
	NORMAL("Normal", 0),
	POWER("Power", 1),
	INVISIBLE("Invisible", 2),
	BEAM("Beam", 3),
	NEW_POWER("Focused", 4);
	
	private int id;
	private String name;
	
	private MODE(String name, int id) {
		this.id = id;
		this.name = name;
	}
	
	public static MODE getMode(String name) {
		return MODE.valueOf(name);
	}
	
	public static MODE getMode(int id) {
		return MODE.values()[id];
	}
	
	public String getName() {
		return name;
	}
	
	public String getFormalName() {
		return Utils.getFormalText(name);
	}
	
	public MODE fromFormalName(String name) {
		return getMode(Utils.fromFormalText(name));
	}
	
	public int getID() {
		return id;
	}
}

	@Override
	public float[] getColor(int index) {
		return new float[] {red, green, blue};
	}

	public Direction getDirection() {
		return getBlockState().getValue(BlockRotatable.FACING);
	}
	
	public Vector3f getForward() {
		return getDirection().step();
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
	public TileEntityLaser getLaser() {
		return this;
	}
	@Override
	public boolean isRemoved() {
		return false;
	}
}
