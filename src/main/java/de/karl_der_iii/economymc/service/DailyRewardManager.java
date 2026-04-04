package de.karl_der_iii.economymc.service;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    public static int getStreak(UUID playerId) {
        ensureLoaded();
        return Math.max(0, parseInt(playerId + ".streak", 0));
    }

    public static int getCurrentReward(UUID playerId) {
        ensureLoaded();
        int base = AdminSettingsManager.dailyBaseReward();
        int percent = AdminSettingsManager.dailyIncreasePercent();
        int max = AdminSettingsManager.dailyMaxReward();
        int streak = getStreak(playerId);

        long reward = Math.round(base * (1.0 + ((long) streak * percent) / 100.0));
        return (int) Math.max(base, Math.min(max, reward));
    }

    public static void markClaimed(UUID playerId) {
        ensureLoaded();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate last = getLastClaimDate(playerId);
        int newStreak = 0;

        if (last != null) {
            long gap = ChronoUnit.DAYS.between(last, today);
            if (gap == 1) {
                newStreak = getStreak(playerId) + 1;
            } else if (gap == 0) {
                newStreak = getStreak(playerId);
            }
        }

        PROPS.setProperty(playerId.toString(), today.toString());
        PROPS.setProperty(playerId + ".streak", Integer.toString(Math.max(0, newStreak)));
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

    private static int parseInt(String key, int fallback) {
        try {
            return Integer.parseInt(PROPS.getProperty(key, Integer.toString(fallback)));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}