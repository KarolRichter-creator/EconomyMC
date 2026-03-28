package de.karol.plotz.service;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class EconomyBridge {
    private EconomyBridge() {}

    public static boolean removeMoney(ServerPlayer player, int amount) {
        try {
            MinecraftServer server = player.server;
            CommandSourceStack source = server.createCommandSourceStack()
                .withSuppressedOutput()
                .withPermission(4);

            String name = player.getGameProfile().getName();
            CommandDispatcher<CommandSourceStack> dispatcher = server.getCommands().getDispatcher();

            int result = dispatcher.execute("eco removemoney " + name + " " + amount, source);
            return result > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addMoney(MinecraftServer server, String playerName, int amount) {
        try {
            if (playerName == null || playerName.isBlank() || playerName.equals("System")) {
                return true;
            }

            CommandSourceStack source = server.createCommandSourceStack()
                .withSuppressedOutput()
                .withPermission(4);

            CommandDispatcher<CommandSourceStack> dispatcher = server.getCommands().getDispatcher();
            int result = dispatcher.execute("eco addmoney " + playerName + " " + amount, source);

            return result > 0;
        } catch (Exception ignored) {
            return false;
        }
    }
}