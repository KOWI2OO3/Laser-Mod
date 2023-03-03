package KOWI2003.LaserMod.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joml.Vector3f;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.network.PacketComputerToMainThread;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityRemoteCC extends TileEntityLaserController {

	CCInterfacePeripheral peripheral;
    private LazyOptional<IPeripheral> peripheralCap;
	
	public TileEntityRemoteCC(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER_CONTROLLER_CC, pos, state);
		peripheral = new CCInterfacePeripheral(this);
	}
	
	public int getConnectedType() {
		if(!isConnected())
			return -1;
		return getConnectedType(controlPos);
	}
	
	public int getConnectedType(BlockPos pos) {
		Block block = getLevel().getBlockState(pos).getBlock();
		if(block == ModBlocks.Laser.get())
			return 1;
		if(block == ModBlocks.LaserProjector.get())
			return 2;
		if(block == ModBlocks.AdvancedLaser.get())
			return 3;
		return -1;
	}
	
	public TileEntityAdvancedLaser getAdvancedLaserControlBlockEntity() {
		BlockEntity BlockEntity = getControlTileEntity();
		if(BlockEntity instanceof TileEntityAdvancedLaser)
			return (TileEntityAdvancedLaser) BlockEntity;
		return null;
	}
	
	public void setAdvancedLaserRotation(Vec2 rotation) {
		TileEntityAdvancedLaser te = getAdvancedLaserControlBlockEntity();
		if(te == null)
			return;
		te.setRotationEular(rotation);
	}
	
	@Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side )
    {
		//sync();
        if( cap == Capabilities.CAPABILITY_PERIPHERAL) {
        	 if( peripheral == null ) peripheral = new CCInterfacePeripheral( this );
             if( peripheralCap == null ) peripheralCap = LazyOptional.of( () -> peripheral );
             return peripheralCap.cast();
        }
        return super.getCapability( cap, side );
    }

	public void callMethode(int index, Object[] args) {
		switch( index )
        {
			case 0:
			{
				BlockEntity BlockEntity = getControlTileEntity();
				if(BlockEntity instanceof TileEntityLaser)
					((TileEntityLaser)BlockEntity).setActive((boolean)args[0]);
				else if(BlockEntity instanceof TileEntityLaserProjector)
					((TileEntityLaserProjector)BlockEntity).setActive((boolean)args[0]);
				break;
			}
			
			case 1:
			{
				link(new BlockPos((double)args[0], (double)args[1], (double)args[2]));
				break;
			}
			
			case 2:
			{
				Disconnect();
				break;
			}
			
			case 4:
			{
				float r = (float)((double)args[0]);
				r = r > 1.0f ? 1.0f : r < 0 ? 0 : r;
				float g = (float)((double)args[1]);
				g = g > 1.0f ? 1.0f : g < 0 ? 0 : g;
				float b = (float)((double)args[2]);
				b = b > 1.0f ? 1.0f : b < 0 ? 0 : b;
				BlockEntity BlockEntity = getControlTileEntity();
				if(BlockEntity instanceof TileEntityLaser)
					((TileEntityLaser)BlockEntity).setColor(r, g, b);
				break;
			}
			
			case 5:
			{
				int mode = (int)((double)args[0]);
				BlockEntity BlockEntity = getControlTileEntity();
				if(BlockEntity instanceof TileEntityLaser) {
					if(((TileEntityLaser)BlockEntity).properties.hasUpgarde("mode")) {
						((TileEntityLaser)BlockEntity).mode = MODE.values()[mode];
						((TileEntityLaser)BlockEntity).sync();
					}
				}
				break;
			}
			
        	//setDirection
        	case 8:
        	{
        		if(args.length == 3) {
	        		float x = (float)(double)args[0];
	        		float y = (float)(double)args[1];
	        		float z = (float)(double)args[2];
	        		TileEntityAdvancedLaser te = getAdvancedLaserControlBlockEntity();
	        		if(te == null)
	        			break;
	        		Vector3f vec = new Vector3f(x, y, z);
	        		vec.normalize();
	        		te.setDirectionDirect(vec);
        		}
        		break;
        	}
			default:
				break;
        }
	}
	
public class CCInterfacePeripheral implements IDynamicPeripheral {

	TileEntityLaserController te;
	
	public CCInterfacePeripheral(TileEntityLaserController te) {
		this.te = te;
	}
	
	@Override
	public String getType() {
		return "Laser_Controller";
	}

	@Override
	public MethodResult callMethod(IComputerAccess compuer, ILuaContext content, int methodIndex, IArguments args)
			throws LuaException {
		try {
			switch( methodIndex )
	        {
		 		// setActive
	        	case 0:
	        	{
	        		if(args.getAll().length == 1) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			if(args.getAll()[0] instanceof Boolean)
	        				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	        			else
	        				throw new LuaException("Expected a Boolean value");
	        			return MethodResult.of();
	        		}
	        	}
	        	
	        	// connect
	        	case 1:
	        	{
	        		if(args.getAll().length == 3) {
	        			if(isConnected())
	        				throw new LuaException("Already has a Connection");
	        			if(args.getAll()[0] instanceof Number && args.getAll()[1] instanceof Number && args.getAll()[2] instanceof Number) {
	        				BlockPos pos = new BlockPos((double)args.getAll()[0], (double)args.getAll()[1], (double)args.getAll()[2]);
	        				if(getConnectedType(pos) == -1)
	        					return MethodResult.of(false);
	        				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	        				te.link(pos);
	        				return MethodResult.of(true);
	        			}else
	        				throw new LuaException("Expected 3 int values");
	        		}else
	    				throw new LuaException("Expected 3 int values as arguments");
	        	}
	        	
	        	// disconnect
	        	case 2:
	        	{
	        		if(args.getAll().length == 0) {
	        			if(!isConnected())
	        				return MethodResult.of(false);
	    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	    				te.Disconnect();
	    				return MethodResult.of(true);
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	//isConnected
	        	case 3:
	        	{
	        		if(args.getAll().length == 0) {
	        			return MethodResult.of(isConnected());
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	//setColor
	        	case 4:
	        	{
	        		if(args.getAll().length == 3) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			if(getConnectedType() == 2)
	        				throw new LuaException("Connected Device Cannot be colored!");
	        			if(args.getAll()[0] instanceof Number && args.getAll()[1] instanceof Number && args.getAll()[2] instanceof Number) {
	        				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	        				return MethodResult.of();
	        			}else
	        				throw new LuaException("Expected 3 float values");
	        		}else
	    				throw new LuaException("Expected 3 float values as arguments");
	        	}
	        	
	        	//setMode
	        	case 5:
	        	{
	        		if(args.getAll().length == 1) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			if(args.getAll()[0] instanceof Number) {
	        				int mode = (int)((double)args.getAll()[0]);
	        				int max = MODE.values().length;
	        				if(mode >= max)
	            				throw new LuaException("value must be between 0 and " + (max - 1));
	        				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	        				return MethodResult.of();
	        			}else
	        				throw new LuaException("Expected 1 int value");
	        		}else
	    				throw new LuaException("Expected 1 int value as the argument");
	        	}
	        	
	        	// canBeColored
	        	case 6:
	        	{
	        		if(args.getAll().length == 0) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			return MethodResult.of(getConnectedType() == 1);
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	// getDevice
	        	case 7:
	        	{
	        		if(args.getAll().length == 0) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			return MethodResult.of(getLevel().getBlockState(controlPos).getBlock().getName().toString());
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	//setDirection
	        	case 8:
	        	{
	        		if(args.getAll().length == 3) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			if(getConnectedType() != 3)
	        				throw new LuaException("Cannot set the direction of this device (device incapatible, should be a advanced laser)!");
	        			if(!(args.getAll()[0] instanceof Number))
	        				throw new LuaException("Expected a number argument! [argument 0]");
	        			if(!(args.getAll()[1] instanceof Number))
	        				throw new LuaException("Expected a number argument! [argument 1]");
	        			if(!(args.getAll()[2] instanceof Number))
	        				throw new LuaException("Expected a number argument! [argument 2]");
	    				PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
	        			return MethodResult.of();
	        		}
	        		throw new LuaException("Expected 3 number arguments!");
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
				"setActive",
				"connect",
				"disconnect",
				"isConnected",
				"setColor",
				"setMode",
				"canBeColored",
				"getDevice",
				"setDirection"
		};
	}
	
	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}
	
}

}
