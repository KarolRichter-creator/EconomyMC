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

import java.util.List;

public class PlotzBankLoanDetailMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final String loanId;

    public static void open(ServerPlayer player, String loanId) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzBankLoanDetailMenu(containerId, inventory, player, loanId),
            Component.literal(LanguageManager.tr("bank.detail.title"))
        ));
    }

    public PlotzBankLoanDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, String loanId) {
        this(containerId, inventory, viewer, new SimpleContainer(45), loanId);
    }

    private PlotzBankLoanDetailMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box, String loanId) {
        super(MenuType.GENERIC_9x5, containerId, inventory, box, 5);
        this.viewer = viewer;
        this.box = box;
        this.loanId = loanId;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        LoanManager.LoanEntry loan = LoanManager.getLoan(loanId);
        if (loan == null) {
            box.setItem(22, MenuUtil.named(Items.BARRIER, LanguageManager.tr("bank.not_found")));
            box.setItem(40, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
            broadcastChanges();
            return;
        }

        String lender = loan.lenderName().isBlank()
            ? LanguageManager.tr("bank.target.server")
            : loan.lenderName();

        box.setItem(4, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.tr("bank.detail.title")));
        box.setItem(11, MenuUtil.named(Items.PAPER, LanguageManager.tr("bank.loan_id") + ": " + loan.id()));
        box.setItem(13, MenuUtil.named(Items.EMERALD, LanguageManager.tr("bank.detail.amount") + loan.principal()));
        box.setItem(15, MenuUtil.named(Items.CLOCK, LanguageManager.tr("bank.detail.status") + loan.status().name()));

        box.setItem(20, MenuUtil.named(Items.PLAYER_HEAD, LanguageManager.tr("bank.detail.borrower") + loan.borrowerName()));
        box.setItem(24, MenuUtil.named(Items.PLAYER_HEAD, LanguageManager.tr("bank.detail.lender") + lender));

        box.setItem(36, MenuUtil.playerInfoHead(viewer));
        box.setItem(40, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));

        if (loan.borrowerId().equals(viewer.getUUID()) && loan.status() == LoanManager.LoanStatus.ACTIVE) {
            box.setItem(42, MenuUtil.named(Items.EMERALD, LanguageManager.tr("bank.command.repay")));
        }

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 40) {
            PlotzBankMenu.open(sp);
            return;
        }

        if (slotId == 42) {
            LoanManager.repayLoan(loanId, sp.getUUID());
            refresh();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}