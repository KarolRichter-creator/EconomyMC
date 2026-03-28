package de.karol.plotz.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleDraftData {
    public static class SaleDraft {
        public final UUID owner;
        public final String name;
        public final int price;
        public final String description;
        public final String locationText;
        public final String justification;
        public final String builtOnPlot;
        public final List<String> chunks;

        public SaleDraft(UUID owner, String name, int price, String description, String locationText,
                         String justification, String builtOnPlot, List<String> chunks) {
            this.owner = owner;
            this.name = name;
            this.price = price;
            this.description = description;
            this.locationText = locationText;
            this.justification = justification;
            this.builtOnPlot = builtOnPlot;
            this.chunks = chunks;
        }
    }

    private static final List<SaleDraft> DRAFTS = new ArrayList<>();

    private SaleDraftData() {}

    public static List<SaleDraft> getDrafts() {
        return DRAFTS;
    }

    public static void addDraft(SaleDraft draft) {
        DRAFTS.add(draft);
    }
}
