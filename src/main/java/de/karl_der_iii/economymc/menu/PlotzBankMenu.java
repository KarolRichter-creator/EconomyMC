package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.LoanManager;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotzBankMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final Map<Integer, String> loanIds = new HashMap<>();

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzBankMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("bank.title"))
        ));
    }

    public PlotzBankMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(54));
    }

    private PlotzBankMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        loanIds.clear();

        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        box.setItem(4, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.tr("bank.title")));

        List<LoanManager.LoanEntry> loans = LoanManager.getVisibleLoans(viewer.getUUID(), viewer.hasPermissions(2));
        int slot = 0;
        for (LoanManager.LoanEntry loan : loans) {
            if (slot >= 45) break;

            String lender = loan.lenderName().isBlank()
                ? LanguageManager.tr("bank.target.server")
                : loan.lenderName();

            box.setItem(slot, MenuUtil.named(
                Items.PAPER,
                "§e#" + loan.id() + " §7| $" + loan.principal() + " | " + loan.status().name(),
                List.of(
                    LanguageManager.tr("bank.detail.borrower") + loan.borrowerName(),
                    LanguageManager.tr("bank.detail.lender") + lender
                )
            ));
            loanIds.put(slot, loan.id());
            slot++;
        }

        box.setItem(45, MenuUtil.playerInfoHead(viewer));
        box.setItem(49, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
        box.setItem(53, MenuUtil.named(Items.PAPER, LanguageManager.tr("bank.detail.info_hint")));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 49) {
            PlotzMainMenu.open(sp);
            return;
        }

        String loanId = loanIds.get(slotId);
        if (loanId != null) {
            PlotzBankLoanDetailMenu.open(sp, loanId);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}