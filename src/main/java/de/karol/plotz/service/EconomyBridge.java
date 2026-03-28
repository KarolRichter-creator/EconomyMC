package de.karol.plotz.service;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.Method;
import java.util.UUID;

public final class EconomyBridge {
    private EconomyBridge() {}

    private static Object getManager(MinecraftServer server) throws Exception {
        Class<?> economyCraftClass = Class.forName("com.reazip.economycraft.EconomyCraft");
        Method getManagerMethod = economyCraftClass.getMethod("getManager", MinecraftServer.class);
        return getManagerMethod.invoke(null, server);
    }

    public static long getBalance(ServerPlayer player) {
        try {
            Object manager = getManager(player.server);
            if (manager == null) {
                return 0L;
            }

            Class<?> managerClass = manager.getClass();

            try {
                Method getBalance = managerClass.getMethod("getBalance", UUID.class, boolean.class);
                Object result = getBalance.invoke(manager, player.getUUID(), true);
                return result instanceof Long l ? l : 0L;
            } catch (NoSuchMethodException ignored) {
            }

            try {
                Method getBalance = managerClass.getMethod("getBalance", UUID.class);
                Object result = getBalance.invoke(manager, player.getUUID());
                return result instanceof Long l ? l : 0L;
            } catch (NoSuchMethodException ignored) {
            }

            return 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static boolean hasEnough(ServerPlayer player, long amount) {
        return getBalance(player) >= amount;
    }

    public static boolean removeMoney(ServerPlayer player, int amount) {
        try {
            Object manager = getManager(player.server);
            if (manager == null) {
                return false;
            }

            if (!hasEnough(player, amount)) {
                return false;
            }

            Class<?> managerClass = manager.getClass();

            try {
                Method removeMoney = managerClass.getMethod("removeMoney", UUID.class, long.class);
                Object result = removeMoney.invoke(manager, player.getUUID(), (long) amount);
                return result instanceof Boolean b && b;
            } catch (NoSuchMethodException ignored) {
            }

            try {
                Method removeMoney = managerClass.getMethod("removeMoney", UUID.class, int.class);
                Object result = removeMoney.invoke(manager, player.getUUID(), amount);
                return result instanceof Boolean b && b;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addMoney(MinecraftServer server, String playerName, int amount) {
        try {
            if (playerName == null || playerName.isBlank() || playerName.equals("System")) {
                return true;
            }

            Object manager = getManager(server);
            if (manager == null) {
                return false;
            }

            Class<?> managerClass = manager.getClass();
            UUID sellerId = null;

            try {
                Method tryResolveUuidByName = managerClass.getMethod("tryResolveUuidByName", String.class);
                Object resolved = tryResolveUuidByName.invoke(manager, playerName);
                if (resolved instanceof UUID uuid) {
                    sellerId = uuid;
                }
            } catch (NoSuchMethodException ignored) {
            }

            if (sellerId == null) {
                for (ServerPlayer online : server.getPlayerList().getPlayers()) {
                    if (online.getGameProfile().getName().equalsIgnoreCase(playerName)) {
                        sellerId = online.getUUID();
                        break;
                    }
                }
            }

            if (sellerId == null) {
                return false;
            }

            try {
                Method addMoney = managerClass.getMethod("addMoney", UUID.class, long.class);
                addMoney.invoke(manager, sellerId, (long) amount);
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            try {
                Method addMoney = managerClass.getMethod("addMoney", UUID.class, int.class);
                addMoney.invoke(manager, sellerId, amount);
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}