package de.karl_der_iii.economymc.service;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ServerShopManager {
    public enum Category {
        ARMOR("server.shop.category.armor", Items.IRON_CHESTPLATE),
        BLOCKS("server.shop.category.blocks", Items.BRICKS),
        FOOD("server.shop.category.food", Items.COOKED_BEEF);

        private final String translationKey;
        private final Item icon;

        Category(String translationKey, Item icon) {
            this.translationKey = translationKey;
            this.icon = icon;
        }

        public String translationKey() {
            return translationKey;
        }

        public Item icon() {
            return icon;
        }
    }

    public record Entry(String id, Category category, Item item, int count, int price) {}

    private static final List<Entry> ENTRIES = List.of(
        new Entry("iron_helmet", Category.ARMOR, Items.IRON_HELMET, 1, 220),
        new Entry("iron_chestplate", Category.ARMOR, Items.IRON_CHESTPLATE, 1, 360),
        new Entry("iron_leggings", Category.ARMOR, Items.IRON_LEGGINGS, 1, 300),
        new Entry("iron_boots", Category.ARMOR, Items.IRON_BOOTS, 1, 180),
        new Entry("shield", Category.ARMOR, Items.SHIELD, 1, 140),

        new Entry("stone", Category.BLOCKS, Items.STONE, 64, 90),
        new Entry("glass", Category.BLOCKS, Items.GLASS, 64, 110),
        new Entry("bricks", Category.BLOCKS, Items.BRICKS, 64, 150),
        new Entry("packed_ice", Category.BLOCKS, Items.PACKED_ICE, 32, 200),
        new Entry("blue_ice", Category.BLOCKS, Items.BLUE_ICE, 16, 240),

        new Entry("bread", Category.FOOD, Items.BREAD, 16, 55),
        new Entry("cooked_beef", Category.FOOD, Items.COOKED_BEEF, 16, 95),
        new Entry("cooked_chicken", Category.FOOD, Items.COOKED_CHICKEN, 16, 80),
        new Entry("golden_carrot", Category.FOOD, Items.GOLDEN_CARROT, 16, 145),
        new Entry("apple", Category.FOOD, Items.APPLE, 16, 50)
    );

    private ServerShopManager() {}

    public static List<Entry> getEntries(Category category) {
        List<Entry> result = new ArrayList<>();
        for (Entry entry : ENTRIES) {
            if (entry.category() == category) {
                result.add(entry);
            }
        }
        return result;
    }

    public static ItemStack createDisplayStack(Entry entry) {
        ItemStack stack = new ItemStack(entry.item(), entry.count());
        return stack;
    }

    public static String entryNameKey(Entry entry) {
        return "server.shop.item." + entry.id().toLowerCase(Locale.ROOT);
    }

    public static boolean buy(ServerPlayer player, Entry entry) {
        if (!BalanceManager.removeBalance(player.getUUID(), entry.price())) {
            return false;
        }

        ItemStack purchased = new ItemStack(entry.item(), entry.count());
        if (!player.getInventory().add(purchased.copy())) {
            player.drop(purchased.copy(), false);
        }

        TreasuryManager.addTreasury(entry.price());
        TransactionHistoryManager.add(
            player.getUUID(),
            LanguageManager.format("history.server_shop.buy", LanguageManager.tr(entryNameKey(entry)), entry.price())
        );
        TransactionHistoryManager.addTreasury(
            LanguageManager.format("history.server_shop.sell", LanguageManager.tr(entryNameKey(entry)), entry.price())
        );
        ScoreboardManager.update(player.server);
        return true;
    }
}