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

    private ScoreboardManager() {}

    public static void update(MinecraftServer server) {
        try {
            CommandSourceStack source = server.createCommandSourceStack()
                .withSuppressedOutput()
                .withPermission(4);

            server.getCommands().performPrefixedCommand(source, "scoreboard objectives remove " + OBJECTIVE);

            if (!AdminSettingsManager.scoreboardEnabled()) {
                return;
            }

            server.getCommands().performPrefixedCommand(
                source,
                "scoreboard objectives add " + OBJECTIVE + " dummy \"" + sanitize(LanguageManager.tr("scoreboard.balance")) + "\""
            );
            server.getCommands().performPrefixedCommand(source, "scoreboard objectives setdisplay sidebar " + OBJECTIVE);

            clearTeams(source, server);

            List<Map.Entry<UUID, Long>> entries = new ArrayList<>(BalanceManager.getAllBalances().entrySet());
            entries.removeIf(e -> BalanceManager.TREASURY_ACCOUNT_ID.equals(e.getKey()));
            entries.sort(Map.Entry.<UUID, Long>comparingByValue(Comparator.reverseOrder()));

            int score = 15;
            int count = 0;
            for (Map.Entry<UUID, Long> entry : entries) {
                if (count >= 5) break;

                String team = "ec_line_" + count;
                String fake = "§" + Integer.toHexString(count);
                String name = BalanceManager.resolveDisplayName(server, entry.getKey());
                String prefix = trim(LanguageManager.tr("scoreboard.balance") + ": " + name + " ");
                String suffix = trim("$" + entry.getValue());

                server.getCommands().performPrefixedCommand(source, "scoreboard teams add " + team);
                server.getCommands().performPrefixedCommand(source, "scoreboard teams modify " + team + " prefix \"" + sanitize(prefix) + "\"");
                server.getCommands().performPrefixedCommand(source, "scoreboard teams modify " + team + " suffix \"" + sanitize(suffix) + "\"");
                server.getCommands().performPrefixedCommand(source, "scoreboard teams join " + team + " " + quote(fake));
                server.getCommands().performPrefixedCommand(source, "scoreboard players set " + quote(fake) + " " + OBJECTIVE + " " + score);

                score--;
                count++;
            }

            String treasuryTeam = "ec_treasury";
            String treasuryFake = "§a";
            String treasuryPrefix = trim(LanguageManager.tr("scoreboard.treasury") + ": ");
            String treasurySuffix = trim("$" + TreasuryManager.getTreasury());

            server.getCommands().performPrefixedCommand(source, "scoreboard teams add " + treasuryTeam);
            server.getCommands().performPrefixedCommand(source, "scoreboard teams modify " + treasuryTeam + " prefix \"" + sanitize(treasuryPrefix) + "\"");
            server.getCommands().performPrefixedCommand(source, "scoreboard teams modify " + treasuryTeam + " suffix \"" + sanitize(treasurySuffix) + "\"");
            server.getCommands().performPrefixedCommand(source, "scoreboard teams join " + treasuryTeam + " " + quote(treasuryFake));
            server.getCommands().performPrefixedCommand(source, "scoreboard players set " + quote(treasuryFake) + " " + OBJECTIVE + " " + (score - 1));
        } catch (Exception ignored) {
        }
    }

    private static void clearTeams(CommandSourceStack source, MinecraftServer server) {
        for (int i = 0; i < 5; i++) {
            server.getCommands().performPrefixedCommand(source, "scoreboard teams remove ec_line_" + i);
        }
        server.getCommands().performPrefixedCommand(source, "scoreboard teams remove ec_treasury");
    }

    private static String trim(String s) {
        return s.length() > 32 ? s.substring(0, 32) : s;
    }

    private static String sanitize(String input) {
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String quote(String input) {
        return "\"" + sanitize(input) + "\"";
    }
}
