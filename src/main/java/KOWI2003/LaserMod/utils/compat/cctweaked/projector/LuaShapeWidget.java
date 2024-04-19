package KOWI2003.LaserMod.utils.compat.cctweaked.projector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.utils.Utils;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData.ShapeType;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

public class LuaShapeWidget extends LuaProjectorWidget {

    public LuaShapeWidget(Supplier<ProjectorWidgetData> dataSupplier, Runnable sync) {
        super(dataSupplier, sync);
    }

    @Override
    public String[] getMethodNames() {
        List<String> methods = new ArrayList<>(List.of(super.getMethodNames()));
        methods.addAll(List.of(
            "getType",
            "getColor",
            "setType",
            "setColor"
        ));
        return methods.toArray(i -> new String[i]);
    }

    @Override
    public MethodResult callSubMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        if(dataSupplier.get() instanceof ProjectorShapeData data) {
            switch(method) {
                case 0:
                    return MethodResult.of(data.type.toString());
                case 1:
                    return MethodResult.of(data.color);
                case 2:
                    if(arguments.count() == 1){
                        if(arguments.getAll()[0] instanceof String txt) {
                            if(List.of(ShapeType.values()).stream().map(v -> v.name()).toList().contains(txt)) {
                                data.type = ShapeType.valueOf(txt);
                                sync.run();
                                return MethodResult.of();
                            }
                            throw new LuaException("Expected argument 1 must be any of " + List.of(ShapeType.values()).stream().map(v -> v.name()).toList());
                        }
                        if(arguments.getAll()[0] instanceof Number number) {
                            if(ShapeType.values().length > number.intValue()) {
                                data.type = ShapeType.values()[number.intValue()];
                                sync.run();
                                return MethodResult.of();
                            }
                            throw new LuaException("Expected argument 1 must be between [0 ~ " + (ShapeType.values().length-1) + "]");
                        }
                    } 
                    throw new LuaException("Expected 1 number or string: " + List.of(ShapeType.values()).stream().map(v -> v.name()).toList() + " argument");
                case 3:
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
                            
                            data.color = color;
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
