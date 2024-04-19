package KOWI2003.LaserMod.items;

import java.util.function.Consumer;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemUpgradeBase.LaserTools;
import KOWI2003.LaserMod.items.render.RenderMultiTool;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemLaserMutliTool extends LaserItem {
	
	public ItemLaserMutliTool(Properties properties, int maxCharge) {
		super(properties, maxCharge);
		defaultProperties.setProperty(LaserProperties.Properties.SPEED, 1.4f);
		defaultProperties.setProperty(LaserProperties.Properties.DAMAGE, 4.0f);
		defaultProperties.setProperty(LaserProperties.Properties.DURABILITY, 400);
	}

	public ItemLaserMutliTool(Properties properties) {
		this(properties, 1000);
	}
	
	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}

	@Override
	public <T extends LivingEntity> void damage(ItemStack stack, int amount, T entity, Consumer<T> entityConsumer) {
		super.damage(stack, amount, entity, entityConsumer);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return RenderMultiTool.get();
			}
		});
	}
	
	@Override
	public boolean canBeUsed(ItemUpgradeBase upgrade) {
		return upgrade.isUsefullForLaserTool(LaserTools.OMNI);
	}

}
