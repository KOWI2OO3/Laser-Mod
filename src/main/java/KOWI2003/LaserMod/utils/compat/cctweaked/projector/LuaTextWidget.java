package KOWI2003.LaserMod.utils.compat.cctweaked.projector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

public class LuaTextWidget extends LuaProjectorWidget {

    public LuaTextWidget(Supplier<ProjectorWidgetData> dataSupplier, Runnable sync) {
        super(dataSupplier, sync);
    }

    @Override
    public String[] getMethodNames() {
        List<String> methods = new ArrayList<>(List.of(super.getMethodNames()));
        methods.addAll(List.of(
            "getText",
            "isCentered",
            "setText",
            "setCentered"
        ));
        return methods.toArray(i -> new String[i]);
    }

    @Override
    public MethodResult callSubMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        if(dataSupplier.get() instanceof ProjectorTextData data) {
            switch(method) {
                case 0:
                    return MethodResult.of(data.text);
                case 1:
                    return MethodResult.of(data.Centered);
                case 2:
                    if(arguments.count() == 1 && arguments.getAll()[0] instanceof String txt){
                        data.text = txt;
                        sync.run();
                        return MethodResult.of();
                    } 
                    throw new LuaException("Expected 1 string argument");
                case 3:
                    if(arguments.count() == 1 && arguments.getAll()[0] instanceof Boolean toggle){
                        data.Centered = toggle;
                        sync.run();
                        return MethodResult.of();
                    } 
                    throw new LuaException("Expected 1 boolean argument");
                default:
                    break;
            }
        }
        return super.callSubMethod(context, method, arguments);
    }
    
}
