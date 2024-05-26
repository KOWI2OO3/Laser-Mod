package KOWI2003.LaserMod.tileentities;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.network.PacketComputerToMainThread;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import KOWI2003.LaserMod.tileentities.projector.ProjectorTemplates;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.utils.TileEntityUtils;
import KOWI2003.LaserMod.utils.compat.cctweaked.LuaMap;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaItemWidget;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaPlayerWidget;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaProjectorWidget;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaShapeWidget;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaTextBoxWidget;
import KOWI2003.LaserMod.utils.compat.cctweaked.projector.LuaTextWidget;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaTable;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityRemoteCC extends TileEntityLaserController {

	public final ConcurrentHashMap<BlockPos, BlockEntity> map = new ConcurrentHashMap<>(); 

	CCInterfacePeripheral peripheral;
    private LazyOptional<IPeripheral> peripheralCap;
	
	public TileEntityRemoteCC(BlockPos pos, BlockState state) {
		super(ModTileTypes.LASER_CONTROLLER_CC, pos, state);
		peripheral = new CCInterfacePeripheral(this);
	}

	@Override
	public void link(BlockPos pos) {
		super.link(pos);
		map.put(pos, getControlTileEntity());
	}

	@Override
	public void Disconnect() {
		map.remove(controlPos);
		super.Disconnect();
	}
	
	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
		if(controlPos != null)
			map.put(controlPos, getControlTileEntity());
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
		int type = getConnectedType();
		switch(type) {
			case 1:
				callLaserMethode(index, args);
				break;
			case 2:
				callProjectorMethode(index, args);
				break;
			default:
				callDefaultMethode(index, args);
				break;
		}
	}

	public void callDefaultMethode(int index, Object[] args) {
		switch( index )
        {
			case 0:
			{
				link(new BlockPos((double)args[0], (double)args[1], (double)args[2]));
				break;
			}
			
			case 1:
			{
				Disconnect();
				break;
			}
			
			default:
				break;
		}
	}

	public void callProjectorMethode(int index, Object[] args) {
		BlockEntity blockEntity = getControlTileEntity();
		switch( index )
        {
			case 0:
			case 1:
				callDefaultMethode(index, args);
				break;
			case 3:
				if(blockEntity instanceof TileEntityLaserProjector projector)
					projector.setActive((boolean)args[0]);
				break;
			case 4:
				if(args.length == 1) {
					ProjectorTemplates template = null;
					if(args[0] instanceof Number ordinal && ProjectorTemplates.values().length > ordinal.intValue())
						template = ProjectorTemplates.values()[ordinal.intValue()];
					else if(args[0] instanceof String name)
						template = ProjectorTemplates.valueOf(name);

					if(template != null && blockEntity instanceof TileEntityLaserProjector projector)
						projector.setTemplate(template);
				}
				break;
			default:
				break;
		}
	}

	public void callLaserMethode(int index, Object[] args) {
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

	TileEntityRemoteCC te;
	
	public CCInterfacePeripheral(TileEntityRemoteCC te) {
		this.te = te;
	}
	
	@Override
	public String getType() {
		return "Laser_Controller";
	}

	@Override
	public MethodResult callMethod(IComputerAccess computer, ILuaContext context, int methodIndex, IArguments args) throws LuaException {
		int type = te.getConnectedType();
		switch(type) {
			case 1:	// Laser
			case 3: // Advanced Laser
				return callLaserMethod(computer, context, methodIndex, args);
			case 2:
				return callProjectorMethod(computer, context, methodIndex, args);
				// return callProjectorMethod(computer, context, methodIndex, args);
			default:
				return callDefaultMethod(computer, context, methodIndex, args);
		}
	}

	@Override
	public String[] getMethodNames() {
		int type = te.getConnectedType();
		switch(type) {
			case 1:	// Laser
			case 3: // Advanced Laser
				return new String[] {
					"setActive",
					"connect",
					"disconnect",
					"isConnected",
					"getDeviceType",
					"getDeviceName",
					"setColor",
					"setMode",
					"canBeColored",
					"getDevice",
					"setDirection"
				};
			case 2: // Laser Projector
				return new String[] {
					"connect",
					"disconnect",
					"isConnected",
					"getDeviceType",
					"getDeviceName",
					"setActive",
					"setTemplate",
					"getWidgets",
					"getWidgetsOfType",
					"createWidget",
					"removeWidget"
				};
			default:
				return new String[] {
					"connect",
					"disconnect",
					"isConnected",
					"getDeviceType",
					"getDeviceName",
				};
		}
	}
	
	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

	public Object[] getTile() {
		if(te.controlPos == null)
			return null;

		BlockEntity tile = null;

		if(te.level.isClientSide) {
			tile = getTileClient();
		}else
			tile = te.level.getBlockEntity(te.controlPos);
		
		map.put(te.controlPos, tile);

		return new Object[] {tile};
	}

	@OnlyIn(Dist.CLIENT)
	private BlockEntity getTileClient() {
		return Minecraft.getInstance().level.getBlockEntity(te.controlPos);
	}

	public MethodResult callProjectorMethod(IComputerAccess computer, ILuaContext context, int methodIndex, IArguments args) throws LuaException {
		BlockEntity tile = null;
		if(!map.containsKey(te.controlPos)) { 
			// Requires the computer thread to wait on the mainthread
			MethodResult result = context.executeMainThreadTask(this::getTile);
			result.getResult();
			try{
				Thread.sleep(40);
			}catch(Exception ex) {}
		}
		
		if(map.containsKey(te.controlPos))
			tile = map.get(te.controlPos);

		switch(methodIndex) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				return callDefaultMethod(computer, context, methodIndex, args);
			case 5: //setActive
				if(args.getAll().length == 1) {
					if(!isConnected())
						throw new LuaException("No Connected Device!");
					if(args.getAll()[0] instanceof Boolean)
						PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
					else
						throw new LuaException("Expected a Boolean value");
					return MethodResult.of();
				}
			case 6: //setTemplate
				if(args.getAll().length == 1) {
					if(!isConnected())
						throw new LuaException("No Connected Device!");

					boolean numberCondition = args.getAll()[0] instanceof Number && ((Number)args.getAll()[0]).intValue() < ProjectorTemplates.values().length;
					boolean nameCondition = args.getAll()[0] instanceof String && List.of(ProjectorTemplates.values()).stream().map(v -> v.name()).toList().contains((String)args.getAll()[0]);
					if(numberCondition || nameCondition) {
						PacketHandler.sendToServer(new PacketComputerToMainThread(getBlockPos(), methodIndex, args.getAll()));
						return MethodResult.of();
					}
				}
				throw new LuaException("Expected a number value as arguments or string of " + List.of(ProjectorTemplates.values()).stream().map(v -> v.name()).toList());
			case 7: //getWidgets
				if(tile instanceof TileEntityLaserProjector projector) {
					LuaTable<Integer, LuaProjectorWidget> table = new LuaMap<>();
					for (int i = 0; i < projector.context.getWidgets().size(); i++) {
						var value = projector.context.getWidgets().get(i);
						final int j = i;
						table.put(i, getLuaWidget(() -> projector.context.getWidgets().get(j), context, value.type));
					}
					return MethodResult.of(table);
				}
				throw new LuaException("The connected block is not an projector!");
			case 8: //getWidgetsOfType
				if(args.count() != 1)
					throw new LuaException("Expected 1 argument string of " + List.of(ProjectorWidgetTypes.values()).stream().map(v -> v.name()).toList());
				if(!(args.getAll()[0] instanceof String))
					throw new LuaException("Expected 1 argument string of " + List.of(ProjectorWidgetTypes.values()).stream().map(v -> v.name()).toList());
				if(tile instanceof TileEntityLaserProjector projector) {
					LuaTable<Integer, LuaProjectorWidget> table = new LuaMap<>();
					for (int i = 0; i < projector.context.getWidgets().size(); i++) {
						ProjectorWidgetData value = projector.context.getWidgets().get(i);
						final UUID id = value.id;
						if(id != null && projector.context.getWidget(id) != null)
							table.put(i, getLuaWidget(() -> projector.context.getWidget(id), context, value.type));
					}
					return MethodResult.of(table);
				}
				throw new LuaException("The connected block is not an projector!");
			case 9:
				if(args.count() != 1)
					throw new LuaException("Expected 1 argument string of " + List.of(ProjectorWidgetTypes.values()).stream().map(v -> v.name()).toList());
				if(!(args.getAll()[0] instanceof String))
					throw new LuaException("Expected 1 argument string of " + List.of(ProjectorWidgetTypes.values()).stream().map(v -> v.name()).toList());
				if(tile instanceof TileEntityLaserProjector projector) {
					ProjectorWidgetTypes type =  ProjectorWidgetTypes.valueOf((String)args.getAll()[0]);
					final UUID id = projector.context.addWidget(type);
					if(id == null || projector.context.getWidget(id) == null)
						throw new LuaException("An internal error occured");
					return MethodResult.of(getLuaWidget(() -> projector.context.getWidget(id), context, type));
				}
				throw new LuaException("The connected block is not an projector!");
			case 10:
				if(args.count() != 1)
					throw new LuaException("Expected 1 argument string of " + List.of(ProjectorWidgetTypes.values()).stream().map(v -> v.name()).toList());
				if(!(args.getAll()[0] instanceof String))
					throw new LuaException("Expected 1 argument string of the id of the widget (use widget.getId())");
				if(tile instanceof TileEntityLaserProjector projector) {
					final UUID id;
					try {
						id = UUID.fromString((String)args.getAll()[0]);
					}catch(Exception ex) {
						throw new LuaException("IncorrectFormatException: Failed to parse id!");
					}

					if(id == null)
						throw new LuaException("An internal error occured");
					return MethodResult.of(projector.context.removeWidget(id));
				}
				throw new LuaException("The connected block is not an projector!");
			default:
				throw new LuaException("Method index out of range!");
		}
	}

	private LuaProjectorWidget getLuaWidget(Supplier<ProjectorWidgetData> data, ILuaContext context, ProjectorWidgetTypes type) throws LuaException {
		BlockEntity tile = null;
		if(!map.containsKey(te.controlPos)) { 
			// Requires the computer thread to wait on the mainthread
			MethodResult result = context.executeMainThreadTask(this::getTile);
			result.getResult();
			try{
				Thread.sleep(40);
			}catch(Exception ex) {}
		}
		
		if(map.containsKey(te.controlPos))
			tile = map.get(te.controlPos);

		final var control = tile;

		Runnable sync = () -> {
			if(control != null) 
				TileEntityUtils.syncToServer(control);
		};

		if(type == ProjectorWidgetTypes.Text)
			return new LuaTextWidget(data, sync);
		if(type == ProjectorWidgetTypes.TextBox)
			return new LuaTextBoxWidget(data, sync);
		if(type == ProjectorWidgetTypes.Item)
			return new LuaItemWidget(data, sync);
		if(type == ProjectorWidgetTypes.Player)
			return new LuaPlayerWidget(data, sync);
		if(type == ProjectorWidgetTypes.Shape)
			return new LuaShapeWidget(data, sync);

		return new LuaProjectorWidget(data, sync);
	}

	public MethodResult callDefaultMethod(IComputerAccess compuer, ILuaContext content, int methodIndex, IArguments args) throws LuaException {
		switch(methodIndex) {
			// connect
			case 0:
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
			case 1:
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
			case 2:
			{
				if(args.getAll().length == 0) {
					return MethodResult.of(isConnected());
				}else
					throw new LuaException("Expected no arguments");
			}

			//getDeviceType
			case 3:
				if(args.getAll().length == 0) {
					if(!isConnected())
						throw new LuaException("No Connected Device!");
					int type = te.getConnectedType();
					return MethodResult.of(type);
				}else
					throw new LuaException("Expected no arguments");

			//getDeviceName
			case 4:
				if(args.getAll().length == 0) {
					if(!isConnected())
						throw new LuaException("No Connected Device!");
					switch(te.getConnectedType()){
						case 1:
							return MethodResult.of("Laser");
						case 3:
							return MethodResult.of("Projector");
						case 2:
							return MethodResult.of("Advanced Laser");
						
						default:
							return MethodResult.of(getLevel().getBlockState(controlPos).getBlock().getRegistryName().toString());
					}
				}else
					throw new LuaException("Expected no arguments");
			default:
				throw new LuaException("Method index out of range!");
		}
	}

	public MethodResult callLaserMethod(IComputerAccess compuer, ILuaContext content, int methodIndex, IArguments args) throws LuaException {
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
	        	
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					return callDefaultMethod(compuer, content, methodIndex - 1, args);
	        	
	        	//setColor
	        	case 6:
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
	        	case 7:
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
	        	case 8:
	        	{
	        		if(args.getAll().length == 0) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			return MethodResult.of(getConnectedType() == 1);
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	// getDevice
	        	case 9:
	        	{
	        		if(args.getAll().length == 0) {
	        			if(!isConnected())
	        				throw new LuaException("No Connected Device!");
	        			return MethodResult.of(getLevel().getBlockState(controlPos).getBlock().getRegistryName().toString());
	        		}else
	    				throw new LuaException("Expected no arguments");
	        	}
	        	
	        	//setDirection
	        	case 10:
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
	
	}
}
