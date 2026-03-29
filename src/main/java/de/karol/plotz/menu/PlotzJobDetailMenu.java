package de.karol.plotz.menu;

import de.karol.plotz.service.BalanceManager;
import de.karol.plotz.service.JobManager;
import de.karol.plotz.service.TreasuryManager;
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

public class PlotzJobDetailMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final String jobId;
    private final int returnPage;

    public static void open(ServerPlayer player, String jobId, int returnPage) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzJobDetailMenu(containerId, inventory, player, jobId, returnPage),
            Component.literal("Job Details")
        ));
    }

    public PlotzJobDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, String jobId, int returnPage) {
        this(containerId, inventory, viewer, new SimpleContainer(27), jobId, returnPage);
    }

    private PlotzJobDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, String jobId, int returnPage) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        this.jobId = jobId;
        this.returnPage = returnPage;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        JobManager.JobEntry job = JobManager.getJob(jobId);
        if (job == null || !job.open()) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, "§cJob no longer available"));
            box.setItem(21, MenuUtil.named(Items.BARRIER, "§cBack"));
            MenuUtil.putPlayerInfoHead(box, viewer, 18);
            broadcastChanges();
            return;
        }

        box.setItem(10, MenuUtil.named(job.serverJob() ? Items.GOLD_BLOCK : Items.PAPER, "§e" + job.title()));
        box.setItem(11, MenuUtil.named(Items.GOLD_INGOT, "§6Reward: $" + job.reward()));
        box.setItem(12, MenuUtil.named(Items.NAME_TAG, "§7Created by: " + job.creatorName()));
        box.setItem(13, MenuUtil.named(Items.BOOK, "§7Description: " + job.description()));
        box.setItem(14, MenuUtil.named(Items.CLOCK, "§7Due: " + job.dueText()));
        box.setItem(21, MenuUtil.named(Items.BARRIER, "§cBack"));

        boolean owner = job.creatorId() != null && job.creatorId().equals(viewer.getUUID());
        boolean admin = viewer.hasPermissions(2);

        if (owner || admin) {
            box.setItem(23, MenuUtil.named(Items.RED_CONCRETE, "§cWithdraw Job"));
        } else {
            box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, "§aAccept Job"));
        }

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 21) {
            PlotzJobsMenu.open(sp, returnPage);
            return;
        }

        if (slotId != 23) {
            return;
        }

        JobManager.JobEntry job = JobManager.getJob(jobId);
        if (job == null || !job.open()) {
            sp.sendSystemMessage(Component.literal("§cJob no longer available."));
            PlotzJobsMenu.open(sp, returnPage);
            return;
        }

        boolean owner = job.creatorId() != null && job.creatorId().equals(sp.getUUID());
        boolean admin = sp.hasPermissions(2);

        if (owner || admin) {
            if (!JobManager.withdrawJob(jobId)) {
                sp.sendSystemMessage(Component.literal("§cCould not withdraw job."));
                return;
            }

            if (job.serverJob()) {
                TreasuryManager.addTreasury(job.reward());
            } else if (job.creatorId() != null) {
                BalanceManager.addBalance(job.creatorId(), job.reward());
            }

            sp.sendSystemMessage(Component.literal("§aJob withdrawn and refunded."));
            PlotzJobsMenu.open(sp, returnPage);
            return;
        }

        if (job.creatorId() != null && job.creatorId().equals(sp.getUUID())) {
            sp.sendSystemMessage(Component.literal("§cYou cannot accept your own job."));
            return;
        }

        if (!JobManager.acceptJob(jobId, sp.getGameProfile().getName())) {
            sp.sendSystemMessage(Component.literal("§cCould not accept job."));
            return;
        }

        BalanceManager.addBalance(sp.getUUID(), job.reward());
        sp.sendSystemMessage(Component.literal("§aYou accepted the job and received $" + job.reward()));
        PlotzJobsMenu.open(sp, returnPage);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}