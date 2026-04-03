package de.karl_der_iii.economymc.service;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ScoreboardManager {
    private static final String OBJECTIVE = "economymc_balance";

    private ScoreboardManager() {
    }

    public static void update(MinecraftServer server) {
        try {
            CommandSourceStack source = server.createCommandSourceStack()
                .withSuppressedOutput()
                .withPermission(4);

            String title = escape(LanguageManager.tr("menu.player.balance").replaceAll("§.", "").replace(": $%d", "").replace(": $", "").trim());
            if (title.isBlank()) {
                title = "Balance";
            }

            String treasuryLabel = sanitize(LanguageManager.tr("history.treasury").replaceAll("§.", "").trim());
            if (treasuryLabel.isBlank()) {
                treasuryLabel = "Treasury";
            }

            server.getCommands().performPrefixedCommand(source, "scoreboard objectives remove " + OBJECTIVE);
            server.getCommands().performPrefixedCommand(source, "scoreboard objectives add " + OBJECTIVE + " dummy \"" + title + "\"");
            server.getCommands().performPrefixedCommand(source, "scoreboard objectives setdisplay sidebar " + OBJECTIVE);

            List<Map.Entry<UUID, Long>> entries = new ArrayList<>(BalanceManager.getAllBalances().entrySet());
            entries.removeIf(e -> BalanceManager.TREASURY_ACCOUNT_ID.equals(e.getKey()));
            entries.sort(Map.Entry.<UUID, Long>comparingByValue(Comparator.reverseOrder()));

            int count = 0;
            for (Map.Entry<UUID, Long> entry : entries) {
                if (count >= 5) break;

                String name = sanitize(BalanceManager.resolveDisplayName(server, entry.getKey()));
                server.getCommands().performPrefixedCommand(
                    source,
                    "scoreboard players set " + name + " " + OBJECTIVE + " " + entry.getValue()
                );
                count++;
            }

            long treasury = TreasuryManager.getTreasury();
            server.getCommands().performPrefixedCommand(
                source,
                "scoreboard players set " + treasuryLabel + " " + OBJECTIVE + " " + treasury
            );
            server.getCommands().performPrefixedCommand(source, "scoreboard objectives setdisplay sidebar " + OBJECTIVE);
        } catch (Exception ignored) {
        }
    }

    private static String sanitize(String input) {
        String value = input.replaceAll("§.", "").replaceAll("[^\\p{L}\\p{N}_]", "_");
        if (value.isBlank()) {
            return "Entry";
        }
        return value;
    }

    private static String escape(String input) {
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}