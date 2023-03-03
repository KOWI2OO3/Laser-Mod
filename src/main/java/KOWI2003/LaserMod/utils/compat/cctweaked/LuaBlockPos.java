package KOWI2003.LaserMod.utils.compat.cctweaked;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.IDynamicLuaObject;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import net.minecraft.core.BlockPos;

public class LuaBlockPos implements IDynamicLuaObject {

	BlockPos pos;
	
	public LuaBlockPos(BlockPos pos) {
		this.pos = pos;
	}	
	
	@Override
	public MethodResult callMethod(ILuaContext context, int methodIndex, IArguments args) throws LuaException {
		try {
			switch( methodIndex )
	        {
				case 0:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(pos.getX());
					}else
		        		throw new LuaException("Expected 0 arguments!");
				}
				case 1:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(pos.getY());
					}else
		        		throw new LuaException("Expected 0 arguments!");
				}
				case 2:
				{
					if(args.getAll().length == 0) {
						return MethodResult.of(pos.getZ());
					}else
		        		throw new LuaException("Expected 0 arguments!");
				}
				case 3:
				{
					if(args.getAll().length == 3) {
						for (int i = 0; i < 3; i++)
							if(!(args.get(i) instanceof Number))
				        		throw new LuaException("Expected a number [argument " + i + "]");
						pos = pos.offset((int)args.getInt(0), args.getInt(1), args.getInt(2));
						return MethodResult.of(pos);
					}else
		        		throw new LuaException("Expected 3 arguments!");
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
				"getX",
				"getY",
				"getZ",
				"offset"
		};
	}
}
