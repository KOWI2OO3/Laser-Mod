package KOWI2003.LaserMod.items.render.model;

import KOWI2003.LaserMod.items.ItemLaserArmorBase;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LaserArmorModelHandler {

	public static <T extends LivingEntity> HumanoidModel<T> getModel(ItemStack stack, HumanoidModel<T> _default) {
		if(!(stack.getItem() instanceof ItemLaserArmorBase))
			return _default;
		ItemLaserArmorBase armorpiece = (ItemLaserArmorBase)stack.getItem();
		boolean isActive = LaserItemUtils.isExtended(stack);
		EquipmentSlot slot = armorpiece.getEquipmentSlot(stack);
		
		LaserArmorModel<T> model = new LaserArmorModel<T>(stack);
		model.HelmetVisible = slot == EquipmentSlot.HEAD;
		model.HelmetActive.visible = slot == EquipmentSlot.HEAD && isActive;
		
		model.ChestVisible = slot == EquipmentSlot.CHEST;
		model.ChestActive.visible = slot == EquipmentSlot.CHEST && isActive;
		
		model.LeggingsVisible = slot == EquipmentSlot.LEGS;
		model.LeggingsActive = slot == EquipmentSlot.LEGS && isActive;
		

		model.BootsVisible = slot == EquipmentSlot.FEET;
		model.BootsActive = slot == EquipmentSlot.FEET && isActive;
		
		_default.copyPropertiesTo(model);
		return model;
	}
}
