package de.karol.plotz.menu;

import de.karol.plotz.data.PlotzStore;
import de.karol.plotz.service.OpacBridge;
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

import java.util.List;

public class PlotzMyPlotsMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        OpacBridge.syncOwnedClaims(player);

        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzMyPlotsMenu(containerId, inventory, player),
            Component.literal("Mein Besitz")
        ));
    }

    public PlotzMyPlotsMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(54));
    }

    private PlotzMyPlotsMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, ItemStack.EMPTY);
        }

        List<PlotzStore.PlotEntry> plots = PlotzStore.getOwnedPlots(viewer.getUUID());
        int slot = 10;

        for (PlotzStore.PlotEntry plot : plots) {
            if (slot % 9 == 8) {
                slot++;
            }
            if (slot >= 44) {
                break;
            }

            box.setItem(slot, MenuUtil.named(
                plot.capital() ? Items.FILLED_MAP : Items.MAP,
                (plot.capital() ? "§6" : "§b")
                    + plot.title()
                    + " §7| " + plot.chunkCount() + " Chunks | " + plot.location()
            ));
            slot++;
        }

        box.setItem(49, MenuUtil.named(Items.BARRIER, "§cZurück"));
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId == 49 && player instanceof ServerPlayer sp) {
            PlotzMainMenu.open(sp);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}