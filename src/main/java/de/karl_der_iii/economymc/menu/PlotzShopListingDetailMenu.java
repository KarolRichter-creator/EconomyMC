package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.service.BalanceManager;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.ScoreboardManager;
import de.karl_der_iii.economymc.service.TreasuryManager;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.ItemLore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlotzShopListingDetailMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final String listingId;
    private final int returnPage;

    public static void open(ServerPlayer player, String listingId, int returnPage) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzShopListingDetailMenu(containerId, inventory, player, listingId, returnPage),
            Component.literal(LanguageManager.tr("shop.menu.title"))
        ));
    }

    public PlotzShopListingDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, String listingId, int returnPage) {
        this(containerId, inventory, viewer, new SimpleContainer(27), listingId, returnPage);
    }

    private PlotzShopListingDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, String listingId, int returnPage) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        this.listingId = listingId;
        this.returnPage = returnPage;
        refresh();
    }

    private ItemStack createPreview(PlotzStore.ShopListing listing) {
        int base = listing.price();
        int tax = TreasuryManager.calculateTax(base);
        int total = TreasuryManager.calculateTotalWithTax(base);

        if (listing.items().size() == 1) {
            ItemStack stack = listing.items().get(0).copy();
            List<Component> lore = new ArrayList<>();
            lore.add(Component.literal(LanguageManager.tr("shop.base_price") + base));
            lore.add(Component.literal(LanguageManager.tr("shop.tax") + tax));
            lore.add(Component.literal(LanguageManager.tr("shop.total") + total));
            lore.add(Component.literal(LanguageManager.tr("common.seller") + ": " + listing.sellerName()));
            lore.add(Component.literal(LanguageManager.tr("common.amount") + ": " + stack.getCount()));
            stack.set(DataComponents.LORE, new ItemLore(lore));
            return stack;
        }

        ItemStack boxItem = new ItemStack(Items.SHULKER_BOX);
        boxItem.set(DataComponents.CUSTOM_NAME, Component.literal(LanguageManager.tr("shop.bundle")));

        Map<String, Integer> grouped = new LinkedHashMap<>();
        int totalCount = 0;

        for (ItemStack item : listing.items()) {
            String name = item.getHoverName().getString();
            grouped.put(name, grouped.getOrDefault(name, 0) + item.getCount());
            totalCount += item.getCount();
        }

        List<Component> lore = new ArrayList<>();
        lore.add(Component.literal(LanguageManager.tr("shop.base_price") + base));
        lore.add(Component.literal(LanguageManager.tr("shop.tax") + tax));
        lore.add(Component.literal(LanguageManager.tr("shop.total") + total));
        lore.add(Component.literal(LanguageManager.tr("common.seller") + ": " + listing.sellerName()));
        lore.add(Component.literal(LanguageManager.tr("shop.stacks_inside") + listing.items().size()));
        lore.add(Component.literal(LanguageManager.tr("shop.total_items") + totalCount));

        for (Map.Entry<String, Integer> entry : grouped.entrySet()) {
            lore.add(Component.literal("§f" + entry.getValue() + "x " + entry.getKey()));
        }

        boxItem.set(DataComponents.LORE, new ItemLore(lore));
        return boxItem;
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        PlotzStore.ShopListing listing = PlotzStore.getShopListingById(listingId);
        if (listing == null) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, "§c" + LanguageManager.tr("shop.listing_missing")));
            box.setItem(21, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
            MenuUtil.putPlayerInfoHead(box, viewer, 18);
            broadcastChanges();
            return;
        }

        box.setItem(13, createPreview(listing));
        box.setItem(21, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        if (listing.sellerId().equals(viewer.getUUID())) {
            box.setItem(23, MenuUtil.named(Items.RED_CONCRETE, LanguageManager.tr("shop.withdraw")));
        } else {
            box.setItem(23, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("shop.buy")));
        }

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 21) {
            PlotzShopMenu.open(sp, returnPage);
            return;
        }

        if (slotId != 23) {
            return;
        }

        PlotzStore.ShopListing listing = PlotzStore.getShopListingById(listingId);
        if (listing == null) {
            sp.sendSystemMessage(Component.literal("§c" + LanguageManager.tr("shop.listing_missing")));
            PlotzShopMenu.open(sp, returnPage);
            return;
        }

        if (listing.sellerId().equals(sp.getUUID())) {
            for (ItemStack stack : listing.items()) {
                if (!sp.getInventory().add(stack.copy())) {
                    sp.drop(stack.copy(), false);
                }
            }

            PlotzStore.removeShopListing(listingId);
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("shop.withdrawn")));
            PlotzShopMenu.open(sp, returnPage);
            return;
        }

        int total = TreasuryManager.calculateTotalWithTax(listing.price());
        int tax = TreasuryManager.calculateTax(listing.price());

        if (!BalanceManager.removeBalance(sp.getUUID(), total)) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("shop.not_enough_money")));
            return;
        }

        TreasuryManager.addTreasury(tax);
        BalanceManager.addBalance(listing.sellerId(), listing.price());
        ScoreboardManager.update(sp.server);

        for (ItemStack stack : listing.items()) {
            if (!sp.getInventory().add(stack.copy())) {
                sp.drop(stack.copy(), false);
            }
        }

        PlotzStore.removeShopListing(listingId);
        sp.sendSystemMessage(Component.literal(LanguageManager.format("shop.bought", total, tax)));
        PlotzShopMenu.open(sp, returnPage);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}