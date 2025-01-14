package KOWI2003.LaserMod.entities;

import javax.annotation.Nonnull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.item.ItemStack;

public class ModEntityDataSerializers {

	public static final EntityDataSerializer<ItemStack[]> UPGRADES = new EntityDataSerializer<ItemStack[]>() {
		
		@Override
		public void write(@Nonnull FriendlyByteBuf buf, @Nonnull ItemStack[] stacks) {
			buf.writeInt(stacks.length);
			for (ItemStack stack : stacks)
				buf.writeItem(stack);
		}

		public ItemStack[] read(@Nonnull FriendlyByteBuf buf) {
			int size = buf.readInt();
			ItemStack[] stacks = new ItemStack[size];
			for(int i = 0; i < size; i++)
				stacks[i] = buf.readItem();
			return stacks;
		}
		
		@Override
		public ItemStack[] copy(@Nonnull ItemStack[] stacks) {
			ItemStack[] copy = new ItemStack[stacks.length];
			for (int i = 0; i < copy.length; i++)
				copy[i] = stacks[i].copy();
			return copy;
		}
	};
	
}
