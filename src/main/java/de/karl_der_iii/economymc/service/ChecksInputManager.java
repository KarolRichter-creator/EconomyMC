package de.karl_der_iii.economymc.service;

import de.karl_der_iii.economymc.menu.PlotzChecksMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ChecksInputManager {
    private enum Stage {
        CREATE_AMOUNT,
        CREATE_CODE,
        REDEEM_CODE
    }

    private record Draft(Stage stage, int amount, String checkId) {}

    private static final Map<UUID, Draft> DRAFTS = new ConcurrentHashMap<>();

    private ChecksInputManager() {}

    public static void startCreate(ServerPlayer player) {
        DRAFTS.put(player.getUUID(), new Draft(Stage.CREATE_AMOUNT, 0, ""));
        player.sendSystemMessage(Component.literal("§eEnter the check amount in chat now."));
        player.closeContainer();
    }

    public static void startRedeem(ServerPlayer player, String checkId) {
        DRAFTS.put(player.getUUID(), new Draft(Stage.REDEEM_CODE, 0, checkId));
        player.sendSystemMessage(Component.literal("§eEnter the check code in chat now."));
        player.closeContainer();
    }

    public static boolean handleChat(ServerPlayer player, String message) {
        Draft draft = DRAFTS.get(player.getUUID());
        if (draft == null) return false;

        switch (draft.stage()) {
            case CREATE_AMOUNT -> {
                int amount;
                try {
                    amount = Integer.parseInt(message.trim());
                } catch (NumberFormatException e) {
                    player.sendSystemMessage(Component.literal("§cThat is not a valid number."));
                    return true;
                }

                if (amount <= 0) {
                    player.sendSystemMessage(Component.literal("§cAmount must be above 0."));
                    return true;
                }

                if (!BalanceManager.removeBalance(player.getUUID(), amount)) {
                    player.sendSystemMessage(Component.literal("§cYou do not have enough money."));
                    DRAFTS.remove(player.getUUID());
                    PlotzChecksMenu.open(player, 0);
                    return true;
                }

                DRAFTS.put(player.getUUID(), new Draft(Stage.CREATE_CODE, amount, ""));
                player.sendSystemMessage(Component.literal("§eEnter the check code in chat now."));
                return true;
            }
            case CREATE_CODE -> {
                DRAFTS.remove(player.getUUID());
                CheckManager.createCheck(
                    player.getUUID(),
                    player.getGameProfile().getName(),
                    draft.amount(),
                    message.trim()
                );
                player.sendSystemMessage(Component.literal("§aCheck created."));
                PlotzChecksMenu.open(player, 0);
                return true;
            }
            case REDEEM_CODE -> {
                DRAFTS.remove(player.getUUID());
                boolean ok = CheckManager.redeem(
                    draft.checkId(),
                    message.trim(),
                    player.getUUID(),
                    player.getGameProfile().getName()
                );
                if (!ok) {
                    player.sendSystemMessage(Component.literal("§cInvalid code or check already redeemed."));
                } else {
                    player.sendSystemMessage(Component.literal("§aCheck redeemed successfully."));
                }
                PlotzChecksMenu.open(player, 0);
                return true;
            }
        }

        return false;
    }
}