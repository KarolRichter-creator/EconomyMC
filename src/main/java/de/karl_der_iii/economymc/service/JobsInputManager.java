package de.karl_der_iii.economymc.service;

import de.karl_der_iii.economymc.menu.PlotzJobsMenu;
import de.karl_der_iii.economymc.menu.PlotzServerModeMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class JobsInputManager {
    private enum Stage {
        TITLE,
        DESCRIPTION,
        REWARD,
        DUE_DAYS
    }

    private record Draft(boolean serverJob, Stage stage, String title, String description, int reward) {}

    private static final Map<UUID, Draft> DRAFTS = new ConcurrentHashMap<>();

    private JobsInputManager() {}

    public static void startPlayerJob(ServerPlayer player) {
        DRAFTS.put(player.getUUID(), new Draft(false, Stage.TITLE, "", "", 0));
        player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.title")));
        player.closeContainer();
    }

    public static void startServerJob(ServerPlayer player) {
        DRAFTS.put(player.getUUID(), new Draft(true, Stage.TITLE, "", "", 0));
        player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.server_title")));
        player.closeContainer();
    }

    public static boolean handleChat(ServerPlayer player, String message) {
        Draft draft = DRAFTS.get(player.getUUID());
        if (draft == null) {
            return false;
        }

        switch (draft.stage()) {
            case TITLE -> {
                DRAFTS.put(player.getUUID(), new Draft(draft.serverJob(), Stage.DESCRIPTION, message, "", 0));
                player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.description")));
                return true;
            }
            case DESCRIPTION -> {
                DRAFTS.put(player.getUUID(), new Draft(draft.serverJob(), Stage.REWARD, draft.title(), message, 0));
                player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.reward")));
                return true;
            }
            case REWARD -> {
                int reward;
                try {
                    reward = Integer.parseInt(message.trim());
                } catch (NumberFormatException e) {
                    player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.invalid_number")));
                    return true;
                }

                if (reward <= 0) {
                    player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.reward_positive")));
                    return true;
                }

                DRAFTS.put(player.getUUID(), new Draft(draft.serverJob(), Stage.DUE_DAYS, draft.title(), draft.description(), reward));
                player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.due_days")));
                return true;
            }
            case DUE_DAYS -> {
                int dueDays;
                try {
                    dueDays = Integer.parseInt(message.trim());
                } catch (NumberFormatException e) {
                    player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.invalid_number")));
                    return true;
                }

                if (dueDays <= 0) {
                    player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.days_positive")));
                    return true;
                }

                DRAFTS.remove(player.getUUID());

                if (draft.serverJob()) {
                    if (!TreasuryManager.removeTreasury(draft.reward())) {
                        player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.treasury_missing")));
                        PlotzServerModeMenu.open(player);
                        return true;
                    }
                } else {
                    if (!BalanceManager.removeBalance(player.getUUID(), draft.reward())) {
                        player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.player_missing_money")));
                        PlotzJobsMenu.open(player, 0, true, false);
                        return true;
                    }
                }

                JobManager.createJob(
                    draft.serverJob() ? null : player.getUUID(),
                    draft.serverJob() ? LanguageManager.tr("common.treasury") : player.getGameProfile().getName(),
                    draft.title(),
                    draft.description(),
                    draft.reward(),
                    dueDays,
                    draft.serverJob()
                );

                player.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.input.created")));
                if (draft.serverJob()) {
                    PlotzServerModeMenu.open(player);
                } else {
                    PlotzJobsMenu.open(player, 0, true, false);
                }
                return true;
            }
        }

        return false;
    }
}