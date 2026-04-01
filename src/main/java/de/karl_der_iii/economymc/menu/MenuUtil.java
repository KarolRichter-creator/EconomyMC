package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.BalanceManager;
import de.karl_der_iii.economymc.service.LanguageManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class MenuUtil {
    private MenuUtil() {}

    public static ItemStack named(Item item, String text) {
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(text));
        return stack;
    }

    public static ItemStack playerInfoHead(ServerPlayer player) {
        long money = BalanceManager.getBalance(player.getUUID());
        return named(
            Items.EMERALD,
            "§e" + player.getGameProfile().getName()
                + " §7| "
                + LanguageManager.tr("money.label")
                + ": $" + money
        );
    }

    public static void putPlayerInfoHead(net.minecraft.world.Container container, ServerPlayer player, int slot) {
        container.setItem(slot, playerInfoHead(player));
    }
}