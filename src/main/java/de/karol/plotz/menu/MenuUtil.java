package de.karol.plotz.menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class MenuUtil {
    private MenuUtil() {}

    public static ItemStack named(Item item, String text) {
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(text));
        return stack;
    }
}