package KOWI2003.LaserMod.tileentities;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.network.PacketComputerToMainThread;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import KOWI2003.LaserMod.utils.compat.cctweaked.LuaBlockPos;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.IDynamicLuaObject;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityDeviceHub extends SyncableBlockEntity implements BlockEntityTicker<TileEntityDeviceHub> {
	//TODO: add a physical clear list button, a display of the amount of devices connected
	//TODO: add gui with clear button and display a list of connected devices where you could remove any specific device at any moment (also a device count)
	
	List<Device> devices = new LinkedList<Device>();
	
	IPeripheral peripheral;
	private LazyOptional<IPeripheral> peripheralCap;
	
	public TileEntityDeviceHub(BlockPos pos, BlockState state) {
		super(ModTileTypes.DEVICE_HUB, pos, state);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, TileEntityDeviceHub tile) {
		validateDevices();
	}
	
	public void validateDevices() {
		List<Device> destructionQueue = new LinkedList<>();
		for (Device device : devices) {
			device.validateDevice(getLevel());
			if(device.queueDestroy)
				destructionQueue.add(device);
		}
		for(Device device : destructionQueue)
			devices.remove(device);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		List<Integer> coords = new LinkedList<>();
		for (Device d: devices) {
			coords.add(d.getPos().getX());
			coords.add(d.getPos().getY());
			coords.add(d.getPos().getZ());
		}
		nbt.putIntArray("Data", coords);
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		List<BlockPos> positions = new LinkedList<>();
		int[] coords = nbt.getIntArray("Data");
		for(int i = 0; i < coords.length; i += 3)
			positions.add(new BlockPos(coords[i], coords[i+1], coords[i+2]));
		devices.clear();
		for (BlockPos pos : positions)
			devices.add(new Device(pos, getLevel()));
		super.load(nbt);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		sync();
		if(cap == Capabilities.CAPABILITY_PERIPHERAL) {
			if(peripheral == null) peripheral = new PeripheralInterface(this);
			if(peripheralCap == null) peripheralCap = LazyOptional.of(() -> peripheral);
			return peripheralCap.cast();
		}
		return super.getCapability(cap, side);
	}
	
	public void link(BlockPos pos) {
		Device device = new Device(pos, getLevel());
		BlockEntity te = device.getTileEntity();
		if(te instanceof TileEntityLaser) {
			((TileEntityLaser)te).isRemoteControlled = true;
			((TileEntityLaser)te).sync();
		}if(te instanceof TileEntityLaserProjector) {
			((TileEntityLaserProjector)te).isRemoteControlled = true;
			((TileEntityLaserProjector)te).sync();
		}
		devices.add(device);
		sync();
	}
	
	public void callMethod(int methodIndex, Object[] args) {
		switch( methodIndex )
        {
			//connectDevice
			case 3:
			{
				if(args.length == 3)
					link(new BlockPos((int)(double)args[0], (int)(double)args[1], (int)(double)args[2]));
				break;
			}
			
			//disconnectDevice
			case 4:
			{
				if(args.length == 1) {
					int index = (int)(double)args[0];
					if(index < devices.size()) {
						devices.remove(index);
						sync();
					}
				}
				break;
			}
		
			//setActive
			case 5:
			{
				if(args.length == 1) {
					boolean value = (boolean)args[0];
					for (Device d : devices) {
						BlockEntity te = d.getTileEntity();
						if(te instanceof TileEntityLaser)
							((TileEntityLaser) te).setActive(value);
						else if(te instanceof TileEntityLaserProjector)
							((TileEntityLaserProjector) te).setActive(value);
					}
				}else if(args.length == 2) {
					boolean value = (boolean)args[0];
					int index = (int)args[1];
					if(index >= devices.size())
						break;
					BlockEntity te = devices.get(index).getTileEntity();
					if(te instanceof TileEntityLaser)
						((TileEntityLaser) te).setActive(value);
					else if(te instanceof TileEntityLaserProjector)
						((TileEntityLaserProjector) te).setActive(value);
				}
				break;
			}
			
			//setColor
			case 6:
			{
				if(args.length >= 3 && args.length <= 4) {
					float r = (float)((double)args[0]);
					r = r > 1.0f ? 1.0f : r < 0 ? 0 : r;
					float g = (float)((double)args[1]);
					g = g > 1.0f ? 1.0f : g < 0 ? 0 : g;
					float b = (float)((double)args[2]);
					b = b > 1.0f ? 1.0f : b < 0 ? 0 : b;
					
					if(args.length == 4) {
						int index = (int)args[1];
						if(index >= devices.size())
							break;
						BlockEntity te = devices.get(index).getTileEntity();
						if(te instanceof TileEntityLaser)
							((TileEntityLaser) te).setColor(r, g, b);
					}else
						for(Device d : devices) {
							BlockEntity te = d.getTileEntity();
							if(te instanceof TileEntityLaser)
								((TileEntityLaser) te).setColor(r, g, b);
						}
				}
				break;
			}
			
			//setMode
			case 7:
			{
				if(args.length >= 1 && args.length <= 2) {
					int mode = (int)(double)args[0];
					for(Device d : devices) {
						BlockEntity te = d.getTileEntity();
						if(te instanceof TileEntityLaser) {
							if(mode < MODE.values().length) {
								MODE m = MODE.values()[mode];
								((TileEntityLaser) te).mode = m;
								((TileEntityLaser) te).sync();
							}
						}
					}
				}
				break;
			}
        	
        	//setDirection
        	case 9:
        	{
        		if(args.length >= 3 && args.length <= 4) {
	        		float x = (float)(double)args[0];
	        		float y = (float)(double)args[1];
	        		float z = (float)(double)args[2];
	        		Vector3f vec = new Vector3f(x, y, z);
	        		vec.normalize();
	        		
	        		if(args.length == 4) {
	        			Device d = devices.get((int)(double)args[3]);
	        			BlockEntity te = d.getTileEntity();
	        			if(te instanceof TileEntityAdvancedLaser)
	        				((TileEntityAdvancedLaser) te).setDirectionDirect(vec);
	        		}else 
	        			for(Device d : devices)
	        			{
	        				BlockEntity te = d.getTileEntity();
		        			if(te instanceof TileEntityAdvancedLaser)
		        				((TileEntityAdvancedLaser) te).setDirectionDirect(vec);
	        			}
        		}
				break;
        	}
        	
			default:
				break;
        }
	}
	
public class PeripheralInterface implements IDynamicPeripheral {

	TileEntityDeviceHub te;
	
	public PeripheralInterface(TileEntityDeviceHub te) {
		this.te = te;
	}

	@Override
	public String getType() {
		return "Device_Hub";
	}

	@Override
	public MethodResult callMethod(IComputerAccess computer, ILuaContext content, int methodIndex, IArguments args)
			throws LuaException {
		try {
		switch( methodIndex )
        {
			//getDevicesCount
			case 0:
			{
				if(args.getAll().length == 0) {
					return MethodResult.of(te.devices.size());
				}else
    				throw new LuaException("Expected 0 arguments");
			}
			
			//getMethodeUse
			case 1:
			{
				throw new LuaException("Not Yet Implemented!");
//				if(args.getAll().length == 0) {
//					return MethodResult.of(
//							"getAmount()", 
//							"getMethodUse() or getMethodUse( [methodName] )",
//							"getDevice( [deviceIndex] )",
//							"setActive( [boolean], {deviceIndex} )"
//							);
//				}else
//    				throw new LuaException("Expected 0 arguments");
			}
			
			//getDevice
			case 2:
			{
				if(args.getAll().length == 1) {
					if(!(args.getAll()[0] instanceof Number))
	    				throw new LuaException("Expected a number argument [argument 0]");
					if((int)(double)args.getAll()[0] >= te.devices.size())
						throw new LuaException("The index " + (int)(double)args.getAll()[0] + " is out of range for the devices! [argument 0]");
					return MethodResult.of(new DeviceObject(te, (int)(double)args.getAll()[0]));
				}else
    				throw new LuaException("Expected 1 number arguments");
			}
			
			//connectDevice
			case 3:
			{
				if(args.getAll().length == 3) {
					for(int i = 0; i < 3; i++) {
						if(!(args.get(i) instanceof Number))
							throw new LuaException("Expected a number! [argument " + i +"]");
					}
					BlockPos pos = new BlockPos(args.getInt(0), args.getInt(1), args.getInt(2));
					Device d = new Device(pos, te.level);
					if(d.getType() == DeviceType.NONE)
						throw new LuaException("Block at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ") is not a valid Device!");
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
				}else
					throw new LuaException("Expected 3 arguments!");
				return MethodResult.of();
			}
			
			//disconnectDevice
			case 4:
			{
				if(args.getAll().length == 1) {
					if(!(args.get(0) instanceof Number))
						throw new LuaException("Expected a number! [argument 0]");
					if((int)(double)args.getAll()[0] >= te.devices.size())
						throw new LuaException("The index " + (int)(double)args.getAll()[0] + " is out of range for the devices! [argument 0]");
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
				}else
					throw new LuaException("Expected 1 arguments!");
				return MethodResult.of();
			}
			
			//setActive
			case 5:
			{
				if(args.getAll().length == 1) {
					if(!(args.getAll()[0] instanceof Boolean))
						throw new LuaException("Expected a boolean [argument 0]");
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
				}else if(args.getAll().length == 2) {
					if(!(args.getAll()[0] instanceof Boolean))
						throw new LuaException("Expected a boolean [argument 0]");
					if(!(args.getAll()[1] instanceof Number))
						throw new LuaException("Expected a number [argument 1]");
					if((int)(double)args.getAll()[1] >= te.devices.size())
						throw new LuaException("The index " + (int)(double)args.getAll()[1] + " is out of range for the devices! [argument 1]");
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
				}else
					throw new LuaException("Expected 1 or 2 arguments!");
				return MethodResult.of();
			}
			
			//setColor
			case 6:
			{
				if(args.getAll().length >= 3 && args.getAll().length <= 4) {
					for(int i = 0; i < 3; i++) {
						if(!(args.get(i) instanceof Number))
							throw new LuaException("Expected a number! [argument " + i +"]");
						if(!(args.getDouble(i) >= 0 && args.getDouble(i) <= 1))
							throw new LuaException("Number must be in range (0.0 -> 1.0)! [argument " + i +"]");
					}
					if(args.getAll().length == 4) {
						if(!(args.get(3) instanceof Number))
							throw new LuaException("Expected a number! [argument 3]");
						if((int)(double)args.getAll()[3] >= te.devices.size())
							throw new LuaException("The index " + (int)(double)args.getAll()[3] + " is out of range for the devices! [argument 3]");
					}
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
				}else
					throw new LuaException("Expected 3 or 4 arguments!");
				return MethodResult.of();
			}
			
			//setMode
			case 7:
			{
				if(args.getAll().length >= 1 && args.getAll().length <= 2) {
					if(!(args.getAll()[0] instanceof Number))
						throw new LuaException("Expected a number! [argument 0]");
					int modeIndex = (int)args.getAll()[0];
					int max = MODE.values().length;
					if(args.getAll().length == 2) {
						if(!(args.get(1) instanceof Number))
							throw new LuaException("Expected a number! [argument 1]");
						if((int)(double)args.getAll()[1] >= te.devices.size())
							throw new LuaException("The index " + (int)(double)args.getAll()[1] + " is out of range for the devices! [argument 1]");
						
						Device d = te.devices.get((int)(double)args.getAll()[1]);
						if(d.getType() == DeviceType.LASER || d.getType() == DeviceType.ADVANCED_LASER)
							max = MODE.values().length;
					}
					if(modeIndex >= max)
						throw new LuaException("Mode index is out of range (0 -> " + (max - 1) + ")! [argument 0]");
					PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
    				return MethodResult.of();
				}else
					throw new LuaException("Expected 1 or 2 arguments!");
			}
			
			//canBeColored
			case 8:
			{
				if(args.getAll().length == 1) {
					if(!(args.get(0) instanceof Number))
						throw new LuaException("Expected a number! [argument 0]");
					if((int)(double)args.getAll()[0] >= te.devices.size())
						throw new LuaException("The index " + (int)(double)args.getAll()[0] + " is out of range for the devices! [argument 0]");
					BlockEntity tileentity = te.devices.get((int)(double)args.getAll()[0]).getTileEntity();
					return MethodResult.of(tileentity instanceof TileEntityLaser && ((TileEntityLaser) tileentity).getProperties().hasUpgarde("color"));
				}else
    				throw new LuaException("Expected 1 argument!");
			}
        	
        	//setDirection
        	case 9:
        	{
        		if(args.getAll().length >= 3 && args.getAll().length <= 4) {
        			if(args.getAll().length == 4) {
        				Device d = getDevice(args, 1);
        				if(d != null)
        					if(d.getType() != DeviceType.ADVANCED_LASER)
        						throw new LuaException("Cannot set the direction of this device (device incapatible, should be a advanced laser)!");
	        		}
        			
        			if(!(args.getAll()[0] instanceof Number))
        				throw new LuaException("Expected a number argument! [argument 0]");
        			if(!(args.getAll()[1] instanceof Number))
        				throw new LuaException("Expected a number argument! [argument 1]");
        			if(!(args.getAll()[2] instanceof Number))
        				throw new LuaException("Expected a number argument! [argument 2]");
    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
        		}else
        			throw new LuaException("Expected 3 or 4 arguments!");
    			return MethodResult.of();
        	}
        	
        	default:
        	{
        		throw new LuaException("Method index out of range!");
        	}
        }
		}catch(NullPointerException ex) {
			return MethodResult.of();
		}
	}

	@Override
	public String[] getMethodNames() {
		return new String[] {
				"getDevicesCount",
				"getMethodUse",	//getMethodeUse() or overload: getMethodeUse([Methode Name])
				"getDevice",
				"connectDevice",	//connectDevice(x, y, z)
				"disconnectDevice",	//disconnectDevice([index]) or disconnectDevice(x, y, z)
				"setActive",	//setActive([true/false]) or overload: setActive([true/false], [device_index])
				"setColor",
				"setMode",
				"canBeColored",
				"setDirection"
		};
	}
	
	public int getIndex(IArguments args, int argumentIndex) throws LuaException {
		if(args.getAll().length == argumentIndex+1) {
			if(!(args.get(argumentIndex) instanceof Number))
				throw new LuaException("Expected a number! [argument " + argumentIndex + "]");
			if((int)(double)args.getAll()[argumentIndex] >= te.devices.size())
				throw new LuaException("The index " + (int)(double)args.getAll()[argumentIndex] + " is out of range for the devices! [argument " + argumentIndex + "]");
			return (int)(double)args.getAll()[argumentIndex];
		}
		return -1;
	}
	
	@Nullable
	public Device getDevice(IArguments args, int argumentIndex) throws LuaException {
		int index = getIndex(args, argumentIndex);
		if(index < te.devices.size() && index >= 0) {
			return te.devices.get(index);
		}
		return null;
	}
	
	@Override
	public boolean equals(IPeripheral arg0) {
		return false;
	}
}

public class DeviceObject implements IDynamicLuaObject {

	TileEntityDeviceHub te;
	int index;
	
	public DeviceObject(TileEntityDeviceHub te, int index) {
		this.te = te;
		this.index = index;
	}
	
	@Override
	public MethodResult callMethod(ILuaContext context, int methodIndex, IArguments args) throws LuaException {
		try {
			switch( methodIndex )
	        {
				case 0:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(te.devices.get(index).getType().name().toLowerCase());
					}else
	    				throw new LuaException("Expected 0 arguments");
				}
				case 1:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(te.devices.get(index).getTypeID());
					}else
	    				throw new LuaException("Expected 0 arguments");
				}
				case 2:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(new LuaBlockPos(te.devices.get(index).getPos()));
					}else
	    				throw new LuaException("Expected 0 arguments");
				}
				
	        	default:
	        	{
	        		throw new LuaException("Method index out of range!");
	        	}
	        }
			}catch(NullPointerException ex) {
				return MethodResult.of();
			}
	}

	@Override
	public String[] getMethodNames() {
		return new String[] {
				"getType",
				"getTypeID",
				"getPosition"
		};
	}
}

public static class Device {
	
	BlockPos pos;
	Level level;
	
	boolean queueDestroy = false;
	
	public Device(BlockPos pos, Level level) {
		this.pos = pos;
		this.level = level;
	}
	
	public BlockEntity getTileEntity() {
		return level.getBlockEntity(pos);
	}
	
	public BlockState getState() {
		return level != null ? level.getBlockState(pos) : Blocks.AIR.defaultBlockState();
	}
	
	public Block getBlock() {
		return getState().getBlock();
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public DeviceType getType() {
		Block block = getBlock();
		for (DeviceType type: DeviceType.values()) {
			if(type.getBlock() == block)
				return type;
		}
		return DeviceType.NONE;
	}
	
	public int getTypeID() {
		return getType().ordinal()-1;
	}
	
	public void validateDevice(Level level) {
		if(this.level == null)
			this.level = level;
		if(getTypeID() == -1 && this.level != null)
			queueDestroy = true;
	}
}

public enum DeviceType
{
	NONE(Blocks.AIR), LASER(ModBlocks.Laser), LASER_PROJECTOR(ModBlocks.LaserProjector), ADVANCED_LASER(ModBlocks.AdvancedLaser);
	
	Block marker;
	RegistryObject<Block> markerObject;
	
	private DeviceType(Block marker) {
		this.marker = marker;
	}
	
	private DeviceType(RegistryObject<Block> marker) {
		this.markerObject = marker;
	}
	
	public Block getBlock() {
		return marker == null ? markerObject.get() : marker;
	}
}

}
