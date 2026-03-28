package de.karol.plotz.service;

import de.karol.plotz.data.PlotzStore;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.fml.ModList;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.claims.player.api.IPlayerClaimPosListAPI;
import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;
import xaero.pac.common.claims.tracker.api.IClaimsManagerListenerAPI;
import xaero.pac.common.event.api.OPACServerAddonRegisterEvent;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.claims.player.api.IServerPlayerClaimInfoAPI;

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

    public static void syncOwnedClaims(ServerPlayer player) {
        if (!isInstalled()) {
            return;
        }

        try {
            PlotzStore.clearOwnedClaimsFor(player.getUUID());

            IServerPlayerClaimInfoAPI info = OpenPACServerAPI.get(player.server)
                .getServerClaimsManager()
                .getPlayerInfo(player.getUUID());

            if (info == null) {
                return;
            }

            info.getStream().forEach(entry -> {
                ResourceLocation dimension = entry.getKey();
                IPlayerDimensionClaimsAPI dimClaims = entry.getValue();

                dimClaims.getStream().forEach((IPlayerClaimPosListAPI posList) -> {
                    posList.getStream().forEach((ChunkPos chunkPos) -> {
                        boolean capital = PlotzLogic.isCapital(new BlockPos(chunkPos.x * 16 + 8, 64, chunkPos.z * 16 + 8));
                        PlotzStore.upsertOwnedClaim(
                            player.getUUID(),
                            player.getGameProfile().getName(),
                            capital,
                            formatLocation(dimension, chunkPos.x, chunkPos.z)
                        );
                    });
                });
            });
        } catch (Exception ignored) {
        }
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
                try {
                    OpenPACServerAPI.get(server).getServerClaimsManager().unclaim(dimension, chunkX, chunkZ);
                    CLAIM_OWNER_CACHE.remove(key);
                    PlotzStore.removeOwnedPlotByLocation(location);
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        capital
                            ? "§cClaim reverted: you need a capital claim credit."
                            : "§cClaim reverted: you need a normal claim credit."
                    ));
                } catch (Exception e) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§cClaim detected without credits, but automatic rollback failed."
                    ));
                }
                return;
            }

            player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
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