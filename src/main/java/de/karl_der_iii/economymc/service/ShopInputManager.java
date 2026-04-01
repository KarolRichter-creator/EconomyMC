package de.karl_der_iii.economymc.service;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.menu.PlotzShopSellMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ShopInputManager {
    private static final Map<UUID, Boolean> WAITING_FOR_PRICE = new ConcurrentHashMap<>();
    private static final Map<UUID, Boolean> TRANSITIONING = new ConcurrentHashMap<>();

    private ShopInputManager() {}

    public static void waitForPrice(ServerPlayer player) {
        WAITING_FOR_PRICE.put(player.getUUID(), true);
        TRANSITIONING.put(player.getUUID(), true);
        player.sendSystemMessage(Component.literal(LanguageManager.tr("shop.input.enter_price")));
        player.closeContainer();
    }

    public static boolean isTransitioning(UUID playerId) {
        return TRANSITIONING.containsKey(playerId);
    }

    public static boolean handleChat(ServerPlayer player, String message) {
        if (!WAITING_FOR_PRICE.containsKey(player.getUUID())) {
            return false;
        }

        WAITING_FOR_PRICE.remove(player.getUUID());
        TRANSITIONING.remove(player.getUUID());

        PlotzStore.ShopDraft draft = PlotzStore.getShopDraft(player.getUUID());
        if (draft == null || draft.items().isEmpty()) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("shop.input.no_draft")));
            return true;
        }

        try {
            int price = Integer.parseInt(message.trim());
            if (price <= 0) {
                player.sendSystemMessage(Component.literal(LanguageManager.tr("shop.input.price_positive")));
            } else {
                PlotzStore.updateShopDraftPrice(player.getUUID(), price);
                player.sendSystemMessage(Component.literal(LanguageManager.tr("shop.input.price_set") + price));
            }
        } catch (NumberFormatException e) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("shop.input.invalid_number")));
        }

        PlotzShopSellMenu.open(player);
        return true;
    }
}