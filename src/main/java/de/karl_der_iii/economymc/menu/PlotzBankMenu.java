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
    private final Map<Integer, String> loanIdsBySlot = new HashMap<>();

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
        loanIdsBySlot.clear();

        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        box.setItem(10, MenuUtil.named(Items.GOLD_BLOCK, "§6" + LanguageManager.tr("bank.target.server")));
        box.setItem(11, MenuUtil.named(Items.PLAYER_HEAD, "§b" + LanguageManager.tr("bank.target.player")));
        box.setItem(12, MenuUtil.named(Items.BOOK, "§e" + LanguageManager.tr("bank.target.all")));

        box.setItem(14, MenuUtil.named(Items.PAPER, LanguageManager.tr("bank.command.request")));
        box.setItem(15, MenuUtil.named(Items.EMERALD, LanguageManager.tr("bank.command.offer")));
        box.setItem(16, MenuUtil.named(Items.LIME_CONCRETE, LanguageManager.tr("bank.command.accept")));
        box.setItem(17, MenuUtil.named(Items.CHEST, LanguageManager.tr("bank.command.repay")));

        List<LoanManager.LoanEntry> loans = LoanManager.getVisibleLoans(viewer.getUUID(), viewer.hasPermissions(2));
        int slot = 27;

        for (LoanManager.LoanEntry loan : loans) {
            if (slot >= 45) break;

            String lender = loan.lenderName().isBlank()
                ? LanguageManager.tr("bank.target.server")
                : loan.lenderName();

            box.setItem(slot, MenuUtil.named(
                Items.PAPER,
                "§e#" + loan.id()
                    + " §7| $" + loan.principal()
                    + " | " + loan.status().name()
                    + " | " + loan.borrowerName()
                    + " -> " + lender
            ));
            loanIdsBySlot.put(slot, loan.id());
            slot++;
        }

        box.setItem(49, MenuUtil.named(Items.BARRIER, "§cBack"));
        MenuUtil.putPlayerInfoHead(box, viewer, 45);
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == 49) {
            PlotzMainMenu.open(sp);
            return;
        }

        if (slotId == 10) {
            sp.closeContainer();
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.request")));
            sp.sendSystemMessage(Component.literal("§e/ec bank request server <amount> <days>"));
            return;
        }

        if (slotId == 11) {
            sp.closeContainer();
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.request")));
            sp.sendSystemMessage(Component.literal("§e/ec bank request player <name> <amount> <days>"));
            return;
        }

        if (slotId == 12) {
            sp.closeContainer();
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.request")));
            sp.sendSystemMessage(Component.literal("§e/ec bank request all <amount> <days>"));
            return;
        }

        String loanId = loanIdsBySlot.get(slotId);
        if (loanId != null) {
            sp.closeContainer();
            sp.sendSystemMessage(Component.literal("§eLoan ID: " + loanId));
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.offer")));
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.accept")));
            sp.sendSystemMessage(Component.literal("§7" + LanguageManager.tr("bank.command.repay")));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}