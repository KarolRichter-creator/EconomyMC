package de.karol.plotz.service;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class TreasuryManager {
    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("plotz-treasury.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private TreasuryManager() {}

    private static void ensureLoaded() {
        if (loaded) return;
        loaded = true;

        if (!Files.exists(FILE)) {
            PROPS.setProperty("treasury", "0");
            PROPS.setProperty("taxPercent", "4");
            save();
            return;
        }

        try (InputStream in = Files.newInputStream(FILE)) {
            PROPS.load(in);
        } catch (IOException ignored) {
        }
    }

    private static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) {
                PROPS.store(out, "Plotz treasury config");
            }
        } catch (IOException ignored) {
        }
    }

    public static long getTreasury() {
        ensureLoaded();
        try {
            return Long.parseLong(PROPS.getProperty("treasury", "0"));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static void setTreasury(long amount) {
        ensureLoaded();
        PROPS.setProperty("treasury", Long.toString(Math.max(0L, amount)));
        save();
    }

    public static void addTreasury(long amount) {
        setTreasury(getTreasury() + amount);
    }

    public static boolean removeTreasury(long amount) {
        long current = getTreasury();
        if (current < amount) {
            return false;
        }
        setTreasury(current - amount);
        return true;
    }

    public static int getTaxPercent() {
        ensureLoaded();
        try {
            return Integer.parseInt(PROPS.getProperty("taxPercent", "4"));
        } catch (NumberFormatException e) {
            return 4;
        }
    }

    public static void setTaxPercent(int percent) {
        ensureLoaded();
        int clamped = Math.max(0, Math.min(100, percent));
        PROPS.setProperty("taxPercent", Integer.toString(clamped));
        save();
    }

    public static int calculateTax(int amount) {
        return (int) Math.floor(amount * (getTaxPercent() / 100.0));
    }
}