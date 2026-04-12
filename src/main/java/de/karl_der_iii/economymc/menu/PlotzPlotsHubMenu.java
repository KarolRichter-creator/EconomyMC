package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.OpacBridge;
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
import java.util.UUID;

public class PlotzPlotsHubMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzPlotsHubMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("plots.menu.title"))
        ));
    }

    public PlotzPlotsHubMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(45));
    }

    private PlotzPlotsHubMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x5, containerId, inventory, box, 5);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private ItemStack connectorItem() {
        boolean connected = OpacBridge.isInstalled();
        return MenuUtil.named(
            connected ? Items.LIME_DYE : Items.RED_DYE,
            LanguageManager.tr("plots.connector"),
            List.of(connected ? "§a●" : "§c●")
        );
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        UUID id = viewer.getUUID();

        box.setItem(4, MenuUtil.named(Items.MAP, LanguageManager.tr("plots.menu.title")));
        box.setItem(11, MenuUtil.named(
            Items.CHEST,
            LanguageManager.tr("plots.menu.market_only") + " §7(" + PlotzStore.getListings().size() + ")"
        ));
        box.setItem(13, MenuUtil.named(
            Items.GOLD_INGOT,
            LanguageManager.tr("plots.menu.buy_info")
        ));
        box.setItem(22, connectorItem());
        box.setItem(29, MenuUtil.named(
            Items.WRITABLE_BOOK,
            LanguageManager.tr("plots.menu.sale_drafts"),
            List.of(LanguageManager.tr("plots.menu.sell_info"))
        ));
        box.setItem(33, MenuUtil.playerInfoHead(viewer));
        box.setItem(40, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        if (slotId == 11) {
            PlotzMarketMenu.open(sp);
            return;
        }

        if (slotId == 29) {
            PlotzCreateSaleMenu.open(sp);
            return;
        }

        if (slotId == 40) {
            PlotzMainMenu.open(sp);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
