package de.karl_der_iii.economymc.service;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public final class LoanManager {
    public enum LoanTargetType {
        SERVER,
        ALL_PLAYERS,
        SPECIFIC_PLAYER
    }

    public enum LoanStatus {
        REQUESTED,
        OFFERED,
        ACTIVE,
        REPAID,
        CANCELLED,
        EXPIRED
    }

    public record LoanEntry(
        String id,
        UUID borrowerId,
        String borrowerName,
        LoanTargetType targetType,
        UUID targetPlayerId,
        String targetPlayerName,
        int principal,
        int days,
        int interestPercent,
        UUID lenderId,
        String lenderName,
        long createdAt,
        long acceptedAt,
        long dueAt,
        LoanStatus status
    ) {}

    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("economymc-loans.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private LoanManager() {}

    private static void ensureLoaded() {
        if (loaded) return;
        loaded = true;

        if (!Files.exists(FILE)) {
            PROPS.setProperty("nextId", "1");
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
                PROPS.store(out, "EconomyMC loans");
            }
        } catch (IOException ignored) {
        }
    }

    private static String nextId() {
        ensureLoaded();
        int next = Integer.parseInt(PROPS.getProperty("nextId", "1"));
        PROPS.setProperty("nextId", Integer.toString(next + 1));
        save();
        return Integer.toString(next);
    }

    public static LoanEntry createRequest(UUID borrowerId, String borrowerName, LoanTargetType targetType, UUID targetPlayerId, String targetPlayerName, int principal, int days) {
        ensureLoaded();

        String id = nextId();
        long now = System.currentTimeMillis();
        String base = "loan." + id + ".";

        PROPS.setProperty(base + "borrowerId", borrowerId.toString());
        PROPS.setProperty(base + "borrowerName", safe(borrowerName));
        PROPS.setProperty(base + "targetType", targetType.name());
        PROPS.setProperty(base + "targetPlayerId", targetPlayerId == null ? "" : targetPlayerId.toString());
        PROPS.setProperty(base + "targetPlayerName", safe(targetPlayerName));
        PROPS.setProperty(base + "principal", Integer.toString(principal));
        PROPS.setProperty(base + "days", Integer.toString(days));
        PROPS.setProperty(base + "interestPercent", "0");
        PROPS.setProperty(base + "lenderId", "");
        PROPS.setProperty(base + "lenderName", "");
        PROPS.setProperty(base + "createdAt", Long.toString(now));
        PROPS.setProperty(base + "acceptedAt", "0");
        PROPS.setProperty(base + "dueAt", "0");
        PROPS.setProperty(base + "status", LoanStatus.REQUESTED.name());
        save();

        TransactionHistoryManager.add(borrowerId, LanguageManager.format("history.loan.request", principal));
        return getLoan(id);
    }

    public static boolean makeOffer(String id, UUID lenderId, String lenderName, int interestPercent) {
        LoanEntry loan = getLoan(id);
        if (loan == null || loan.status() != LoanStatus.REQUESTED) {
            return false;
        }

        String base = "loan." + id + ".";
        PROPS.setProperty(base + "interestPercent", Integer.toString(Math.max(0, interestPercent)));
        PROPS.setProperty(base + "lenderId", lenderId == null ? "" : lenderId.toString());
        PROPS.setProperty(base + "lenderName", safe(lenderName));
        PROPS.setProperty(base + "status", LoanStatus.OFFERED.name());
        save();

        return true;
    }

    public static boolean acceptOffer(String id) {
        LoanEntry loan = getLoan(id);
        if (loan == null || loan.status() != LoanStatus.OFFERED) {
            return false;
        }

        long now = System.currentTimeMillis();
        long dueAt = now + loan.days() * 24L * 60L * 60L * 1000L;

        if (loan.lenderId() == null) {
            if (!TreasuryManager.removeTreasury(loan.principal())) {
                return false;
            }
            TransactionHistoryManager.addTreasury(LanguageManager.format("history.loan.funded", loan.principal()));
        } else {
            if (!BalanceManager.removeBalance(loan.lenderId(), loan.principal())) {
                return false;
            }
            TransactionHistoryManager.add(loan.lenderId(), LanguageManager.format("history.loan.funded", loan.principal()));
        }

        BalanceManager.addBalance(loan.borrowerId(), loan.principal());
        TransactionHistoryManager.add(loan.borrowerId(), LanguageManager.format("history.loan.received", loan.principal()));

        String base = "loan." + id + ".";
        PROPS.setProperty(base + "acceptedAt", Long.toString(now));
        PROPS.setProperty(base + "dueAt", Long.toString(dueAt));
        PROPS.setProperty(base + "status", LoanStatus.ACTIVE.name());
        save();

        return true;
    }

    public static boolean repayLoan(String id, UUID payerId) {
        LoanEntry loan = getLoan(id);
        if (loan == null || loan.status() != LoanStatus.ACTIVE || !loan.borrowerId().equals(payerId)) {
            return false;
        }

        int total = getCurrentRepayAmount(loan);
        int interest = Math.max(0, total - loan.principal());

        if (!BalanceManager.removeBalance(payerId, total)) {
            return false;
        }

        if (loan.lenderId() == null) {
            TreasuryManager.addTreasury(total);
            TransactionHistoryManager.addTreasury(LanguageManager.format("history.loan.repaid", total));
        } else {
            BalanceManager.addBalance(loan.lenderId(), total);
            TransactionHistoryManager.add(loan.lenderId(), LanguageManager.format("history.loan.repaid", total));
        }

        TransactionHistoryManager.add(payerId, LanguageManager.format("history.loan.repaid", loan.principal()));
        if (interest > 0) {
            TransactionHistoryManager.add(payerId, LanguageManager.format("history.loan.interest", interest));
        }

        String base = "loan." + id + ".";
        PROPS.setProperty(base + "status", LoanStatus.REPAID.name());
        save();
        return true;
    }

    public static int getCurrentRepayAmount(LoanEntry loan) {
        int base = loan.principal();
        int interest = (int) Math.floor(base * (loan.interestPercent() / 100.0));

        if (loan.status() != LoanStatus.ACTIVE) {
            return base + interest;
        }

        long now = System.currentTimeMillis();
        if (now <= loan.dueAt()) {
            return base + interest;
        }

        long overdueDays = Math.max(1L, (now - loan.dueAt()) / (24L * 60L * 60L * 1000L));
        int overdueInterest = (int) Math.floor(base * (loan.interestPercent() / 100.0) * overdueDays);
        return base + interest + overdueInterest;
    }

    public static LoanEntry getLoan(String id) {
        ensureLoaded();
        String base = "loan." + id + ".";
        if (!PROPS.containsKey(base + "borrowerId")) {
            return null;
        }

        return new LoanEntry(
            id,
            UUID.fromString(PROPS.getProperty(base + "borrowerId")),
            PROPS.getProperty(base + "borrowerName", "Unknown"),
            LoanTargetType.valueOf(PROPS.getProperty(base + "targetType", LoanTargetType.ALL_PLAYERS.name())),
            parseUuid(PROPS.getProperty(base + "targetPlayerId", "")),
            PROPS.getProperty(base + "targetPlayerName", ""),
            Integer.parseInt(PROPS.getProperty(base + "principal", "0")),
            Integer.parseInt(PROPS.getProperty(base + "days", "1")),
            Integer.parseInt(PROPS.getProperty(base + "interestPercent", "0")),
            parseUuid(PROPS.getProperty(base + "lenderId", "")),
            PROPS.getProperty(base + "lenderName", ""),
            Long.parseLong(PROPS.getProperty(base + "createdAt", "0")),
            Long.parseLong(PROPS.getProperty(base + "acceptedAt", "0")),
            Long.parseLong(PROPS.getProperty(base + "dueAt", "0")),
            LoanStatus.valueOf(PROPS.getProperty(base + "status", LoanStatus.REQUESTED.name()))
        );
    }

    public static List<LoanEntry> getVisibleLoans(UUID viewerId, boolean adminView) {
        ensureLoaded();
        List<LoanEntry> result = new ArrayList<>();

        for (String key : PROPS.stringPropertyNames()) {
            if (!key.startsWith("loan.") || !key.endsWith(".borrowerId")) continue;

            String id = key.substring(5, key.length() - 11);
            LoanEntry entry = getLoan(id);
            if (entry == null) continue;

            if (adminView
                || entry.borrowerId().equals(viewerId)
                || (entry.lenderId() != null && entry.lenderId().equals(viewerId))
                || entry.targetType() == LoanTargetType.ALL_PLAYERS
                || (entry.targetType() == LoanTargetType.SPECIFIC_PLAYER && entry.targetPlayerId() != null && entry.targetPlayerId().equals(viewerId))
                || entry.targetType() == LoanTargetType.SERVER) {
                result.add(entry);
            }
        }

        result.sort(Comparator.comparingLong(LoanEntry::createdAt).reversed());
        return result;
    }

    private static UUID parseUuid(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String safe(String text) {
        return text == null ? "" : text.replace("\n", " ").replace("\r", " ");
    }
}