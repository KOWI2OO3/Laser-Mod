package KOWI2003.LaserMod.utils.compat.cctweaked.projector;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Supplier;

import KOWI2003.LaserMod.gui.widgets.DataProperties.RangeProperty;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.IDynamicLuaObject;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

public class LuaProjectorWidget implements IDynamicLuaObject {

    Supplier<ProjectorWidgetData> dataSupplier;
    Runnable sync;

    public LuaProjectorWidget(Supplier<ProjectorWidgetData> dataSupplier, Runnable sync) {
        this.dataSupplier = dataSupplier;
        this.sync = sync;
    }

    public boolean isValid() {
        return dataSupplier != null & dataSupplier.get() != null;
    }

    @Override
    public String[] getMethodNames() {
        return new String[] {
            "getX",
            "getY",
            "getZ",
            "getWidth",
            "getHeight",
            "getDepth",
            "getRotation",
            "getScale",
            "getAlpha",
            "getType",
            "getId",
            "setX",
            "setY",
            "setZ",
            "setWidth",
            "setHeight",
            "setDepth",
            "setRotation",
            "setScale",
            "setAlpha",
        };
    }

    @Override
    public final MethodResult callMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        if(!isValid())
            throw new LuaException("Internal Object Error!");

        if(method < 9)
            return MethodResult.of(getValue(getMethodNames()[method].replace("get", "").toLowerCase()));

        if(method >= 11 && method <= 19 && arguments.getAll().length == 1) {
            setValue(getMethodNames()[method].replace("set", "").toLowerCase(), arguments.getAll()[0]);
            sync.run();
            return MethodResult.of();
        }

        switch(method) {
            case 9:
                return MethodResult.of(dataSupplier.get().type.name());
            case 10:
                return MethodResult.of(dataSupplier.get().id.toString());
            default:
                return callSubMethod(context, method - 20, arguments);
        }
    }

    public MethodResult callSubMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        throw new LuaException("Method index out of range!");
    }

    protected Object getValue(String name) throws LuaException {
        var data = dataSupplier.get();
        try {
            Field field = data.getClass().getField(name);
            return field.get(dataSupplier.get());
        }catch (Exception ex) {
            throw new LuaException("Internal Object Error!");
        }
    }

    public UUID getUUID() {
        return isValid() ? dataSupplier.get().id : null;
    }

    private void setValue(String name, Object value) throws LuaException {
        var data = dataSupplier.get();
        try {
            Field field = data.getClass().getField(name);
            if(field.getType().isInstance(value)) {
                RangeProperty range = field.getAnnotation(RangeProperty.class);
                if(range != null && value instanceof Number number) {
                    if(number.doubleValue() < range.min() || number.doubleValue() > range.max()) 
                        throw new LuaException("The number must be between [" + range.min() + " ~ " + range.max() + "]");
                }
                field.set(dataSupplier.get(), value);
            }if(field.getType() == float.class && value instanceof Number num) {
                RangeProperty range = field.getAnnotation(RangeProperty.class);
                if(range != null) {
                    if(num.doubleValue() < range.min() || num.doubleValue() > range.max()) 
                        throw new LuaException("The number must be between [" + range.min() + " ~ " + range.max() + "]");
                }
                field.set(dataSupplier.get(), num.floatValue());
            }else
                throw new LuaException("The value must be of type " + field.getType().getSimpleName());
            sync.run();
        }catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
            throw new LuaException("Internal Object Error!");
        }
    }
}
