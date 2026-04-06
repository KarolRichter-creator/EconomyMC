package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.AdminSettingsManager;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.ServerShopManager;
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
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotzServerShopCategoryMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final ServerShopManager.Category category;
    private final Map<Integer, ServerShopManager.Entry> entriesBySlot = new HashMap<>();

    public static void open(ServerPlayer player, ServerShopManager.Category category) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzServerShopCategoryMenu(containerId, inventory, player, category),
            Component.literal(LanguageManager.tr(category.translationKey()))
        ));
    }

    public PlotzServerShopCategoryMenu(int containerId, Inventory inventory, ServerPlayer viewer, ServerShopManager.Category category) {
        this(containerId, inventory, viewer, new SimpleContainer(54), category);
    }

    private PlotzServerShopCategoryMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, ServerShopManager.Category category) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        this.category = category;
        refresh();
    }

    private ItemStack itemEntry(ServerShopManager.Entry entry) {
        ItemStack stack = ServerShopManager.createDisplayStack(entry);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(LanguageManager.tr(ServerShopManager.entryNameKey(entry))));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.literal(LanguageManager.tr("server.shop.price") + entry.price()));
        lore.add(Component.literal(LanguageManager.tr("server.shop.amount") + entry.count()));
        lore.add(Component.literal(LanguageManager.tr("common.click_to_view")));
        stack.set(DataComponents.LORE, new ItemLore(lore));
        return stack;
    }

    private void refresh() {
        entriesBySlot.clear();

        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        box.setItem(4, MenuUtil.named(category.icon(), LanguageManager.tr(category.translationKey())));

        List<ServerShopManager.Entry> entries = ServerShopManager.getEntries(category);
        int slot = 10;
        for (ServerShopManager.Entry entry : entries) {
            while (slot % 9 == 8) {
                slot += 2;
            }
            if (slot >= 44) break;
            box.setItem(slot, itemEntry(entry));
            entriesBySlot.put(slot, entry);
            slot++;
        }

        box.setItem(45, MenuUtil.playerInfoHead(viewer));
        box.setItem(49, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (!AdminSettingsManager.serverShopEnabled()) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("msg.server_shop_disabled")));
            PlotzMainMenu.open(sp);
            return;
        }

        if (slotId == 49) {
            PlotzServerShopMenu.open(sp);
            return;
        }

        ServerShopManager.Entry entry = entriesBySlot.get(slotId);
        if (entry == null) {
            return;
        }

        if (!ServerShopManager.buy(sp, entry)) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("server.shop.not_enough_money")));
            return;
        }

        sp.sendSystemMessage(Component.literal(LanguageManager.format(
            "server.shop.bought",
            LanguageManager.tr(ServerShopManager.entryNameKey(entry)),
            entry.price()
        )));
        refresh();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}