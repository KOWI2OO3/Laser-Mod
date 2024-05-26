package KOWI2003.LaserMod.utils.compat.cctweaked.projector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class LuaItemWidget extends LuaProjectorWidget {

    public LuaItemWidget(Supplier<ProjectorWidgetData> dataSupplier, Runnable sync) {
        super(dataSupplier, sync);
    }

    @Override
    public String[] getMethodNames() {
        List<String> methods = new ArrayList<>(List.of(super.getMethodNames()));
        methods.addAll(List.of(
            "getItem",
            "isOnSurface",
            "setItem",
            "setOnSurface"
        ));
        return methods.toArray(i -> new String[i]);
    }

    @Override
    public MethodResult callSubMethod(ILuaContext context, int method, IArguments arguments) throws LuaException {
        if(dataSupplier.get() instanceof ProjectorItemData data) {
            switch(method) {
                case 0:
                    return MethodResult.of(data.item.isEmpty() ? null : data.item.getItem().getRegistryName().toString());
                case 1:
                    return MethodResult.of(data.onSurface);
                case 2:
                    if(arguments.count() == 1){
                        if(arguments.getAll()[0] instanceof String txt) {
                            try {
                                ItemStack stack = null;
                                if(txt == null || txt.isEmpty())
                                    stack = ItemStack.EMPTY;
                                else
                                    stack = new ItemStack(Registry.ITEM.get(new ResourceLocation(txt)));
                                    
                                data.item = stack == null ? ItemStack.EMPTY : stack;
                                sync.run();
                                return MethodResult.of();
                            }catch(Exception ex) {}
                        }else if(arguments.getAll()[0] instanceof ItemStack stack) {
                            data.item = stack;
                            sync.run();
                            return MethodResult.of();
                        }
                    } 
                    throw new LuaException("Expected 1 string or itemstack argument");
                case 3:
                    if(arguments.count() == 1 && arguments.getAll()[0] instanceof Boolean toggle){
                        data.onSurface = toggle;
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
