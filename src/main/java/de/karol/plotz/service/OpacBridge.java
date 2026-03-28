package de.karol.plotz.service;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

import java.lang.reflect.Method;
import java.util.UUID;

public final class OpacBridge {
    private OpacBridge() {}

    public static boolean isInstalled() {
        return ModList.get().isLoaded("openpartiesandclaims");
    }

    public static boolean isPlayerInParty(ServerPlayer player) {
        if (!isInstalled()) {
            return false;
        }

        try {
            MinecraftServer server = player.server;
            UUID playerId = player.getUUID();

            Class<?> apiClass = Class.forName("xaero.pac.common.server.api.OpenPACServerAPI");
            Method getMethod = apiClass.getMethod("get", MinecraftServer.class);
            Object apiInstance = getMethod.invoke(null, server);

            Method getPartyManagerMethod = apiInstance.getClass().getMethod("getPartyManager");
            Object partyManager = getPartyManagerMethod.invoke(apiInstance);

            Method getPartyByMemberMethod = partyManager.getClass().getMethod("getPartyByMember", UUID.class);
            Object party = getPartyByMemberMethod.invoke(partyManager, playerId);

            return party != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getPartyStatusText(ServerPlayer player) {
        if (!isInstalled()) {
            return "§cOPAC not installed";
        }

        return isPlayerInParty(player)
            ? "§aRW / Party found"
            : "§cNo RW / Party found";
    }
}