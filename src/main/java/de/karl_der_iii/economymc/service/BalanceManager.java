package de.karl_der_iii.economymc.service;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public final class BalanceManager {
    public static final UUID TREASURY_ACCOUNT_ID = UUID.fromString("00000000-0000-0000-0000-000000000999");

    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("economymc-balances.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private BalanceManager() {}

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
                PROPS.store(out, "EconomyMC balances");
            }
        } catch (IOException ignored) {
        }
    }

    public static long getBalance(UUID accountId) {
        ensureLoaded();
        String value = PROPS.getProperty(accountId.toString(), "0");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static void setBalance(UUID accountId, long amount) {
        ensureLoaded();
        PROPS.setProperty(accountId.toString(), Long.toString(amount));
        save();
    }

    public static void addBalance(UUID accountId, long amount) {
        setBalance(accountId, getBalance(accountId) + amount);
    }

    public static boolean removeBalance(UUID accountId, long amount) {
        long current = getBalance(accountId);
        if (current < amount) return false;
        setBalance(accountId, current - amount);
        return true;
    }

    public static void addBalanceAllowNegative(UUID accountId, long amount) {
        setBalance(accountId, getBalance(accountId) + amount);
    }

    public static Optional<UUID> resolveKnownPlayer(MinecraftServer server, String name) {
        ensureLoaded();

        for (UUID id : getAllBalances().keySet()) {
            if (TREASURY_ACCOUNT_ID.equals(id)) continue;
            String display = resolveDisplayName(server, id);
            if (display.equalsIgnoreCase(name)) {
                return Optional.of(id);
            }
        }

        return Optional.empty();
    }

    public static Optional<UUID> resolveKnownAccount(MinecraftServer server, String name) {
        if (name.equalsIgnoreCase("treasury") || name.equalsIgnoreCase("server")) {
            setBalance(TREASURY_ACCOUNT_ID, getBalance(TREASURY_ACCOUNT_ID));
            return Optional.of(TREASURY_ACCOUNT_ID);
        }

        return resolveKnownPlayer(server, name);
    }

    public static List<String> getKnownAccountNames(MinecraftServer server) {
        ensureLoaded();

        List<String> result = new ArrayList<>();
        result.add("Treasury");

        for (UUID id : getAllBalances().keySet()) {
            if (TREASURY_ACCOUNT_ID.equals(id)) continue;
            String display = resolveDisplayName(server, id);
            if (!result.contains(display)) {
                result.add(display);
            }
        }

        return result;
    }

    public static String resolveDisplayName(MinecraftServer server, UUID uuid) {
        if (TREASURY_ACCOUNT_ID.equals(uuid)) {
            return "Treasury";
        }

        if (server.getProfileCache() != null) {
            Optional<GameProfile> cached = server.getProfileCache().get(uuid);
            if (cached.isPresent()) {
                return cached.get().getName();
            }
        }

        return uuid.toString().substring(0, 8);
    }

    public static Map<UUID, Long> getAllBalances() {
        ensureLoaded();
        Map<UUID, Long> result = new LinkedHashMap<>();

        for (String key : PROPS.stringPropertyNames()) {
            try {
                UUID id = UUID.fromString(key);
                result.put(id, getBalance(id));
            } catch (IllegalArgumentException ignored) {
            }
        }

        return result;
    }
}