package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.OpacBridge;
import de.karl_der_iii.economymc.service.PendingPlotSelectionManager;
import de.karl_der_iii.economymc.service.PendingPlotSelectionManager.PendingSelection;
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

import java.util.List;

public class PlotzClaimConfirmMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzClaimConfirmMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("plots.confirm.menu"))
        ));
    }

    public PlotzClaimConfirmMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(27));
    }

    private PlotzClaimConfirmMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        PendingSelection s = PendingPlotSelectionManager.get(viewer);
        if (s == null) {
            box.setItem(13, MenuUtil.named(Items.BARRIER, LanguageManager.tr("plots.confirm.no_selection")));
            broadcastChanges();
            return;
        }

        box.setItem(11, MenuUtil.named(
            Items.EMERALD,
            LanguageManager.tr("plots.confirm.buy"),
            List.of(
                LanguageManager.tr("plots.confirm.chunks") + s.chunkCount(),
                LanguageManager.tr("plots.confirm.type") + (s.capital() ? LanguageManager.tr("plots.confirm.capital") : LanguageManager.tr("plots.confirm.normal")),
                LanguageManager.tr("plots.confirm.price") + "$" + s.totalPrice()
            )
        ));

        box.setItem(15, MenuUtil.named(
            Items.BARRIER,
            LanguageManager.tr("common.cancel")
        ));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        PendingSelection s = PendingPlotSelectionManager.get(sp);
        if (s == null) {
            sp.closeContainer();
            return;
        }

        if (slotId == 11) {
            if (!PlotzLogic.tryCharge(sp, s.totalPrice())) {
                sp.sendSystemMessage(Component.literal(LanguageManager.format("plots.claim.not_enough.normal", s.totalPrice())));
                sp.closeContainer();
                return;
            }

            boolean success = OpacBridge.claimPendingSelection(sp, s);
            if (success) {
                sp.sendSystemMessage(Component.literal(LanguageManager.format("plots.confirm.success", s.totalPrice())));
            } else {
                sp.sendSystemMessage(Component.literal(LanguageManager.tr("plots.confirm.failed")));
            }
            PendingPlotSelectionManager.remove(sp);
            sp.closeContainer();
            return;
        }

        if (slotId == 15) {
            PendingPlotSelectionManager.remove(sp);
            sp.closeContainer();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}