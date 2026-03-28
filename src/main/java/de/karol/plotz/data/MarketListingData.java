package de.karol.plotz.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MarketListingData {
    public static class Listing {
        public final UUID seller;
        public final String title;
        public final int price;
        public final String description;
        public final String locationText;
        public final String justification;
        public final String builtOnPlot;
        public final int chunkCount;

        public Listing(UUID seller, String title, int price, String description, String locationText,
                       String justification, String builtOnPlot, int chunkCount) {
            this.seller = seller;
            this.title = title;
            this.price = price;
            this.description = description;
            this.locationText = locationText;
            this.justification = justification;
            this.builtOnPlot = builtOnPlot;
            this.chunkCount = chunkCount;
        }
    }

    private static final List<Listing> LISTINGS = new ArrayList<>();

    private MarketListingData() {}

    public static List<Listing> getListings() {
        return LISTINGS;
    }

    public static void addListing(Listing listing) {
        LISTINGS.add(listing);
    }

    static {
        LISTINGS.add(new Listing(
            UUID.randomUUID(),
            "Beispiel-Grundstück am Fluss",
            6500,
            "Großes Grundstück mit guter Lage.",
            "Nahe Hauptstadt",
            "Nähe zur Stadt und gute Erreichbarkeit",
            "Haus, Lager und kleiner Turm",
            6
        ));
    }
}
