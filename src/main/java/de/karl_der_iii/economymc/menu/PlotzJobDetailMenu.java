package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.JobManager;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.TreasuryManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PlotzJobDetailMenu extends ChestMenu {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm");
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final String jobId;
    private final int returnPage;
    private final boolean allowCreate;
    private final boolean serverOnly;

    public static void open(ServerPlayer player, String jobId, int returnPage, boolean allowCreate, boolean serverOnly) {
        JobManager.processExpiredJobs();

        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzJobDetailMenu(containerId, inventory, player, jobId, returnPage, allowCreate, serverOnly),
            Component.literal(LanguageManager.tr("job.detail.title"))
        ));
    }

    public PlotzJobDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, String jobId, int returnPage, boolean allowCreate, boolean serverOnly) {
        this(containerId, inventory, viewer, new SimpleContainer(27), jobId, returnPage, allowCreate, serverOnly);
    }

    private PlotzJobDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, String jobId, int returnPage, boolean allowCreate, boolean serverOnly) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        this.jobId = jobId;
        this.returnPage = returnPage;
        this.allowCreate = allowCreate;
        this.serverOnly = serverOnly;
        refresh();
    }

    private String formatTime(long millis) {
        return FORMATTER.format(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()));
    }

    private String statusLine(JobManager.JobEntry job) {
        return switch (job.status()) {
            case OPEN -> JobManager.canAcceptNow(job)
                ? LanguageManager.tr("jobs.status.open")
                : LanguageManager.tr("jobs.status.opens_at") + formatTime(job.availableAt());
            case IN_PROGRESS -> LanguageManager.tr("jobs.in_progress_by") + job.workerName();
            case COMPLETED -> LanguageManager.tr("jobs.status.completed") + " " + job.workerName();
            case CONFIRMED -> LanguageManager.tr("jobs.status.confirmed");
            case CANCELLED -> LanguageManager.tr("jobs.status.cancelled");
            case FAILED -> LanguageManager.tr("jobs.status.failed");
        };
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        JobManager.JobEntry job = JobManager.getJob(jobId);
        if (job == null) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, LanguageManager.tr("jobs.unavailable")));
            box.setItem(21, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
            MenuUtil.putPlayerInfoHead(box, viewer, 18);
            broadcastChanges();
            return;
        }

        box.setItem(10, MenuUtil.named(job.serverJob() ? Items.GOLD_BLOCK : Items.PAPER, "§e" + job.title()));
        box.setItem(11, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.tr("jobs.reward") + job.reward()));
        box.setItem(12, MenuUtil.named(Items.NAME_TAG, LanguageManager.tr("jobs.created_by") + job.creatorName()));
        box.setItem(13, MenuUtil.named(Items.BOOK, LanguageManager.tr("jobs.description") + job.description()));
        box.setItem(14, MenuUtil.named(Items.CLOCK, LanguageManager.tr("jobs.due_in") + job.dueDays() + LanguageManager.tr("jobs.day_suffix")));
        box.setItem(15, MenuUtil.named(Items.COMPASS, LanguageManager.tr("common.status") + ": " + statusLine(job)));
        box.setItem(16, MenuUtil.named(Items.GOLD_NUGGET, LanguageManager.tr("jobs.current_payout") + JobManager.calculateCurrentReward(job)));
        box.setItem(21, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        boolean creator = job.creatorId() != null && job.creatorId().equals(viewer.getUUID());
        boolean worker = job.workerId() != null && job.workerId().equals(viewer.getUUID());
        boolean serverModeManage = serverOnly && viewer.hasPermissions(2);

        if (job.status() == JobManager.JobStatus.OPEN) {
            if (job.serverJob() && serverModeManage) {
                if (JobManager.canAcceptNow(job)) {
                    box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("jobs.accept_server")));
                } else {
                    box.setItem(23, MenuUtil.named(Items.CLOCK, LanguageManager.tr("jobs.available_at") + formatTime(job.availableAt())));
                }
                box.setItem(24, MenuUtil.named(Items.RED_CONCRETE, LanguageManager.tr("jobs.withdraw")));
            } else if (creator) {
                box.setItem(23, MenuUtil.named(Items.RED_CONCRETE, LanguageManager.tr("jobs.withdraw")));
            } else {
                if (JobManager.canAcceptNow(job)) {
                    box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("jobs.accept")));
                } else {
                    box.setItem(23, MenuUtil.named(Items.CLOCK, LanguageManager.tr("jobs.available_at") + formatTime(job.availableAt())));
                }
            }
        } else if (job.status() == JobManager.JobStatus.IN_PROGRESS) {
            if (worker) {
                box.setItem(23, MenuUtil.named(Items.WRITABLE_BOOK, LanguageManager.tr("jobs.mark_completed")));
                box.setItem(24, MenuUtil.named(Items.RED_CONCRETE, LanguageManager.tr("jobs.cancel_job")));
            } else {
                box.setItem(23, MenuUtil.named(Items.NAME_TAG, LanguageManager.tr("jobs.in_progress_by") + job.workerName()));
            }
        } else if (job.status() == JobManager.JobStatus.COMPLETED) {
            if (creator || serverModeManage) {
                box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("jobs.confirm_job")));
            } else {
                box.setItem(23, MenuUtil.named(Items.NAME_TAG, LanguageManager.tr("jobs.waiting_confirmation")));
            }
        }

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 21) {
            PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
            return;
        }

        JobManager.JobEntry job = JobManager.getJob(jobId);
        if (job == null) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.unavailable")));
            PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
            return;
        }

        boolean creator = job.creatorId() != null && job.creatorId().equals(sp.getUUID());
        boolean worker = job.workerId() != null && job.workerId().equals(sp.getUUID());
        boolean serverModeManage = serverOnly && sp.hasPermissions(2);

        if (slotId == 23) {
            if (job.status() == JobManager.JobStatus.OPEN) {
                if (job.serverJob() && serverModeManage) {
                    if (!JobManager.canAcceptNow(job)) {
                        sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.accept_yet")));
                        return;
                    }
                    if (!JobManager.acceptJob(jobId, sp.getUUID(), sp.getGameProfile().getName())) {
                        sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.not_open")));
                        return;
                    }
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.accepted_server")));
                    PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                    return;
                }

                if (creator) {
                    if (!JobManager.withdrawByCreator(jobId)) {
                        sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.withdraw")));
                        return;
                    }
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.withdrawn")));
                    PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                    return;
                }

                if (!JobManager.canAcceptNow(job)) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.accept_yet")));
                    return;
                }

                if (job.creatorId() != null && job.creatorId().equals(sp.getUUID())) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.own_job")));
                    return;
                }

                if (!JobManager.acceptJob(jobId, sp.getUUID(), sp.getGameProfile().getName())) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.not_open")));
                    return;
                }

                sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.accepted")));
                PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                return;
            }

            if (job.status() == JobManager.JobStatus.IN_PROGRESS && worker) {
                if (!JobManager.markCompleted(jobId, sp.getUUID())) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.complete")));
                    return;
                }
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.completed")));
                PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                return;
            }

            if (job.status() == JobManager.JobStatus.COMPLETED && (creator || serverModeManage)) {
                if (!JobManager.confirmJob(jobId)) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.confirm")));
                    return;
                }
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.confirmed")));
                PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                return;
            }
        }

        if (slotId == 24) {
            if (job.status() == JobManager.JobStatus.OPEN && job.serverJob() && serverModeManage) {
                if (!JobManager.withdrawByCreator(jobId)) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.withdraw")));
                    return;
                }
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.withdrawn_server")));
                PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
                return;
            }

            if (job.status() == JobManager.JobStatus.IN_PROGRESS && worker) {
                if (!JobManager.cancelByWorker(jobId)) {
                    sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.error.cancel")));
                    return;
                }

                int penalty = TreasuryManager.calculateCancelPenalty(job.reward());
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("jobs.msg.cancelled_penalty") + penalty));
                PlotzJobsMenu.open(sp, returnPage, allowCreate, serverOnly);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}