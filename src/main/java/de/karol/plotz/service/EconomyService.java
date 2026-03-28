package de.karol.plotz.service;

import net.minecraft.server.level.ServerPlayer;

public class EconomyService {

    private EconomyService() {}

    public static boolean tryBuyNormalCredit(ServerPlayer player, int amount) {
        // TODO: EconomyCraft sauber anbinden:
        // laut Mod-Doku gibt es /eco removemoney bzw. /removemoney zum Geldabzug.
        return true;
    }

    public static boolean tryBuyCapitalCredit(ServerPlayer player, int amount) {
        // TODO: EconomyCraft sauber anbinden
        return true;
    }

    public static boolean tryBuyListing(ServerPlayer buyer, int amount) {
        // TODO: Käufer belasten, Verkäufer gutschreiben
        return true;
    }
}
