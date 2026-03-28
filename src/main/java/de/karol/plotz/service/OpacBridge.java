package de.karol.plotz.service;

import de.karol.plotz.data.PlotzStore;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.claims.tracker.api.IClaimsManagerListenerAPI;
import xaero.pac.common.event.api.OPACServerAddonRegisterEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class OpacBridge {
    private static final Map<String, UUID> CLAIM_OWNER_CACHE = new ConcurrentHashMap<>();
    private static MinecraftServer server;

    private OpacBridge() {}

    public static boolean isInstalled() {
        return ModList.get().isLoaded("openpartiesandclaims");
    }

    public static String getPartyStatusText(ServerPlayer player) {
        return isInstalled() ? "§aOPAC connected" : "§cOPAC not installed";
    }

    public static void registerClaimsTracker(OPACServerAddonRegisterEvent event) {
        server = event.getServer();

        event.getClaimsManagerTrackerAPI().register(new IClaimsManagerListenerAPI() {
            @Override
            public void onWholeRegionChange(ResourceLocation dimension, int regionX, int regionZ) {
            }

            @Override
            public void onChunkChange(ResourceLocation dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
                handleChunkChange(dimension, chunkX, chunkZ, claim);
            }

            @Override
            public void onDimensionChange(ResourceLocation dimension) {
            }
        });
    }

    private static void handleChunkChange(ResourceLocation dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
        if (server == null) {
            return;
        }

        String key = dimension + "|" + chunkX + "|" + chunkZ;
        String location = formatLocation(dimension, chunkX, chunkZ);
        UUID previousOwner = CLAIM_OWNER_CACHE.get(key);

        if (claim == null) {
            CLAIM_OWNER_CACHE.remove(key);
            PlotzStore.removeOwnedPlotByLocation(location);
            return;
        }

        UUID newOwner = claim.getPlayerId();
        if (newOwner == null) {
            return;
        }

        if (newOwner.equals(previousOwner)) {
            return;
        }

        CLAIM_OWNER_CACHE.put(key, newOwner);

        ServerPlayer player = server.getPlayerList().getPlayer(newOwner);
        if (player == null) {
            return;
        }

        boolean capital = PlotzLogic.isCapital(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8));

        if (previousOwner == null) {
            boolean spent = capital
                ? PlotzStore.spendCapitalCredit(newOwner)
                : PlotzStore.spendNormalCredit(newOwner);

            if (!spent) {
                player.sendSystemMessage(Component.literal(
                    capital
                        ? "§cYou need a capital claim credit for this claim."
                        : "§cYou need a normal claim credit for this claim."
                ));
                // Rückgängig machen bauen wir als nächsten Schritt sauberer aus
                return;
            }

            player.sendSystemMessage(Component.literal(
                capital
                    ? "§aCapital claim detected. 1 capital credit was used."
                    : "§aClaim detected. 1 normal credit was used."
            ));
        }

        PlotzStore.upsertOwnedClaim(
            newOwner,
            player.getGameProfile().getName(),
            capital,
            location
        );
    }

    private static String formatLocation(ResourceLocation dimension, int chunkX, int chunkZ) {
        return dimension + " | Chunk " + chunkX + ", " + chunkZ;
    }
}