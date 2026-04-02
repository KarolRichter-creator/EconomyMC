package de.karl_der_iii.economymc.menu;

import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.PayInputManager;
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

public class PlotzPayMenu extends ChestMenu {
    private final ServerPlayer viewer;
    private final SimpleContainer box;
    private final List<UUID> targets = new ArrayList<>();

    public static void open(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider(
            (containerId, inventory, p) -> new PlotzPayMenu(containerId, inventory, player),
            Component.literal(LanguageManager.tr("pay.menu.title"))
        ));
    }

    public PlotzPayMenu(int containerId, Inventory inventory, ServerPlayer viewer) {
        this(containerId, inventory, viewer, new SimpleContainer(54));
    }

    private PlotzPayMenu(int containerId, Inventory inventory, ServerPlayer viewer, SimpleContainer box) {
        super(MenuType.GENERIC_9x6, containerId, inventory, box, 6);
        this.viewer = viewer;
        this.box = box;
        refresh();
    }

    private void refresh() {
        targets.clear();

        for (int i = 0; i < box.getContainerSize(); i++) {
            box.setItem(i, MenuUtil.named(Items.GRAY_STAINED_GLASS_PANE, " "));
        }

        List<ServerPlayer> online = new ArrayList<>(viewer.server.getPlayerList().getPlayers());
        online.removeIf(p -> p.getUUID().equals(viewer.getUUID()));

        int slot = 10;
        for (ServerPlayer target : online) {
            if (slot % 9 == 8) {
                slot++;
            }
            if (slot >= 44) {
                break;
            }

            box.setItem(slot, MenuUtil.named(
                Items.PLAYER_HEAD,
                LanguageManager.format("pay.menu.player", target.getGameProfile().getName())
            ));
            targets.add(target.getUUID());
            slot++;
        }

        box.setItem(45, MenuUtil.playerInfoHead(viewer));
        box.setItem(49, MenuUtil.named(Items.BARRIER, LanguageManager.tr("common.back")));
        box.setItem(53, MenuUtil.named(Items.GOLD_INGOT, LanguageManager.tr("pay.menu.info")));
        broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }

        if (slotId == 49) {
            PlotzMainMenu.open(sp);
            return;
        }

        int index = slotIdToTargetIndex(slotId);
        if (index >= 0 && index < targets.size()) {
            UUID targetId = targets.get(index);
            ServerPlayer target = sp.server.getPlayerList().getPlayer(targetId);
            if (target != null) {
                PayInputManager.start(sp, targetId, target.getGameProfile().getName());
            }
        }
    }

    private int slotIdToTargetIndex(int slotId) {
        int count = -1;
        for (int slot = 10; slot < 44; slot++) {
            if (slot % 9 == 8) {
                continue;
            }
            count++;
            if (slot == slotId) {
                return count;
            }
        }
        return -1;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}