package de.karol.plotz.menu;

import de.karol.plotz.service.JobsInputManager;
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

public class PlotzServerModeMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzServerModeMenu(containerId, inventory, player),
            Component.literal("Plotz Server Mode")
        ));
    }

    public PlotzServerModeMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(27));
    }

    private PlotzServerModeMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        box.setItem(10, MenuUtil.named(Items.GOLD_BLOCK, "§6Treasury: $" + TreasuryManager.getTreasury()));
        box.setItem(12, MenuUtil.named(Items.RED_CONCRETE, "§cTax -1%"));
        box.setItem(13, MenuUtil.named(Items.PAPER, "§7Tax Rate: " + TreasuryManager.getTaxPercent() + "%"));
        box.setItem(14, MenuUtil.named(Items.LIME_CONCRETE, "§aTax +1%"));
        box.setItem(16, MenuUtil.named(Items.EMERALD, "§aCreate Server Job"));
        box.setItem(22, MenuUtil.named(Items.BOOK, "§bOpen Jobs"));
        MenuUtil.putPlayerInfoHead(box, viewer, 18);

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 12) {
            TreasuryManager.setTaxPercent(TreasuryManager.getTaxPercent() - 1);
            refresh();
            return;
        }

        if (slotId == 14) {
            TreasuryManager.setTaxPercent(TreasuryManager.getTaxPercent() + 1);
            refresh();
            return;
        }

        if (slotId == 16) {
            JobsInputManager.startServerJob(sp);
            return;
        }

        if (slotId == 22) {
            PlotzJobsMenu.open(sp, 0);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}