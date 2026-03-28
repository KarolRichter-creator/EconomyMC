package de.karol.plotz.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OwnedPlotData {
    public static class PlotEntry {
        public final String plotId;
        public final UUID owner;
        public final String worldId;
        public final List<String> chunks;
        public final boolean capital;

        public PlotEntry(String plotId, UUID owner, String worldId, List<String> chunks, boolean capital) {
            this.plotId = plotId;
            this.owner = owner;
            this.worldId = worldId;
            this.chunks = chunks;
            this.capital = capital;
        }
    }

    private static final List<PlotEntry> PLOTS = new ArrayList<>();

    private OwnedPlotData() {}

    public static List<PlotEntry> getPlots() {
        return PLOTS;
    }

    public static void addPlot(PlotEntry entry) {
        PLOTS.add(entry);
    }
}
