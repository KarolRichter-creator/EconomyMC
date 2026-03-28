package de.karol.plotz.service;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public final class PlotzLogic {
    public static final int NORMAL_CHUNK_PRICE = 250;
    public static final int CAPITAL_CHUNK_PRICE = 500;
    public static final int MIN_DISTANCE_BLOCKS = 700;

    private PlotzLogic() {}

    public static boolean isCapital(BlockPos pos) {
        return CapitalAreaManager.isInside(pos);
    }

    public static boolean tryCharge(ServerPlayer player, int amount) {
        if (!EconomyBridge.hasEnough(player, amount)) {
            return false;
        }
        return EconomyBridge.removeMoney(player, amount);
    }

    public static boolean paySeller(ServerPlayer buyerContext, String sellerName, int amount) {
        return EconomyBridge.addMoney(buyerContext.server, sellerName, amount);
    }

    public static boolean isServerClaimBlocked(ServerPlayer player) {
        return false;
    }

    public static boolean isMinDistanceValid(ServerPlayer player) {
        return true;
    }

    public static boolean canBuyNormalCredit(ServerPlayer player) {
        return tryCharge(player, NORMAL_CHUNK_PRICE);
    }

    public static boolean canBuyCapitalCredit(ServerPlayer player) {
        return tryCharge(player, CAPITAL_CHUNK_PRICE);
    }
}