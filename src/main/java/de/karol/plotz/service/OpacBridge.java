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
import xaero.pac.common.server.api.OpenPACServerAPI;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class OpacBridge {
    private static final Map<String, UUID> CLAIM_OWNER_CACHE = new ConcurrentHashMap<>();
    private static MinecraftServer server;

    private OpacBridge() {}

    public static boolean isInstalled() {
        return ModList.get().isLoaded("openpartiesandclaims");
    }

    public static boolean isPlayerInParty(ServerPlayer player) {
        if (!isInstalled()) {
            return false;
        }

        try {
            return OpenPACServerAPI.get(player.server)
                .getPartyManager()
                .getPartyByMember(player.getUUID()) != null;
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

    public static void registerClaimsTracker(OPACServerAddonRegisterEvent event) {
        server = event.getServer();

        event.getClaimsManagerTrackerAPI().register(new IClaimsManagerListenerAPI() {
            @Override
            public void onWholeRegionChange(ResourceLocation dimension, int regionX, int regionZ) {
                // not needed for first version
            }

            @Override
            public void onChunkChange(ResourceLocation dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
                handleChunkChange(dimension, chunkX, chunkZ, claim);
            }

            @Override
            public void onDimensionChange(ResourceLocation dimension) {
                // not needed for first version
            }
        });
    }

    private static void handleChunkChange(ResourceLocation dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
        if (server == null) {
            return;
        }

        String key = dimension + "|" + chunkX + "|" + chunkZ;
        UUID previousOwner = CLAIM_OWNER_CACHE.get(key);

        if (claim == null) {
            CLAIM_OWNER_CACHE.remove(key);
            PlotzStore.removeOwnedPlotByLocation(formatLocation(dimension, chunkX, chunkZ));
            return;
        }

        UUID newOwner = claim.getPlayerId();
        if (newOwner == null) {
            // best-effort: likely not a normal player claim
            return;
        }

        if (Objects.equals(previousOwner, newOwner)) {
            return;
        }

        CLAIM_OWNER_CACHE.put(key, newOwner);

        // only charge on a fresh claim, not on cache refresh / ownership repeat
        if (previousOwner != null) {
            return;
        }

        ServerPlayer player = server.getPlayerList().getPlayer(newOwner);
        if (player == null) {
            return;
        }

        boolean capital = PlotzLogic.isCapital(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8));
        boolean spent = capital
            ? PlotzStore.spendCapitalCredit(newOwner)
            : PlotzStore.spendNormalCredit(newOwner);

        if (!spent) {
            try {
                OpenPACServerAPI.get(server).getServerClaimsManager().unclaim(dimension, chunkX, chunkZ);
                CLAIM_OWNER_CACHE.remove(key);
                player.sendSystemMessage(Component.literal(
                    capital
                        ? "§cClaim reverted: you do not have a capital claim credit."
                        : "§cClaim reverted: you do not have a normal claim credit."
                ));
            } catch (Exception e) {
                player.sendSystemMessage(Component.literal("§cClaim failed and could not be synced correctly."));
            }
            return;
        }

        PlotzStore.upsertOwnedClaim(
            newOwner,
            player.getGameProfile().getName(),
            capital,
            formatLocation(dimension, chunkX, chunkZ)
        );

        player.sendSystemMessage(Component.literal(
            capital
                ? "§aCapital claim synced. 1 capital credit was used."
                : "§aClaim synced. 1 normal credit was used."
        ));
    }

    private static String formatLocation(ResourceLocation dimension, int chunkX, int chunkZ) {
        return dimension + " | Chunk " + chunkX + ", " + chunkZ;
    }
}