package de.karol.plotz.menu;

import de.karol.plotz.config.PlotzConfig;
import de.karol.plotz.data.MarketListingData;
import de.karol.plotz.data.OwnedPlotData;
import de.karol.plotz.data.PlayerCreditsData;
import de.karol.plotz.data.SaleDraftData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class PlotzMenuService {

    private PlotzMenuService() {}

    public static int openMainOverview(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Nur Spieler können /plotz benutzen."));
            return 0;
        }

        source.sendSuccess(() -> Component.literal("§6======= [ Plotz ] ======="), false);
        source.sendSuccess(() -> Component.literal("§eNormale Claim-Chunks kaufen"), false);
        source.sendSuccess(() -> Component.literal(" §7Preis: " + PlotzConfig.NORMAL_CHUNK_PRICE + "$"), false);
        source.sendSuccess(() -> Component.literal(" §7Verfügbar: " + PlayerCreditsData.getNormalCredits(player.getUUID())), false);

        source.sendSuccess(() -> Component.literal("§eHauptstadt-Claim-Chunks kaufen"), false);
        source.sendSuccess(() -> Component.literal(" §7Preis: " + PlotzConfig.CAPITAL_CHUNK_PRICE + "$"), false);
        source.sendSuccess(() -> Component.literal(" §7Verfügbar: " + PlayerCreditsData.getCapitalCredits(player.getUUID())), false);

        source.sendSuccess(() -> Component.literal("§bEigene Grundstücke: " + OwnedPlotData.getPlots().size()), false);
        source.sendSuccess(() -> Component.literal("§bVerkaufsentwürfe: " + SaleDraftData.getDrafts().size()), false);
        source.sendSuccess(() -> Component.literal("§bMarktangebote: " + MarketListingData.getListings().size()), false);

        source.sendSuccess(() -> Component.literal("§7Geplante Menüpunkte:"), false);
        source.sendSuccess(() -> Component.literal(" §8- Guthaben kaufen"), false);
        source.sendSuccess(() -> Component.literal(" §8- Markt ansehen"), false);
        source.sendSuccess(() -> Component.literal(" §8- Eigene Verkäufe"), false);
        source.sendSuccess(() -> Component.literal(" §8- Besitz"), false);
        source.sendSuccess(() -> Component.literal(" §8- OPAC Server-Claims automatisch sperren"), false);

        source.sendSuccess(() -> Component.literal("§6========================"), false);
        return 1;
    }
}
