package de.karol.plotz.menu;

import de.karol.plotz.data.PlotzStore;
import de.karol.plotz.service.PlotzLogic;
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

import java.util.UUID;

public class PlotzMainMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzMainMenu(containerId, inventory, player),
            Component.literal("Plotz")
        ));
    }

    public PlotzMainMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(27));
    }

    private PlotzMainMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x3, containerId, inventory, box, 3);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, ItemStack.EMPTY);
        }

        UUID id = viewer.getUUID();
        boolean capitalHere = PlotzLogic.isCapital(viewer.blockPosition());

        box.setItem(11, MenuUtil.named(
            Items.BOOK,
            "§eNormale Claim-Chunks kaufen §7(" + PlotzStore.getNormalCredits(id) + " | " + PlotzLogic.NORMAL_CHUNK_PRICE + "$)"
        ));

        box.setItem(13, MenuUtil.named(
            Items.ENCHANTED_BOOK,
            "§6Hauptstadt-Claim-Chunks kaufen §7(" + PlotzStore.getCapitalCredits(id) + " | " + PlotzLogic.CAPITAL_CHUNK_PRICE + "$)"
        ));

        box.setItem(15, MenuUtil.named(
            Items.EMERALD,
            "§aNeuen Verkauf anlegen"
        ));

        box.setItem(19, MenuUtil.named(
            Items.COMPASS,
            capitalHere ? "§6Aktuelle Position: Hauptstadt" : "§7Aktuelle Position: Normal"
        ));

        box.setItem(20, MenuUtil.named(
            Items.MAP,
            "§bMein Besitz §7(" + PlotzStore.getOwnedPlots(id).size() + ")"
        ));

        box.setItem(22, MenuUtil.named(
            Items.WRITABLE_BOOK,
            "§dMeine Verkäufe §7(" + PlotzStore.getListingsBySeller(id).size() + ")"
        ));

        box.setItem(24, MenuUtil.named(
            Items.CHEST,
            "§3Marktangebote §7(" + PlotzStore.getListings().size() + ")"
        ));

        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        if (slotId == 11) {
            if (PlotzLogic.tryCharge(sp, PlotzLogic.NORMAL_CHUNK_PRICE)) {
                PlotzStore.addNormalCredit(sp.getUUID(), 1);
                sp.sendSystemMessage(Component.literal("§a1 normaler Claim-Chunk gekauft."));
                refresh();
            } else {
                sp.sendSystemMessage(Component.literal("§cNicht genug Geld."));
            }
            return;
        }

        if (slotId == 13) {
            if (PlotzLogic.tryCharge(sp, PlotzLogic.CAPITAL_CHUNK_PRICE)) {
                PlotzStore.addCapitalCredit(sp.getUUID(), 1);
                sp.sendSystemMessage(Component.literal("§a1 Hauptstadt-Claim-Chunk gekauft."));
                refresh();
            } else {
                sp.sendSystemMessage(Component.literal("§cNicht genug Geld."));
            }
            return;
        }

        if (slotId == 15) {
            boolean capital = PlotzLogic.isCapital(sp.blockPosition());

            PlotzStore.addListing(new PlotzStore.Listing(
                java.util.UUID.randomUUID().toString(),
                sp.getUUID(),
                sp.getGameProfile().getName(),
                "Grundstück von " + sp.getGameProfile().getName(),
                capital ? 8000 : 5000,
                capital,
                1,
                "X=" + sp.blockPosition().getX() + " Z=" + sp.blockPosition().getZ(),
                capital ? "Grundstück in der Hauptstadt" : "Grundstück außerhalb der Hauptstadt",
                "Einfacher Startpreis",
                "Noch keine genaue Bauwert-Prüfung"
            ));

            sp.sendSystemMessage(Component.literal("§aEin einfacher Verkaufsentwurf wurde erstellt und in den Markt gelegt."));
            refresh();
            return;
        }

        if (slotId == 20) {
            PlotzMyPlotsMenu.open(sp);
            return;
        }

        if (slotId == 22) {
            PlotzMySalesMenu.open(sp);
            return;
        }

        if (slotId == 24) {
            PlotzMarketMenu.open(sp);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}