package de.karol.plotz.service;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public final class BalanceManager {
    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("plotz-balances.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private BalanceManager() {}

    private static void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;

        if (!Files.exists(FILE)) {
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
                PROPS.store(out, "Plotz balances");
            }
        } catch (IOException ignored) {
        }
    }

    public static long getBalance(UUID playerId) {
        ensureLoaded();
        String value = PROPS.getProperty(playerId.toString(), "0");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static void setBalance(UUID playerId, long amount) {
        ensureLoaded();
        PROPS.setProperty(playerId.toString(), Long.toString(Math.max(0L, amount)));
        save();
    }

    public static void addBalance(UUID playerId, long amount) {
        setBalance(playerId, getBalance(playerId) + amount);
    }

    public static boolean removeBalance(UUID playerId, long amount) {
        long current = getBalance(playerId);
        if (current < amount) {
            return false;
        }

        setBalance(playerId, current - amount);
        return true;
    }

    public static Optional<UUID> resolveKnownPlayer(MinecraftServer server, String name) {
        GameProfileCache cache = server.getProfileCache();
        if (cache == null) {
            return Optional.empty();
        }

        Optional<GameProfile> profile = cache.get(name);
        profile.ifPresent(p -> setBalance(p.getId(), getBalance(p.getId())));
        return profile.map(GameProfile::getId);
    }
}