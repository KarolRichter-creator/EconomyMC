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
        TOOLS("server.shop.category.tools", Items.IRON_PICKAXE),
        ARMOR("server.shop.category.armor", Items.IRON_CHESTPLATE),
        BLOCKS("server.shop.category.blocks", Items.BRICKS),
        ICE("server.shop.category.ice", Items.PACKED_ICE),
        FOOD("server.shop.category.food", Items.COOKED_BEEF),
        FARMING("server.shop.category.farming", Items.WHEAT),
        REDSTONE("server.shop.category.redstone", Items.REDSTONE),
        DECORATION("server.shop.category.decoration", Items.LANTERN),
        ORES("server.shop.category.ores", Items.IRON_INGOT),
        MOBDROPS("server.shop.category.mobdrops", Items.STRING),
        NETHER("server.shop.category.nether", Items.NETHERRACK);

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

    public record Entry(String id, Category category, Item item, int count, int basePrice) {}

    private static final List<Entry> ENTRIES = List.of(
        new Entry("iron_pickaxe", Category.TOOLS, Items.IRON_PICKAXE, 1, 240),
        new Entry("iron_shovel", Category.TOOLS, Items.IRON_SHOVEL, 1, 120),
        new Entry("iron_axe", Category.TOOLS, Items.IRON_AXE, 1, 220),
        new Entry("bucket", Category.TOOLS, Items.BUCKET, 1, 90),
        new Entry("shears", Category.TOOLS, Items.SHEARS, 1, 80),
        new Entry("flint_and_steel", Category.TOOLS, Items.FLINT_AND_STEEL, 1, 95),

        new Entry("iron_helmet", Category.ARMOR, Items.IRON_HELMET, 1, 220),
        new Entry("iron_chestplate", Category.ARMOR, Items.IRON_CHESTPLATE, 1, 360),
        new Entry("iron_leggings", Category.ARMOR, Items.IRON_LEGGINGS, 1, 300),
        new Entry("iron_boots", Category.ARMOR, Items.IRON_BOOTS, 1, 180),
        new Entry("shield", Category.ARMOR, Items.SHIELD, 1, 140),

        new Entry("stone", Category.BLOCKS, Items.STONE, 64, 90),
        new Entry("glass", Category.BLOCKS, Items.GLASS, 64, 110),
        new Entry("bricks", Category.BLOCKS, Items.BRICKS, 64, 150),
        new Entry("oak_planks", Category.BLOCKS, Items.OAK_PLANKS, 64, 85),
        new Entry("spruce_planks", Category.BLOCKS, Items.SPRUCE_PLANKS, 64, 85),
        new Entry("birch_planks", Category.BLOCKS, Items.BIRCH_PLANKS, 64, 85),
        new Entry("cobblestone", Category.BLOCKS, Items.COBBLESTONE, 64, 70),
        new Entry("smooth_stone", Category.BLOCKS, Items.SMOOTH_STONE, 64, 120),
        new Entry("stone_bricks", Category.BLOCKS, Items.STONE_BRICKS, 64, 130),

        new Entry("packed_ice", Category.ICE, Items.PACKED_ICE, 32, 200),
        new Entry("blue_ice", Category.ICE, Items.BLUE_ICE, 16, 240),
        new Entry("ice", Category.ICE, Items.ICE, 32, 110),

        new Entry("bread", Category.FOOD, Items.BREAD, 16, 55),
        new Entry("cooked_beef", Category.FOOD, Items.COOKED_BEEF, 16, 95),
        new Entry("cooked_chicken", Category.FOOD, Items.COOKED_CHICKEN, 16, 80),
        new Entry("golden_carrot", Category.FOOD, Items.GOLDEN_CARROT, 16, 145),
        new Entry("apple", Category.FOOD, Items.APPLE, 16, 50),
        new Entry("baked_potato", Category.FOOD, Items.BAKED_POTATO, 16, 65),
        new Entry("pumpkin_pie", Category.FOOD, Items.PUMPKIN_PIE, 8, 95),

        new Entry("wheat", Category.FARMING, Items.WHEAT, 32, 55),
        new Entry("potato", Category.FARMING, Items.POTATO, 32, 55),
        new Entry("carrot", Category.FARMING, Items.CARROT, 32, 60),
        new Entry("melon_seeds", Category.FARMING, Items.MELON_SEEDS, 16, 50),
        new Entry("pumpkin_seeds", Category.FARMING, Items.PUMPKIN_SEEDS, 16, 50),
        new Entry("sugar_cane", Category.FARMING, Items.SUGAR_CANE, 32, 65),

        new Entry("redstone", Category.REDSTONE, Items.REDSTONE, 32, 120),
        new Entry("repeater", Category.REDSTONE, Items.REPEATER, 8, 140),
        new Entry("comparator", Category.REDSTONE, Items.COMPARATOR, 8, 165),
        new Entry("observer", Category.REDSTONE, Items.OBSERVER, 8, 200),
        new Entry("piston", Category.REDSTONE, Items.PISTON, 8, 175),
        new Entry("sticky_piston", Category.REDSTONE, Items.STICKY_PISTON, 8, 210),
        new Entry("hopper", Category.REDSTONE, Items.HOPPER, 8, 260),

        new Entry("lantern", Category.DECORATION, Items.LANTERN, 16, 140),
        new Entry("chain", Category.DECORATION, Items.CHAIN, 16, 110),
        new Entry("flower_pot", Category.DECORATION, Items.FLOWER_POT, 16, 70),
        new Entry("bookshelf", Category.DECORATION, Items.BOOKSHELF, 16, 180),
        new Entry("painting", Category.DECORATION, Items.PAINTING, 8, 90),
        new Entry("item_frame", Category.DECORATION, Items.ITEM_FRAME, 8, 110),

        new Entry("coal", Category.ORES, Items.COAL, 32, 85),
        new Entry("iron_ingot", Category.ORES, Items.IRON_INGOT, 16, 180),
        new Entry("copper_ingot", Category.ORES, Items.COPPER_INGOT, 16, 150),
        new Entry("gold_ingot", Category.ORES, Items.GOLD_INGOT, 16, 240),
        new Entry("lapis_lazuli", Category.ORES, Items.LAPIS_LAZULI, 16, 170),
        new Entry("diamond", Category.ORES, Items.DIAMOND, 8, 600),
        new Entry("emerald", Category.ORES, Items.EMERALD, 8, 520),

        new Entry("string", Category.MOBDROPS, Items.STRING, 16, 95),
        new Entry("bone", Category.MOBDROPS, Items.BONE, 16, 95),
        new Entry("gunpowder", Category.MOBDROPS, Items.GUNPOWDER, 16, 140),
        new Entry("rotten_flesh", Category.MOBDROPS, Items.ROTTEN_FLESH, 16, 40),
        new Entry("ender_pearl", Category.MOBDROPS, Items.ENDER_PEARL, 8, 220),
        new Entry("slime_ball", Category.MOBDROPS, Items.SLIME_BALL, 8, 180),

        new Entry("netherrack", Category.NETHER, Items.NETHERRACK, 64, 75),
        new Entry("soul_sand", Category.NETHER, Items.SOUL_SAND, 32, 120),
        new Entry("soul_soil", Category.NETHER, Items.SOUL_SOIL, 32, 120),
        new Entry("nether_bricks", Category.NETHER, Items.NETHER_BRICKS, 32, 170),
        new Entry("quartz", Category.NETHER, Items.QUARTZ, 16, 190),
        new Entry("glowstone_dust", Category.NETHER, Items.GLOWSTONE_DUST, 16, 150)
    );

    private ServerShopManager() {}

    public static List<Category> categories() {
        return List.of(Category.values());
    }

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
        return new ItemStack(entry.item(), entry.count());
    }

    public static String entryNameKey(Entry entry) {
        return "server.shop.item." + entry.id().toLowerCase(Locale.ROOT);
    }

    public static int tax(Entry entry) {
        return TreasuryManager.calculateTax(entry.basePrice());
    }

    public static int total(Entry entry) {
        return TreasuryManager.calculateTotalWithTax(entry.basePrice());
    }

    public static boolean buy(ServerPlayer player, Entry entry) {
        int total = total(entry);
        if (!BalanceManager.removeBalance(player.getUUID(), total)) {
            return false;
        }

        ItemStack purchased = new ItemStack(entry.item(), entry.count());
        if (!player.getInventory().add(purchased.copy())) {
            player.drop(purchased.copy(), false);
        }

        TreasuryManager.addTreasury(total);
        TransactionHistoryManager.add(
            player.getUUID(),
            LanguageManager.format("history.server_shop.buy", LanguageManager.tr(entryNameKey(entry)), total)
        );
        TransactionHistoryManager.addTreasury(
            LanguageManager.format("history.server_shop.sell", LanguageManager.tr(entryNameKey(entry)), total)
        );
        ScoreboardManager.update(player.server);
        return true;
    }
}
