package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.service.DraftInputManager;
import de.karl_der_iii.economymc.service.LanguageManager;
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

public class PlotzCreateSaleMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private long lastClickMs = 0L;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzCreateSaleMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("sale.menu.title"))
        ));
    }

    public PlotzCreateSaleMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(27));
    }

    private PlotzCreateSaleMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void fillBackground() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }
    }

    private void refresh() {
        fillBackground();

        PlotzStore.SaleDraft draft = PlotzStore.getDraft(viewer.getUUID());

        if (draft == null) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, LanguageManager.tr("sale.no_draft")));
            box.setItem(22, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
            MenuUtil.putPlayerInfoHead(box, viewer, 18);
            broadcastChanges();
            return;
        }

        box.setItem(4, MenuUtil.named(
            Items.COMPASS,
            draft.capital() ? LanguageManager.tr("sale.selected_capital") : LanguageManager.tr("sale.selected_plot")
        ));

        box.setItem(9, MenuUtil.named(Items.MAP, LanguageManager.tr("sale.plot") + draft.chunkCount() + LanguageManager.tr("sale.chunks")));
        box.setItem(10, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.tr("sale.set_price") + draft.price()));
        box.setItem(11, MenuUtil.named(Items.PAPER, LanguageManager.tr("sale.set_description")));
        box.setItem(12, MenuUtil.named(Items.BRICKS, LanguageManager.tr("sale.set_built")));
        box.setItem(13, MenuUtil.named(Items.NAME_TAG, LanguageManager.tr("sale.set_justification")));
        box.setItem(14, MenuUtil.named(
            draft.negotiable() ? Items.EMERALD : Items.GOLD_BLOCK,
            draft.negotiable() ? LanguageManager.tr("sale.negotiable") : LanguageManager.tr("sale.fixed")
        ));
        box.setItem(15, MenuUtil.named(Items.WRITABLE_BOOK, LanguageManager.tr("sale.publish")));
        box.setItem(21, MenuUtil.named(Items.COMPASS, LanguageManager.tr("sale.location") + draft.location()));
        box.setItem(22, MenuUtil.named(Items.BARRIER, LanguageManager.tr("sale.clear_draft")));
        box.setItem(23, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        MenuUtil.putPlayerInfoHead(box, viewer, 18);
        broadcastChanges();
    }

    private boolean clickAllowed() {
        long now = System.currentTimeMillis();
        if (now - lastClickMs < 250L) {
            return false;
        }
        lastClickMs = now;
        return true;
    }

    private void publishListing(ServerPlayer sp) {
        PlotzStore.SaleDraft draft = PlotzStore.getDraft(sp.getUUID());
        if (draft == null) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("sale.no_draft")));
            PlotzMainMenu.open(sp);
            return;
        }

        if (PlotzStore.hasListingForLocation(draft.location())) {
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("sale.already_listed")));
            return;
        }

        PlotzStore.addListing(new PlotzStore.Listing(
            java.util.UUID.randomUUID().toString(),
            sp.getUUID(),
            sp.getGameProfile().getName(),
            draft.title(),
            draft.price(),
            draft.capital(),
            draft.chunkCount(),
            draft.location(),
            draft.description(),
            draft.justification(),
            draft.builtOnPlot(),
            draft.negotiable()
        ));

        PlotzStore.clearDraft(sp.getUUID());
        sp.sendSystemMessage(Component.literal(LanguageManager.tr("sale.published")));
        PlotzMainMenu.open(sp);
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;
        if (!clickAllowed()) return;

        PlotzStore.SaleDraft draft = PlotzStore.getDraft(sp.getUUID());

        if (slotId == 10 && draft != null) {
            DraftInputManager.waitForPrice(sp);
            return;
        }

        if (slotId == 11 && draft != null) {
            DraftInputManager.waitForDescription(sp);
            return;
        }

        if (slotId == 12 && draft != null) {
            DraftInputManager.waitForBuilt(sp);
            return;
        }

        if (slotId == 13 && draft != null) {
            DraftInputManager.waitForJustification(sp);
            return;
        }

        if (slotId == 14 && draft != null) {
            PlotzStore.updateDraftNegotiable(sp.getUUID(), !draft.negotiable());
            refresh();
            return;
        }

        if (slotId == 15 && draft != null) {
            publishListing(sp);
            return;
        }

        if (slotId == 22) {
            PlotzStore.clearDraft(sp.getUUID());
            sp.sendSystemMessage(Component.literal(LanguageManager.tr("sale.cleared")));
            PlotzMainMenu.open(sp);
            return;
        }

        if (slotId == 23) {
            PlotzMainMenu.open(sp);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}