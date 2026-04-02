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

public class PlotzListingDetailMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final String listingId;
    private final boolean backToMySales;

    public static void open(ServerPlayer player, String listingId, boolean backToMySales) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzListingDetailMenu(containerId, inventory, player, listingId, backToMySales),
            Component.literal(LanguageManager.tr("listing.menu.title"))
        ));
    }

    public PlotzListingDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, String listingId, boolean backToMySales) {
        this(containerId, inventory, viewer, new SimpleContainer(27), listingId, backToMySales);
    }

    private PlotzListingDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, String listingId, boolean backToMySales) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        this.listingId = listingId;
        this.backToMySales = backToMySales;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, ItemStack.EMPTY);
        }

        PlotzStore.Listing listing = PlotzStore.getListingById(listingId);
        if (listing == null) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, LanguageManager.tr("listing.missing")));
            box.setItem(22, MenuUtil.named(Items.BARRIER, LanguageManager.tr("market.back")));
            broadcastChanges();
            return;
        }

        box.setItem(10, MenuUtil.named(
            listing.capital() ? Items.ENCHANTED_BOOK : Items.BOOK,
            (listing.capital() ? "§6" : "§e") + listing.title()
        ));
        box.setItem(12, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.format("listing.price", listing.price())));
        box.setItem(13, MenuUtil.named(Items.PAPER, LanguageManager.format("listing.description", listing.description())));
        box.setItem(14, MenuUtil.named(Items.COMPASS, LanguageManager.format("listing.location", listing.location())));
        box.setItem(15, MenuUtil.named(Items.BRICKS, LanguageManager.format("listing.building", listing.builtOnPlot())));
        box.setItem(16, MenuUtil.named(Items.NAME_TAG, LanguageManager.format("listing.reason", listing.justification())));
        box.setItem(21, MenuUtil.named(Items.MAP, LanguageManager.format("listing.chunks", listing.chunkCount())));
        box.setItem(22, MenuUtil.named(Items.BARRIER, LanguageManager.tr("market.back")));
        box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("listing.buy")));

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        if (slotId == 22) {
            if (backToMySales) {
                PlotzMySalesMenu.open(sp);
            } else {
                PlotzMarketMenu.open(sp);
            }
            return;
        }

        if (slotId != 23) {
            return;
        }

        PlotzStore.Listing listing = PlotzStore.getListingById(listingId);
        if (listing == null) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("listing.not.exists")));
            if (backToMySales) {
                PlotzMySalesMenu.open(sp);
            } else {
                PlotzMarketMenu.open(sp);
            }
            return;
        }

        if (listing.sellerId().equals(sp.getUUID())) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("listing.buy.own")));
            return;
        }

        if (!PlotzLogic.tryCharge(sp, listing.price())) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("listing.not.enough")));
            return;
        }

        PlotzLogic.paySeller(sp, listing.sellerName(), listing.price());

        PlotzStore.addOwnedPlot(new PlotzStore.PlotEntry(
            sp.getUUID(),
            sp.getGameProfile().getName(),
            listing.title(),
            listing.capital(),
            listing.chunkCount(),
            listing.location(),
            listing.description()
        ));

        PlotzStore.removeListing(listing.listingId());
        sp.sendSystemMessage(Component.literal(LanguageManager.format("listing.buy.success", listing.title())));
        PlotzMyPlotsMenu.open(sp);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}