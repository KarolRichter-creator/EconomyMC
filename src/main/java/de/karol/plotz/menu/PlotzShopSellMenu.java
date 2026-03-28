package de.karol.plotz.menu;

import de.karol.plotz.data.PlotzStore;
import de.karol.plotz.service.ShopInputManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotzShopSellMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private boolean published = false;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzShopSellMenu(containerId, inventory, player),
            Component.literal("Sell Item")
        ));
    }

    public PlotzShopSellMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(54));
    }

    private PlotzShopSellMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 27; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        PlotzStore.ShopDraft draft = PlotzStore.getShopDraft(viewer.getUUID());
        if (draft != null) {
            box.setItem(49, MenuUtil.named(Items.GOLD_INGOT, "§eSet Price: $" + draft.price()));
        } else {
            box.setItem(49, MenuUtil.named(Items.GOLD_INGOT, "§eSet Price"));
        }

        box.setItem(50, MenuUtil.named(Items.WRITABLE_BOOK, "§aPublish"));
        box.setItem(53, MenuUtil.named(Items.BARRIER, "§cBack"));
        MenuUtil.putPlayerInfoHead(box, viewer, 45);
        broadcastChanges();
    }

    private ItemStack getFirstSellItem() {
        for (int i = 0; i < 27; i++) {
            ItemStack stack = box.getItem(i);
            if (!stack.isEmpty()) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    private void clearSellArea() {
        for (int i = 0; i < 27; i++) {
            box.setItem(i, ItemStack.EMPTY);
        }
    }

    private void saveDraftFromContainer() {
        ItemStack item = getFirstSellItem();
        if (item.isEmpty()) {
            return;
        }

        PlotzStore.ShopDraft existing = PlotzStore.getShopDraft(viewer.getUUID());
        int price = existing == null ? 100 : existing.price();

        PlotzStore.setShopDraft(new PlotzStore.ShopDraft(
            viewer.getUUID(),
            viewer.getGameProfile().getName(),
            item.copy(),
            price
        ));
    }

    private void publish() {
        PlotzStore.ShopDraft draft = PlotzStore.getShopDraft(viewer.getUUID());
        if (draft == null || draft.item().isEmpty()) {
            viewer.sendSystemMessage(Component.literal("§cPut an item in first."));
            return;
        }

        PlotzStore.addShopListing(new PlotzStore.ShopListing(
            UUID.randomUUID().toString(),
            viewer.getUUID(),
            viewer.getGameProfile().getName(),
            draft.item().copy(),
            draft.price()
        ));

        clearSellArea();
        PlotzStore.clearShopDraft(viewer.getUUID());
        published = true;
        viewer.sendSystemMessage(Component.literal("§aShop listing published."));
        PlotzShopMenu.open(viewer);
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        super.clicked(slotId, button, clickType, player);

        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 49) {
            saveDraftFromContainer();
            if (PlotzStore.getShopDraft(sp.getUUID()) == null) {
                sp.sendSystemMessage(Component.literal("§cPut an item in first."));
                return;
            }
            ShopInputManager.waitForPrice(sp);
            return;
        }

        if (slotId == 50) {
            saveDraftFromContainer();
            publish();
            return;
        }

        if (slotId == 53) {
            PlotzShopMenu.open(sp);
            return;
        }

        refresh();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!(player instanceof ServerPlayer sp)) return;
        if (published) return;

        List<ItemStack> toReturn = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            ItemStack stack = box.getItem(i);
            if (!stack.isEmpty()) {
                toReturn.add(stack.copy());
            }
        }

        clearSellArea();

        for (ItemStack stack : toReturn) {
            if (!sp.getInventory().add(stack)) {
                sp.drop(stack, false);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}