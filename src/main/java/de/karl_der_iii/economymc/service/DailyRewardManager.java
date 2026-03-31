package de.karl_der_iii.economymc.service;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;
import java.util.UUID;

public final class DailyRewardManager {
    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("economymc-daily.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private DailyRewardManager() {}

    private static void ensureLoaded() {
        if (loaded) return;
        loaded = true;

        if (!Files.exists(FILE)) return;

        try (InputStream in = Files.newInputStream(FILE)) {
            PROPS.load(in);
        } catch (IOException ignored) {
        }
    }

    private static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) {
                PROPS.store(out, "EconomyMC daily rewards");
            }
        } catch (IOException ignored) {
        }
    }

    public static boolean canClaim(UUID playerId) {
        ensureLoaded();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate last = getLastClaimDate(playerId);
        return last == null || !last.equals(today);
    }

    public static long getRemainingMs(UUID playerId) {
        ensureLoaded();
        if (canClaim(playerId)) {
            return 0L;
        }

        LocalDate tomorrow = LocalDate.now(ZoneId.systemDefault()).plusDays(1);
        long next = tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Math.max(0L, next - System.currentTimeMillis());
    }

    public static void markClaimed(UUID playerId) {
        ensureLoaded();
        PROPS.setProperty(playerId.toString(), LocalDate.now(ZoneId.systemDefault()).toString());
        save();
    }

    private static LocalDate getLastClaimDate(UUID playerId) {
        ensureLoaded();
        String value = PROPS.getProperty(playerId.toString(), "");
        if (value.isBlank()) return null;

        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            return null;
        }
    }
}