package KOWI2003.LaserMod.utils.compat.cctweaked.projector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextBoxData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.utils.Utils;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

public class LuaTextBoxWidget extends LuaProjectorWidget {

    public LuaTextBoxWidget(Supplier<ProjectorWidgetData> dataSupplier, Runnable sync) {
        super(dataSupplier, sync);
    }

    @Nonnull
    @Override
    public String[] getMethodNames() {
        List<String> methods = new ArrayList<>(List.of(super.getMethodNames()));
        methods.addAll(List.of(
            "getText",
            "isCentered",
            "getColor",
            "setText",
            "setCentered",
            "setColor"
        ));
        return methods.toArray(i -> new String[i]);
    }

    @Override
    public MethodResult callSubMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        if(dataSupplier.get() instanceof ProjectorTextBoxData data) {
            switch(method) {
                case 0:
                    return MethodResult.of(data.text);
                case 1:
                    return MethodResult.of(data.isCentered);
                case 2:
                    return MethodResult.of(data.textColor);
                case 3:
                    if(arguments.count() == 1 && arguments.getAll()[0] instanceof String txt){
                        data.text = txt;
                        sync.run();
                        return MethodResult.of();
                    } 
                    throw new LuaException("Expected 1 string argument");
                case 4:
                    if(arguments.count() == 1 && arguments.getAll()[0] instanceof Boolean toggle){
                        data.isCentered = toggle;
                        sync.run();
                        return MethodResult.of();
                    } 
                    throw new LuaException("Expected 1 boolean argument");
                case 5:
                    if(arguments.count() >= 3 && arguments.count() <= 4){
                        boolean areValues = true; 
                        for (var arg : arguments.getAll()) {
                            if(!(arg instanceof Number)) {
                                areValues = false; 
                                break;
                            }
                        }
                        if(areValues) {
                            float[] color = new float[arguments.getAll().length];
                            for (int i = 0; i < arguments.getAll().length; i++) {
                                var arg = arguments.getAll()[i];
                                if(arg instanceof Number num)
                                    color[i] = num.floatValue();
                            }
                            color = Utils.parseColor(color);
                            
                            data.textColor = color;
                            sync.run();
                            return MethodResult.of();
                        }
                    } 
                    throw new LuaException("Expected 3 or 4 float argument as rgb or rgba");
                default:
                    break;
            }
        }
        return super.callSubMethod(context, method, arguments);
    }
    
}
