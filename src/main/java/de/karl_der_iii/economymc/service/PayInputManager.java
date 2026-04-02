package de.karl_der_iii.economymc.service;

import de.karl_der_iii.economymc.menu.PlotzPayMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PayInputManager {
    private record PendingPay(UUID targetId, String targetName) {}

    private static final Map<UUID, PendingPay> WAITING = new ConcurrentHashMap<>();

    private PayInputManager() {}

    public static void start(ServerPlayer player, UUID targetId, String targetName) {
        WAITING.put(player.getUUID(), new PendingPay(targetId, targetName));
        player.sendSystemMessage(Component.literal(LanguageManager.format("pay.menu.enter_amount", targetName)));
        player.closeContainer();
    }

    public static boolean handleChat(ServerPlayer player, String message) {
        PendingPay pending = WAITING.remove(player.getUUID());
        if (pending == null) {
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(message.trim());
        } catch (NumberFormatException e) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("pay.invalid_number")));
            PlotzPayMenu.open(player);
            return true;
        }

        if (amount <= 0) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("pay.invalid_number")));
            PlotzPayMenu.open(player);
            return true;
        }

        if (pending.targetId().equals(player.getUUID())) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("pay.self")));
            PlotzPayMenu.open(player);
            return true;
        }

        ServerPlayer target = player.server.getPlayerList().getPlayer(pending.targetId());
        if (target == null) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("pay.target_offline")));
            PlotzPayMenu.open(player);
            return true;
        }

        if (!BalanceManager.removeBalance(player.getUUID(), amount)) {
            player.sendSystemMessage(Component.literal(LanguageManager.tr("pay.not_enough")));
            PlotzPayMenu.open(player);
            return true;
        }

        BalanceManager.addBalance(pending.targetId(), amount);
        ScoreboardManager.update(player.server);

        player.sendSystemMessage(Component.literal(LanguageManager.format("pay.sent", amount, pending.targetName())));
        target.sendSystemMessage(Component.literal(LanguageManager.format("pay.received", amount, player.getGameProfile().getName())));

        TransactionHistoryManager.add(
            player.getUUID(),
            LanguageManager.format("history.pay.sent", pending.targetName(), amount)
        );
        TransactionHistoryManager.add(
            pending.targetId(),
            LanguageManager.format("history.pay.received", player.getGameProfile().getName(), amount)
        );

        PlotzPayMenu.open(player);
        return true;
    }
}