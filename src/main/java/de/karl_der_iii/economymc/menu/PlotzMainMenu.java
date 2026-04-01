package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.PlotzLogic;
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

import java.util.UUID;

public class PlotzMainMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzMainMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("plots.menu.title"))
        ));
    }

    public PlotzMainMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(27));
    }

    private PlotzMainMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, ItemStack.EMPTY);
        }

        UUID id = viewer.getUUID();
        boolean capitalHere = PlotzLogic.isCapital(viewer.blockPosition());

        box.setItem(11, MenuUtil.named(
            Items.BOOK,
            LanguageManager.format("plots.buy.normal", PlotzStore.getNormalCredits(id), PlotzLogic.NORMAL_CHUNK_PRICE)
        ));

        box.setItem(13, MenuUtil.named(
            Items.ENCHANTED_BOOK,
            LanguageManager.format("plots.buy.capital", PlotzStore.getCapitalCredits(id), PlotzLogic.CAPITAL_CHUNK_PRICE)
        ));

        box.setItem(15, MenuUtil.named(
            Items.EMERALD,
            LanguageManager.tr("plots.create.sale")
        ));

        box.setItem(19, MenuUtil.named(
            Items.COMPASS,
            capitalHere ? LanguageManager.tr("plots.current.capital") : LanguageManager.tr("plots.current.normal")
        ));

        box.setItem(20, MenuUtil.named(
            Items.MAP,
            LanguageManager.format("plots.owned", PlotzStore.getOwnedPlots(id).size())
        ));

        box.setItem(22, MenuUtil.named(
            Items.WRITABLE_BOOK,
            LanguageManager.format("plots.sales", PlotzStore.getListingsBySeller(id).size())
        ));

        box.setItem(24, MenuUtil.named(
            Items.CHEST,
            LanguageManager.format("plots.market", PlotzStore.getListings().size())
        ));

        box.setItem(25, MenuUtil.named(
            Items.CLOCK,
            LanguageManager.tr("plots.history")
        ));

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        if (slotId == 11) {
            if (PlotzLogic.tryCharge(sp, PlotzLogic.NORMAL_CHUNK_PRICE)) {
                PlotzStore.addNormalCredit(sp.getUUID(), 1);
                sp.sendSystemMessage(Component.literal("§a1 " + LanguageManager.tr("credits.normal") + " gekauft."));
                refresh();
            } else {
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("listing.not.enough")));
            }
            return;
        }

        if (slotId == 13) {
            if (PlotzLogic.tryCharge(sp, PlotzLogic.CAPITAL_CHUNK_PRICE)) {
                PlotzStore.addCapitalCredit(sp.getUUID(), 1);
                sp.sendSystemMessage(Component.literal("§a1 " + LanguageManager.tr("credits.capital") + " gekauft."));
                refresh();
            } else {
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("listing.not.enough")));
            }
            return;
        }

        if (slotId == 15) {
            PlotzCreateSaleMenu.open(sp);
            return;
        }

        if (slotId == 20) {
            PlotzMyPlotsMenu.open(sp);
            return;
        }

        if (slotId == 22) {
            PlotzMySalesMenu.open(sp);
            return;
        }

        if (slotId == 24) {
            PlotzMarketMenu.open(sp);
            return;
        }

        if (slotId == 25) {
            PlotzHistoryMenu.open(sp, false);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}