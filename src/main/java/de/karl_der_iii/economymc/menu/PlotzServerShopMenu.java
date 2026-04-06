package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.AdminSettingsManager;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.ServerShopManager;
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

public class PlotzServerShopMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzServerShopMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("server.shop.menu.title"))
        ));
    }

    public PlotzServerShopMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(45));
    }

    private PlotzServerShopMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x5, containerId, inventory, box, 5);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        box.setItem(4, MenuUtil.named(Items.GOLD_BLOCK, LanguageManager.tr("server.shop.menu.title")));

        box.setItem(20, MenuUtil.named(
            ServerShopManager.Category.ARMOR.icon(),
            LanguageManager.tr(ServerShopManager.Category.ARMOR.translationKey())
        ));
        box.setItem(22, MenuUtil.named(
            ServerShopManager.Category.BLOCKS.icon(),
            LanguageManager.tr(ServerShopManager.Category.BLOCKS.translationKey())
        ));
        box.setItem(24, MenuUtil.named(
            ServerShopManager.Category.FOOD.icon(),
            LanguageManager.tr(ServerShopManager.Category.FOOD.translationKey())
        ));

        box.setItem(40, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
        box.setItem(44, MenuUtil.playerInfoHead(viewer));

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

        if (slotId == 20) {
            PlotzServerShopCategoryMenu.open(sp, ServerShopManager.Category.ARMOR);
            return;
        }
        if (slotId == 22) {
            PlotzServerShopCategoryMenu.open(sp, ServerShopManager.Category.BLOCKS);
            return;
        }
        if (slotId == 24) {
            PlotzServerShopCategoryMenu.open(sp, ServerShopManager.Category.FOOD);
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