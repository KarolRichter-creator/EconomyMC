package de.karol.plotz.service;

import net.minecraft.server.level.ServerPlayer;

public class ClaimValidationService {

    private ClaimValidationService() {}

    public static boolean isServerClaimBlocked(ServerPlayer player) {
        // TODO: OPAC-Server-Claims per API prüfen und hier true/false zurückgeben
        return false;
    }

    public static boolean isCapitalChunk(ServerPlayer player) {
        // TODO: über Zonen prüfen
        return false;
    }

    public static boolean isMinDistanceValid(ServerPlayer player) {
        // TODO: 700-Block-Regel hier prüfen
        return true;
    }
}
