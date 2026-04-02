package de.karl_der_iii.economymc.service;

import java.util.HashMap;
import java.util.Map;

public final class LanguageManager {
    private static final Map<String, Map<String, String>> LANG = new HashMap<>();
    private static final Map<String, String> LANGUAGE_NAMES = new HashMap<>();

    static {
        LANGUAGE_NAMES.put("de_de", "Deutsch");
        LANGUAGE_NAMES.put("en_us", "English");
        LANGUAGE_NAMES.put("pl_pl", "Polski");
        LANGUAGE_NAMES.put("fr_fr", "Français");
        LANGUAGE_NAMES.put("es_es", "Español");
        LANGUAGE_NAMES.put("pt_br", "Português");
        LANGUAGE_NAMES.put("ru_ru", "Русский");
        LANGUAGE_NAMES.put("tr_tr", "Türkçe");
        LANGUAGE_NAMES.put("zh_cn", "中文");
        LANGUAGE_NAMES.put("ja_jp", "日本語");

        Map<String, String> en = new HashMap<>();

        en.put("help.header", "§6EconomyMC Commands");
        en.put("help.plots", "§e/ec §7- opens the main menu");
        en.put("help.shop", "§7Shop is in the main menu");
        en.put("help.jobs", "§7Jobs are in the main menu");
        en.put("help.checks", "§7Checks are in the main menu");
        en.put("help.history", "§7History is in the main menu");
        en.put("help.bank", "§7Bank is in the main menu");
        en.put("help.daily", "§7Daily reward is in the main menu");
        en.put("help.pay", "§7Pay is in the main menu");
        en.put("help.servermode", "§7Server mode is in the main menu");
        en.put("help.adminmode", "§7Admin mode is in the main menu");
        en.put("help.admin", "§7Admin functions are in the main menu");
        en.put("help.language", "§7Languages: German, English, Polish, French, Spanish, Portuguese, Russian, Turkish, Chinese, Japanese");

        en.put("cmd.only_players", "Only players can use this command.");
        en.put("msg.shop_disabled", "§cShop is disabled by admin.");
        en.put("msg.jobs_disabled", "§cJobs are disabled by admin.");
        en.put("msg.checks_disabled", "§cChecks are disabled by admin.");
        en.put("msg.servermode_disabled", "§cServer mode is disabled by admin.");

        en.put("common.close", "§cClose");
        en.put("common.back", "§cBack");
        en.put("common.next", "§7Next Page");
        en.put("common.previous", "§7Previous Page");
        en.put("common.confirm", "§aConfirm");
        en.put("common.cancel", "§cCancel");
        en.put("common.create", "§aCreate");
        en.put("common.edit", "§eEdit");
        en.put("common.price", "§6Price");
        en.put("common.description", "§7Description");
        en.put("common.status", "§7Status");
        en.put("common.page", "§7Page ");
        en.put("common.seller", "§7Seller");
        en.put("common.amount", "§7Amount");
        en.put("common.click_to_view", "§7Click to view");
        en.put("common.publish", "§bPublish");
        en.put("common.sell", "§aSell");
        en.put("common.treasury", "Treasury");

        en.put("main.menu.title", "EconomyMC");
        en.put("main.plots", "§aPlots");
        en.put("main.shop", "§eShop");
        en.put("main.jobs", "§bJobs");
        en.put("main.checks", "§dChecks");
        en.put("main.bank", "§6Bank");
        en.put("main.history", "§7History");
        en.put("main.daily", "§aDaily Reward");
        en.put("main.pay", "§6Pay");
        en.put("main.servermode", "§cServer Mode");
        en.put("main.servermode.disabled", "§8Server Mode Disabled");
        en.put("main.adminmode", "§4Admin Mode");

        en.put("plots.menu.title", "EconomyMC Plots");
        en.put("plots.position.capital", "§6Current Position: Capital Zone");
        en.put("plots.position.normal", "§7Current Position: Normal Zone");
        en.put("plots.buy.normal", "§eBuy Normal Claim Credits");
        en.put("plots.buy.capital", "§6Buy Capital Claim Credits");
        en.put("plots.create.sale", "§aCreate Sale Listing");
        en.put("plots.market", "§3Market Listings");
        en.put("plots.mine", "§bMy Plots");
        en.put("plots.sales", "§dMy Sales");
        en.put("plots.connector", "§7OpenPants Connect");
        en.put("plots.buy.normal.fail", "§cYou do not have enough money for a normal claim credit.");
        en.put("plots.buy.normal.ok", "§aBought 1 normal claim credit.");
        en.put("plots.buy.capital.fail", "§cYou do not have enough money for a capital claim credit.");
        en.put("plots.buy.capital.ok", "§aBought 1 capital claim credit.");

        en.put("admin.mode.title", "EconomyMC Admin Mode");
        en.put("admin.jobs", "Jobs");
        en.put("admin.checks", "Checks");
        en.put("admin.shop", "Shop");
        en.put("admin.plot_market", "Plot Market");
        en.put("admin.server_mode", "Server Mode");
        en.put("admin.min_tax", "Minimum Tax");
        en.put("admin.min_overdue", "Minimum Overdue Penalty");
        en.put("admin.min_cancel", "Minimum Cancel Penalty");
        en.put("admin.language", "Language");
        en.put("admin.language.previous", "§ePrevious Language");
        en.put("admin.language.next", "§eNext Language");
        en.put("admin.language.toggle", "§eChange Language");
        en.put("admin.on", "ON");
        en.put("admin.off", "OFF");

        en.put("jobs.menu.title", "EconomyMC Jobs");
        en.put("jobs.server.title", "EconomyMC Server Jobs");
        en.put("jobs.add", "§aCreate Job");
        en.put("jobs.create_hint", "§7Create only from this menu");
        en.put("jobs.status.open", "§aOpen");
        en.put("jobs.status.opens_at", "§eOpens at: ");
        en.put("jobs.status.in_progress", "§6In progress: ");
        en.put("jobs.status.completed", "§bCompleted");
        en.put("jobs.status.confirmed", "§aConfirmed");
        en.put("jobs.status.cancelled", "§cCancelled");
        en.put("jobs.status.failed", "§4Failed");

        en.put("shop.menu.title", "EconomyMC Shop");
        en.put("server.mode.title", "EconomyMC Server Mode");
        en.put("server.tax_minus", "§cTax -1%");
        en.put("server.tax_rate", "§7Tax Rate: ");
        en.put("server.tax_plus", "§aTax +1%");
        en.put("server.auto_tax", "Auto Tax");
        en.put("server.overdue_minus", "§cOverdue -1%");
        en.put("server.overdue_penalty", "§7Overdue Penalty: ");
        en.put("server.overdue_plus", "§aOverdue +1%");
        en.put("server.cancel_minus", "§cCancel -1%");
        en.put("server.cancel_penalty", "§7Cancel Penalty: ");
        en.put("server.cancel_plus", "§aCancel +1%");
        en.put("server.days_minus", "§cDays -1");
        en.put("server.max_overdue_days", "§7Max Overdue Days: ");
        en.put("server.days_plus", "§aDays +1");
        en.put("server.start_hour_minus", "§cStart Hour -1");
        en.put("server.job_open_hour", "§7Job Open Hour: ");
        en.put("server.start_hour_plus", "§aStart Hour +1");
        en.put("server.create_job", "§aCreate Server Job");
        en.put("server.open_jobs", "§bOpen Server Jobs");
        en.put("server.treasury_balance", "§6Treasury: $");

        en.put("history.title", "EconomyMC History");
        en.put("history.empty", "§7No entries yet.");
        en.put("history.mine", "§bMy History");
        en.put("history.treasury", "§6Treasury History");
        en.put("history.daily", "§eDaily reward: $%d");
        en.put("history.pay.sent", "§aPay to %s: $%d");
        en.put("history.pay.received", "§aPay from %s: $%d");

        en.put("daily.already", "§cDaily already claimed for today. Come back in %dh %dm.");
        en.put("daily.claimed", "§aYou claimed your daily $100.");

        en.put("pay.menu.title", "EconomyMC Pay");
        en.put("pay.menu.player", "§ePay %s");
        en.put("pay.menu.info", "§7Select a player");
        en.put("pay.menu.enter_amount", "§eEnter the amount for %s in chat now.");
        en.put("pay.invalid_number", "§cThat is not a valid amount.");
        en.put("pay.target_offline", "§cThat player is no longer online.");
        en.put("pay.known_or_treasury", "§cOnly known players or Treasury can receive money.");
        en.put("pay.self", "§cYou cannot pay yourself.");
        en.put("pay.not_enough", "§cYou do not have enough money.");
        en.put("pay.sent", "§aYou paid $%d to %s.");
        en.put("pay.received", "§aYou received $%d from %s.");

        LANG.put("en_us", en);

        Map<String, String> de = new HashMap<>(en);
        de.put("help.header", "§6EconomyMC Befehle");
        de.put("help.plots", "§e/ec §7- öffnet das Hauptmenü");
        de.put("help.shop", "§7Shop ist im Hauptmenü");
        de.put("help.jobs", "§7Jobs sind im Hauptmenü");
        de.put("help.checks", "§7Checks sind im Hauptmenü");
        de.put("help.history", "§7Verlauf ist im Hauptmenü");
        de.put("help.bank", "§7Bank ist im Hauptmenü");
        de.put("help.daily", "§7Tägliche Belohnung ist im Hauptmenü");
        de.put("help.pay", "§7Bezahlen ist im Hauptmenü");
        de.put("help.servermode", "§7Servermodus ist im Hauptmenü");
        de.put("help.adminmode", "§7Adminmodus ist im Hauptmenü");
        de.put("help.admin", "§7Adminfunktionen sind im Hauptmenü");

        de.put("cmd.only_players", "Nur Spieler können diesen Befehl benutzen.");
        de.put("msg.shop_disabled", "§cShop ist vom Admin deaktiviert.");
        de.put("msg.jobs_disabled", "§cJobs sind vom Admin deaktiviert.");
        de.put("msg.checks_disabled", "§cChecks sind vom Admin deaktiviert.");
        de.put("msg.servermode_disabled", "§cServer-Modus ist vom Admin deaktiviert.");

        de.put("common.close", "§cSchließen");
        de.put("common.back", "§cZurück");
        de.put("common.next", "§7Nächste Seite");
        de.put("common.previous", "§7Vorherige Seite");
        de.put("common.confirm", "§aBestätigen");
        de.put("common.cancel", "§cAbbrechen");
        de.put("common.create", "§aErstellen");
        de.put("common.edit", "§eBearbeiten");
        de.put("common.price", "§6Preis");
        de.put("common.description", "§7Beschreibung");
        de.put("common.status", "§7Status");
        de.put("common.page", "§7Seite ");
        de.put("common.seller", "§7Verkäufer");
        de.put("common.amount", "§7Betrag");
        de.put("common.click_to_view", "§7Zum Anzeigen klicken");
        de.put("common.publish", "§bVeröffentlichen");
        de.put("common.sell", "§aVerkaufen");
        de.put("common.treasury", "Treasury");

        de.put("main.menu.title", "EconomyMC");
        de.put("main.plots", "§aGrundstücke");
        de.put("main.shop", "§eShop");
        de.put("main.jobs", "§bJobs");
        de.put("main.checks", "§dChecks");
        de.put("main.bank", "§6Bank");
        de.put("main.history", "§7Verlauf");
        de.put("main.daily", "§aTägliche Belohnung");
        de.put("main.pay", "§6Bezahlen");
        de.put("main.servermode", "§cServermodus");
        de.put("main.servermode.disabled", "§8Servermodus deaktiviert");
        de.put("main.adminmode", "§4Adminmodus");

        de.put("plots.menu.title", "EconomyMC Grundstücke");
        de.put("plots.position.capital", "§6Aktuelle Position: Hauptstadt-Zone");
        de.put("plots.position.normal", "§7Aktuelle Position: Normale Zone");
        de.put("plots.buy.normal", "§eNormale Claim-Credits kaufen");
        de.put("plots.buy.capital", "§6Hauptstadt-Claim-Credits kaufen");
        de.put("plots.create.sale", "§aVerkaufsangebot erstellen");
        de.put("plots.market", "§3Marktangebote");
        de.put("plots.mine", "§bMeine Grundstücke");
        de.put("plots.sales", "§dMeine Verkäufe");
        de.put("plots.connector", "§7OpenPants Connect");
        de.put("plots.buy.normal.fail", "§cDu hast nicht genug Geld für einen normalen Claim-Credit.");
        de.put("plots.buy.normal.ok", "§a1 normaler Claim-Credit gekauft.");
        de.put("plots.buy.capital.fail", "§cDu hast nicht genug Geld für einen Hauptstadt-Claim-Credit.");
        de.put("plots.buy.capital.ok", "§a1 Hauptstadt-Claim-Credit gekauft.");

        de.put("admin.mode.title", "EconomyMC Adminmodus");
        de.put("admin.jobs", "Jobs");
        de.put("admin.checks", "Checks");
        de.put("admin.shop", "Shop");
        de.put("admin.plot_market", "Grundstücksmarkt");
        de.put("admin.server_mode", "Servermodus");
        de.put("admin.min_tax", "Mindeststeuer");
        de.put("admin.min_overdue", "Mindest-Überzugsstrafe");
        de.put("admin.min_cancel", "Mindest-Abbruchstrafe");
        de.put("admin.language", "Sprache");
        de.put("admin.language.previous", "§eVorherige Sprache");
        de.put("admin.language.next", "§eNächste Sprache");
        de.put("admin.language.toggle", "§eSprache wechseln");
        de.put("admin.on", "AN");
        de.put("admin.off", "AUS");

        de.put("jobs.menu.title", "EconomyMC Jobs");
        de.put("jobs.server.title", "EconomyMC Server-Jobs");
        de.put("jobs.add", "§aJob erstellen");
        de.put("jobs.create_hint", "§7Erstellen nur in diesem Menü");
        de.put("jobs.status.open", "§aOffen");
        de.put("jobs.status.opens_at", "§eÖffnet um: ");
        de.put("jobs.status.in_progress", "§6In Bearbeitung: ");
        de.put("jobs.status.completed", "§bErledigt");
        de.put("jobs.status.confirmed", "§aBestätigt");
        de.put("jobs.status.cancelled", "§cAbgebrochen");
        de.put("jobs.status.failed", "§4Gescheitert");

        de.put("shop.menu.title", "EconomyMC Shop");
        de.put("server.mode.title", "EconomyMC Servermodus");
        de.put("server.tax_minus", "§cSteuer -1%");
        de.put("server.tax_rate", "§7Steuersatz: ");
        de.put("server.tax_plus", "§aSteuer +1%");
        de.put("server.auto_tax", "Automatische Steuer");
        de.put("server.overdue_minus", "§cÜberzug -1%");
        de.put("server.overdue_penalty", "§7Überzugsgebühr: ");
        de.put("server.overdue_plus", "§aÜberzug +1%");
        de.put("server.cancel_minus", "§cAbbruch -1%");
        de.put("server.cancel_penalty", "§7Abbruchgebühr: ");
        de.put("server.cancel_plus", "§aAbbruch +1%");
        de.put("server.days_minus", "§cTage -1");
        de.put("server.max_overdue_days", "§7Max. Überzugstage: ");
        de.put("server.days_plus", "§aTage +1");
        de.put("server.start_hour_minus", "§cStartstunde -1");
        de.put("server.job_open_hour", "§7Job-Startstunde: ");
        de.put("server.start_hour_plus", "§aStartstunde +1");
        de.put("server.create_job", "§aServer-Job erstellen");
        de.put("server.open_jobs", "§bServer-Jobs öffnen");
        de.put("server.treasury_balance", "§6Treasury: $");

        de.put("history.title", "EconomyMC Verlauf");
        de.put("history.empty", "§7Noch keine Einträge vorhanden.");
        de.put("history.mine", "§bMein Verlauf");
        de.put("history.treasury", "§6Treasury-Verlauf");
        de.put("history.daily", "§eTägliche Belohnung: $%d");
        de.put("history.pay.sent", "§aÜberweisung an %s: $%d");
        de.put("history.pay.received", "§aÜberweisung von %s: $%d");

        de.put("daily.already", "§cDaily für heute schon abgeholt. Komm in %dh %dm zurück.");
        de.put("daily.claimed", "§aDu hast dein tägliches $100 abgeholt.");

        de.put("pay.menu.title", "EconomyMC Bezahlen");
        de.put("pay.menu.player", "§eAn %s zahlen");
        de.put("pay.menu.info", "§7Spieler auswählen");
        de.put("pay.menu.enter_amount", "§eGib jetzt den Betrag für %s im Chat ein.");
        de.put("pay.invalid_number", "§cDas ist kein gültiger Betrag.");
        de.put("pay.target_offline", "§cDieser Spieler ist nicht mehr online.");
        de.put("pay.known_or_treasury", "§cNur bekannte Spieler oder das Treasury können Geld erhalten.");
        de.put("pay.self", "§cDu kannst dir nicht selbst Geld senden.");
        de.put("pay.not_enough", "§cDu hast nicht genug Geld.");
        de.put("pay.sent", "§aDu hast $%d an %s bezahlt.");
        de.put("pay.received", "§aDu hast $%d von %s erhalten.");

        LANG.put("de_de", de);

        LANG.put("pl_pl", new HashMap<>(en));
        LANG.put("fr_fr", new HashMap<>(en));
        LANG.put("es_es", new HashMap<>(en));
        LANG.put("pt_br", new HashMap<>(en));
        LANG.put("ru_ru", new HashMap<>(en));
        LANG.put("tr_tr", new HashMap<>(en));
        LANG.put("zh_cn", new HashMap<>(en));
        LANG.put("ja_jp", new HashMap<>(en));
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
        return tr("help.language");
    }

    public static String languageName(String code) {
        return LANGUAGE_NAMES.getOrDefault(code, code);
    }
}