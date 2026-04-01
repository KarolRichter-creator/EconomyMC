package de.karl_der_iii.economymc.service;

import java.util.HashMap;
import java.util.Map;

public final class LanguageManager {
    private static final Map<String, Map<String, String>> LANG = new HashMap<>();

    static {
        Map<String, String> en = new HashMap<>();
        en.put("help.header", "§6EconomyMC Commands");
        en.put("help.plots", "§e/ec plots §7- opens the plots menu");
        en.put("help.shop", "§e/ec shop §7- opens the shop menu");
        en.put("help.jobs", "§e/ec jobs §7- opens the jobs menu");
        en.put("help.checks", "§e/ec checks §7- opens the checks menu");
        en.put("help.history", "§e/ec history §7- opens the payment history");
        en.put("help.daily", "§e/ec daily §7- claims the daily reward");
        en.put("help.pay", "§e/ec pay <player/treasury> <amount> §7- sends money");
        en.put("help.servermode", "§e/ec servermode §7- opens server mode");
        en.put("help.adminmode", "§e/ec adminmode §7- opens admin mode");
        en.put("help.admin", "§e/ec admin ... §7- capital and account management");
        en.put("help.language", "§7Languages: German, English, Polish, French, Spanish, Portuguese, Russian, Turkish, Chinese, Japanese");

        en.put("cmd.only_players", "Only players can use this command.");
        en.put("msg.shop_disabled", "§cShop is disabled by admin.");
        en.put("msg.jobs_disabled", "§cJobs are disabled by admin.");
        en.put("msg.checks_disabled", "§cChecks are disabled by admin.");
        en.put("msg.servermode_disabled", "§cServer mode is disabled by admin.");

        en.put("admin.language", "§7Language: English");
        en.put("admin.language.toggle", "§bSwitch language");

        en.put("plots.menu.title", "EconomyMC Plots");
        en.put("plots.position.capital", "§6Current Position: Capital Zone");
        en.put("plots.position.normal", "§7Current Position: Normal Zone");
        en.put("plots.buy.normal", "§eBuy Normal Claim Credits");
        en.put("plots.buy.capital", "§6Buy Capital Claim Credits");
        en.put("plots.create.sale", "§aCreate Sale Listing");
        en.put("plots.market", "§3Market Listings");
        en.put("plots.mine", "§bMy Plots");
        en.put("plots.sales", "§dMy Sales");
        en.put("plots.history", "§aOpen History");
        en.put("plots.buy.normal.fail", "§cYou do not have enough money for a normal claim credit.");
        en.put("plots.buy.normal.ok", "§aBought 1 normal claim credit.");
        en.put("plots.buy.capital.fail", "§cYou do not have enough money for a capital claim credit.");
        en.put("plots.buy.capital.ok", "§aBought 1 capital claim credit.");

        en.put("menu.player.balance", "§6Balance: $%d");
        en.put("menu.player.normal", "§7Normal Credits: %d");
        en.put("menu.player.capital", "§7Capital Credits: %d");

        en.put("jobs.menu.title", "EconomyMC Jobs");
        en.put("jobs.server.title", "EconomyMC Server Jobs");
        en.put("job.detail.title", "EconomyMC Job Details");

        en.put("checks.menu.title", "EconomyMC Checks");
        en.put("check.redeem.title", "EconomyMC Redeem Check");

        en.put("server.mode.title", "EconomyMC Server Mode");
        en.put("admin.mode.title", "EconomyMC Admin Mode");

        en.put("history.title", "EconomyMC History");
        en.put("history.empty", "§7No entries yet.");
        en.put("history.mine", "§bMy History");
        en.put("history.treasury", "§6Treasury History");
        en.put("history.open", "§aOpen History");
        en.put("history.pay.sent", "§aPay to %s: $%d");
        en.put("history.pay.received", "§aPay from %s: $%d");
        en.put("history.daily", "§eDaily reward: $%d");
        en.put("history.check.create", "§dCreated check: $%d");
        en.put("history.check.redeem", "§dRedeemed check: $%d");
        en.put("history.job.reward", "§bJob payout: $%d");
        en.put("history.job.refund", "§6Job refund: $%d");
        en.put("history.treasury.tax", "§6Tax received: $%d");
        en.put("history.admin.set", "§cAdmin set balance: $%d");
        LANG.put("en_us", en);

        Map<String, String> de = new HashMap<>(en);
        de.put("help.header", "§6EconomyMC Befehle");
        de.put("help.plots", "§e/ec plots §7- öffnet das Grundstücksmenü");
        de.put("help.shop", "§e/ec shop §7- öffnet das Shop-Menü");
        de.put("help.jobs", "§e/ec jobs §7- öffnet das Jobs-Menü");
        de.put("help.checks", "§e/ec checks §7- öffnet das Checks-Menü");
        de.put("help.history", "§e/ec history §7- öffnet den Zahlungsverlauf");
        de.put("help.daily", "§e/ec daily §7- holt die tägliche Belohnung");
        de.put("help.pay", "§e/ec pay <Spieler/Treasury> <Betrag> §7- sendet Geld");
        de.put("help.servermode", "§e/ec servermode §7- öffnet den Servermodus");
        de.put("help.adminmode", "§e/ec adminmode §7- öffnet den Adminmodus");
        de.put("help.admin", "§e/ec admin ... §7- Hauptstadt und Kontoverwaltung");
        de.put("help.language", "§7Sprachen: Deutsch, Englisch, Polnisch, Französisch, Spanisch, Portugiesisch, Russisch, Türkisch, Chinesisch, Japanisch");

        de.put("cmd.only_players", "Nur Spieler können diesen Befehl benutzen.");
        de.put("msg.shop_disabled", "§cShop ist vom Admin deaktiviert.");
        de.put("msg.jobs_disabled", "§cJobs sind vom Admin deaktiviert.");
        de.put("msg.checks_disabled", "§cChecks sind vom Admin deaktiviert.");
        de.put("msg.servermode_disabled", "§cServer-Modus ist vom Admin deaktiviert.");

        de.put("admin.language", "§7Sprache: Deutsch");
        de.put("admin.language.toggle", "§bSprache wechseln");

        de.put("plots.menu.title", "EconomyMC Grundstücke");
        de.put("plots.position.capital", "§6Aktuelle Position: Hauptstadt-Zone");
        de.put("plots.position.normal", "§7Aktuelle Position: Normale Zone");
        de.put("plots.buy.normal", "§eNormale Claim-Credits kaufen");
        de.put("plots.buy.capital", "§6Hauptstadt-Claim-Credits kaufen");
        de.put("plots.create.sale", "§aVerkaufsangebot erstellen");
        de.put("plots.market", "§3Marktangebote");
        de.put("plots.mine", "§bMeine Grundstücke");
        de.put("plots.sales", "§dMeine Verkäufe");
        de.put("plots.history", "§aVerlauf öffnen");
        de.put("plots.buy.normal.fail", "§cDu hast nicht genug Geld für einen normalen Claim-Credit.");
        de.put("plots.buy.normal.ok", "§a1 normaler Claim-Credit gekauft.");
        de.put("plots.buy.capital.fail", "§cDu hast nicht genug Geld für einen Hauptstadt-Claim-Credit.");
        de.put("plots.buy.capital.ok", "§a1 Hauptstadt-Claim-Credit gekauft.");

        de.put("menu.player.balance", "§6Kontostand: $%d");
        de.put("menu.player.normal", "§7Normale Credits: %d");
        de.put("menu.player.capital", "§7Hauptstadt-Credits: %d");

        de.put("jobs.menu.title", "EconomyMC Jobs");
        de.put("jobs.server.title", "EconomyMC Server-Jobs");
        de.put("job.detail.title", "EconomyMC Jobdetails");

        de.put("checks.menu.title", "EconomyMC Checks");
        de.put("check.redeem.title", "EconomyMC Check einlösen");

        de.put("server.mode.title", "EconomyMC Servermodus");
        de.put("admin.mode.title", "EconomyMC Adminmodus");

        de.put("history.title", "EconomyMC Verlauf");
        de.put("history.empty", "§7Noch keine Einträge vorhanden.");
        de.put("history.mine", "§bMein Verlauf");
        de.put("history.treasury", "§6Treasury-Verlauf");
        de.put("history.open", "§aVerlauf öffnen");
        de.put("history.pay.sent", "§aÜberweisung an %s: $%d");
        de.put("history.pay.received", "§aÜberweisung von %s: $%d");
        de.put("history.daily", "§eTägliche Belohnung: $%d");
        de.put("history.check.create", "§dCheck erstellt: $%d");
        de.put("history.check.redeem", "§dCheck eingelöst: $%d");
        de.put("history.job.reward", "§bJob-Auszahlung: $%d");
        de.put("history.job.refund", "§6Job-Rückzahlung: $%d");
        de.put("history.treasury.tax", "§6Steuer erhalten: $%d");
        de.put("history.admin.set", "§cAdmin setzte Guthaben: $%d");
        LANG.put("de_de", de);

        Map<String, String> pl = new HashMap<>(en);
        pl.put("admin.language", "§7Język: Polski");
        pl.put("plots.menu.title", "EconomyMC Działki");
        pl.put("plots.position.capital", "§6Aktualna pozycja: Strefa stolicy");
        pl.put("plots.position.normal", "§7Aktualna pozycja: Zwykła strefa");
        pl.put("plots.buy.normal", "§eKup zwykłe kredyty działki");
        pl.put("plots.buy.capital", "§6Kup kredyty działki stolicy");
        pl.put("plots.create.sale", "§aUtwórz ofertę sprzedaży");
        pl.put("plots.market", "§3Oferty rynku");
        pl.put("plots.mine", "§bMoje działki");
        pl.put("plots.sales", "§dMoje sprzedaże");
        pl.put("plots.history", "§aOtwórz historię");
        LANG.put("pl_pl", pl);

        Map<String, String> fr = new HashMap<>(en);
        fr.put("admin.language", "§7Langue : Français");
        fr.put("plots.menu.title", "EconomyMC Parcelles");
        fr.put("plots.position.capital", "§6Position actuelle : Zone capitale");
        fr.put("plots.position.normal", "§7Position actuelle : Zone normale");
        fr.put("plots.buy.normal", "§eAcheter des crédits de claim normaux");
        fr.put("plots.buy.capital", "§6Acheter des crédits de claim capitale");
        fr.put("plots.create.sale", "§aCréer une offre de vente");
        fr.put("plots.market", "§3Offres du marché");
        fr.put("plots.mine", "§bMes parcelles");
        fr.put("plots.sales", "§dMes ventes");
        fr.put("plots.history", "§aOuvrir l'historique");
        LANG.put("fr_fr", fr);

        Map<String, String> es = new HashMap<>(en);
        es.put("admin.language", "§7Idioma: Español");
        es.put("plots.menu.title", "EconomyMC Parcelas");
        es.put("plots.position.capital", "§6Posición actual: Zona capital");
        es.put("plots.position.normal", "§7Posición actual: Zona normal");
        es.put("plots.buy.normal", "§eComprar créditos de claim normales");
        es.put("plots.buy.capital", "§6Comprar créditos de claim de capital");
        es.put("plots.create.sale", "§aCrear oferta de venta");
        es.put("plots.market", "§3Ofertas del mercado");
        es.put("plots.mine", "§bMis parcelas");
        es.put("plots.sales", "§dMis ventas");
        es.put("plots.history", "§aAbrir historial");
        LANG.put("es_es", es);

        Map<String, String> pt = new HashMap<>(en);
        pt.put("admin.language", "§7Idioma: Português");
        pt.put("plots.menu.title", "EconomyMC Terrenos");
        LANG.put("pt_br", pt);

        Map<String, String> ru = new HashMap<>(en);
        ru.put("admin.language", "§7Язык: Русский");
        ru.put("plots.menu.title", "EconomyMC Участки");
        LANG.put("ru_ru", ru);

        Map<String, String> tr = new HashMap<>(en);
        tr.put("admin.language", "§7Dil: Türkçe");
        tr.put("plots.menu.title", "EconomyMC Arsalar");
        LANG.put("tr_tr", tr);

        Map<String, String> zh = new HashMap<>(en);
        zh.put("admin.language", "§7语言: 中文");
        zh.put("plots.menu.title", "EconomyMC 地块");
        LANG.put("zh_cn", zh);

        Map<String, String> ja = new HashMap<>(en);
        ja.put("admin.language", "§7言語: 日本語");
        ja.put("plots.menu.title", "EconomyMC 区画");
        LANG.put("ja_jp", ja);
    }

    private LanguageManager() {}

    public static String tr(String key) {
        return tr(AdminSettingsManager.language(), key);
    }

    public static String tr(String lang, String key) {
        Map<String, String> source = LANG.getOrDefault(lang, LANG.get("en_us"));
        return source.getOrDefault(key, LANG.get("en_us").getOrDefault(key, key));
    }

    public static String format(String key, Object... args) {
        return String.format(tr(key), args);
    }

    public static String currentLanguageLabel() {
        return tr("admin.language");
    }
}