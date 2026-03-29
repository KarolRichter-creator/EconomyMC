package de.karol.plotz.service;

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

public final class JobManager {
    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("plotz-jobs.properties");
    private static final Properties PROPS = new Properties();
    private static boolean loaded = false;

    private JobManager() {}

    public record JobEntry(
        String id,
        UUID creatorId,
        String creatorName,
        String title,
        String description,
        int reward,
        String dueText,
        boolean serverJob,
        boolean open,
        String acceptedBy
    ) {}

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
                PROPS.store(out, "Plotz jobs");
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

    public static JobEntry createJob(UUID creatorId, String creatorName, String title, String description, int reward, String dueText, boolean serverJob) {
        ensureLoaded();
        String id = nextId();

        String base = "job." + id + ".";
        PROPS.setProperty(base + "creatorId", creatorId == null ? "" : creatorId.toString());
        PROPS.setProperty(base + "creatorName", safe(creatorName));
        PROPS.setProperty(base + "title", safe(title));
        PROPS.setProperty(base + "description", safe(description));
        PROPS.setProperty(base + "reward", Integer.toString(reward));
        PROPS.setProperty(base + "dueText", safe(dueText));
        PROPS.setProperty(base + "serverJob", Boolean.toString(serverJob));
        PROPS.setProperty(base + "open", "true");
        PROPS.setProperty(base + "acceptedBy", "");
        save();

        return getJob(id);
    }

    public static JobEntry getJob(String id) {
        ensureLoaded();
        String base = "job." + id + ".";
        if (!PROPS.containsKey(base + "title")) {
            return null;
        }

        String creatorIdRaw = PROPS.getProperty(base + "creatorId", "");
        UUID creatorId = creatorIdRaw.isBlank() ? null : UUID.fromString(creatorIdRaw);

        return new JobEntry(
            id,
            creatorId,
            PROPS.getProperty(base + "creatorName", "Unknown"),
            PROPS.getProperty(base + "title", "Untitled"),
            PROPS.getProperty(base + "description", ""),
            Integer.parseInt(PROPS.getProperty(base + "reward", "0")),
            PROPS.getProperty(base + "dueText", ""),
            Boolean.parseBoolean(PROPS.getProperty(base + "serverJob", "false")),
            Boolean.parseBoolean(PROPS.getProperty(base + "open", "true")),
            PROPS.getProperty(base + "acceptedBy", "")
        );
    }

    public static List<JobEntry> getOpenJobs() {
        ensureLoaded();
        List<JobEntry> result = new ArrayList<>();

        for (String key : PROPS.stringPropertyNames()) {
            if (key.startsWith("job.") && key.endsWith(".title")) {
                String id = key.substring(4, key.length() - 6);
                JobEntry entry = getJob(id);
                if (entry != null && entry.open()) {
                    result.add(entry);
                }
            }
        }

        result.sort(Comparator.comparingInt(a -> -Integer.parseInt(a.id())));
        return result;
    }

    public static boolean acceptJob(String id, String acceptedBy) {
        JobEntry job = getJob(id);
        if (job == null || !job.open()) {
            return false;
        }

        String base = "job." + id + ".";
        PROPS.setProperty(base + "open", "false");
        PROPS.setProperty(base + "acceptedBy", safe(acceptedBy));
        save();
        return true;
    }

    public static boolean withdrawJob(String id) {
        JobEntry job = getJob(id);
        if (job == null || !job.open()) {
            return false;
        }

        String base = "job." + id + ".";
        PROPS.setProperty(base + "open", "false");
        save();
        return true;
    }

    private static String safe(String text) {
        return text == null ? "" : text.replace("\n", " ").replace("\r", " ");
    }
}