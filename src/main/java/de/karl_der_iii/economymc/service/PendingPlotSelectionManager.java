package de.karl_der_iii.economymc.service;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PendingPlotSelectionManager {
    public enum ActionType {
        BUY,
        SALE_DRAFT
    }

    public record PendingSelection(
        ActionType actionType,
        ResourceLocation dimension,
        int minChunkX,
        int minChunkZ,
        int maxChunkX,
        int maxChunkZ,
        int chunkCount,
        boolean capital,
        int totalPrice
    ) {}

    private static final Map<UUID, PendingSelection> PENDING = new ConcurrentHashMap<>();

    private PendingPlotSelectionManager() {
    }

    public static void set(ServerPlayer player, PendingSelection selection) {
        PENDING.put(player.getUUID(), selection);
    }

    public static PendingSelection get(ServerPlayer player) {
        return PENDING.get(player.getUUID());
    }

    public static PendingSelection remove(ServerPlayer player) {
        return PENDING.remove(player.getUUID());
    }

    public static boolean has(ServerPlayer player) {
        return PENDING.containsKey(player.getUUID());
    }

    public static String locationString(PendingSelection s) {
        return s.dimension() + " | Chunks " + s.minChunkX() + "," + s.minChunkZ() + " -> " + s.maxChunkX() + "," + s.maxChunkZ();
    }
}