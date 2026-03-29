package de.karol.plotz.menu;

import de.karol.plotz.service.JobManager;
import de.karol.plotz.service.JobsInputManager;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotzJobsMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final int page;
    private final Map<Integer, String> jobIdsBySlot = new HashMap<>();

    public static void open(ServerPlayer player, int page) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzJobsMenu(containerId, inventory, player, page),
            Component.literal("Jobs")
        ));
    }

    public PlotzJobsMenu(int containerId, Inventory inventory, ServerPlayer viewer, int page) {
        this(containerId, inventory, viewer, new SimpleContainer(54), page);
    }

    private PlotzJobsMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, int page) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        this.page = page;
        refresh();
    }

    private void refresh() {
        jobIdsBySlot.clear();

        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        List<JobManager.JobEntry> jobs = JobManager.getOpenJobs();
        int start = page * 45;
        int end = Math.min(start + 45, jobs.size());

        int slot = 0;
        for (int i = start; i < end; i++) {
            JobManager.JobEntry job = jobs.get(i);
            box.setItem(slot, MenuUtil.named(
                job.serverJob() ? Items.GOLD_BLOCK : Items.PAPER,
                (job.serverJob() ? "§6" : "§e") + job.title() + " §7| $" + job.reward()
            ));
            jobIdsBySlot.put(slot, job.id());
            slot++;
        }

        box.setItem(45, MenuUtil.playerInfoHead(viewer));
        box.setItem(49, MenuUtil.named(Items.BARRIER, "§cClose"));
        box.setItem(50, MenuUtil.named(Items.ARROW, "§7Previous Page"));
        box.setItem(51, MenuUtil.named(Items.PAPER, "§7Page " + (page + 1)));
        box.setItem(52, MenuUtil.named(Items.ARROW, "§7Next Page"));
        box.setItem(53, MenuUtil.named(Items.EMERALD, "§aAdd Job"));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 49) {
            sp.closeContainer();
            return;
        }

        if (slotId == 50) {
            if (page > 0) open(sp, page - 1);
            return;
        }

        if (slotId == 52) {
            if ((page + 1) * 45 < JobManager.getOpenJobs().size()) open(sp, page + 1);
            return;
        }

        if (slotId == 53) {
            JobsInputManager.startPlayerJob(sp);
            return;
        }

        String jobId = jobIdsBySlot.get(slotId);
        if (jobId != null) {
            PlotzJobDetailMenu.open(sp, jobId, page);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}