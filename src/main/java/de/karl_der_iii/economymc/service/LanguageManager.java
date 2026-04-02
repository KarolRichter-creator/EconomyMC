package de.karl_der_iii.economymc.service;

import java.util.HashMap;
import java.util.Map;

public final class LanguageManager {
    private static final Map<String, Map<String, String>> LANG = new HashMap<>();

    static {
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

        en.put("menu.player.balance", "§6Balance: $%d");
        en.put("menu.player.normal", "§7Normal Credits: %d");
        en.put("menu.player.capital", "§7Capital Credits: %d");

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
        en.put("plots.buy.normal.fail", "§cYou do not have enough money for a normal claim credit.");
        en.put("plots.buy.normal.ok", "§aBought 1 normal claim credit.");
        en.put("plots.buy.capital.fail", "§cYou do not have enough money for a capital claim credit.");
        en.put("plots.buy.capital.ok", "§aBought 1 capital claim credit.");

        en.put("shop.menu.title", "EconomyMC Shop");
        en.put("shop.sell.title", "EconomyMC Sell Item");
        en.put("shop.bundle", "§dShop Bundle");
        en.put("shop.base_price", "§6Base Price: $");
        en.put("shop.tax", "§cTax: $");
        en.put("shop.total", "§aTotal: $");
        en.put("shop.stacks_inside", "§7Stacks inside: ");
        en.put("shop.total_items", "§7Total items: ");
        en.put("shop.and_more", "§8...and more");
        en.put("shop.sell.set_price", "§eSet Price");
        en.put("shop.sell.publish", "§aPublish");
        en.put("shop.sell.put_items_first", "§cPut items in first.");
        en.put("shop.sell.published", "§aShop listing published.");
        en.put("shop.input.enter_price", "§eEnter the shop price in chat now.");
        en.put("shop.input.no_draft", "§cNo shop draft selected.");
        en.put("shop.input.price_positive", "§cPrice must be above 0.");
        en.put("shop.input.price_set", "§aShop price set to $");
        en.put("shop.input.invalid_number", "§cThat is not a valid number.");

        en.put("checks.menu.title", "EconomyMC Checks");
        en.put("checks.create", "§aCreate Check");
        en.put("checks.entry", "%sCheck $%d - %s");
        en.put("checks.input.amount", "§eEnter the check amount in chat now.");
        en.put("checks.input.code", "§eEnter the check code in chat now.");
        en.put("checks.input.invalid_number", "§cThat is not a valid number.");
        en.put("checks.input.amount_positive", "§cAmount must be above 0.");
        en.put("checks.input.not_enough", "§cYou do not have enough money.");
        en.put("checks.input.created", "§aCheck created.");
        en.put("checks.input.invalid_code", "§cInvalid code or check already redeemed.");
        en.put("checks.input.redeemed_success", "§aCheck redeemed successfully.");

        en.put("jobs.menu.title", "EconomyMC Jobs");
        en.put("jobs.input.title", "§eEnter the job title in chat now.");
        en.put("jobs.input.server_title", "§eEnter the server job title in chat now.");
        en.put("jobs.input.description", "§eEnter the job description in chat now.");
        en.put("jobs.input.reward", "§eEnter the reward amount in chat now.");
        en.put("jobs.input.due_days", "§eEnter the due time in full days now.");
        en.put("jobs.input.invalid_number", "§cThat is not a valid number.");
        en.put("jobs.input.reward_positive", "§cReward must be above 0.");
        en.put("jobs.input.days_positive", "§cDue days must be above 0.");
        en.put("jobs.input.treasury_missing", "§cThe treasury does not have enough money for this server job.");
        en.put("jobs.input.player_missing_money", "§cYou do not have enough money to create this job.");
        en.put("jobs.input.created", "§aJob created.");

        en.put("server.mode.title", "EconomyMC Server Mode");
        en.put("server.treasury_balance", "§6Treasury: $");
        en.put("server.tax_minus", "§cTax -1%");
        en.put("server.tax_plus", "§aTax +1%");
        en.put("server.tax_rate", "§7Tax Rate: ");
        en.put("server.auto_tax", "Auto Tax");
        en.put("server.overdue_minus", "§cOverdue -1%");
        en.put("server.overdue_plus", "§aOverdue +1%");
        en.put("server.overdue_penalty", "§7Overdue Penalty: ");
        en.put("server.cancel_minus", "§cCancel -1%");
        en.put("server.cancel_plus", "§aCancel +1%");
        en.put("server.cancel_penalty", "§7Cancel Penalty: ");
        en.put("server.days_minus", "§cDays -1");
        en.put("server.days_plus", "§aDays +1");
        en.put("server.max_overdue_days", "§7Max Overdue Days: ");
        en.put("server.start_hour_minus", "§cStart Hour -1");
        en.put("server.start_hour_plus", "§aStart Hour +1");
        en.put("server.job_open_hour", "§7Job Open Hour: ");
        en.put("server.create_job", "§aCreate Server Job");
        en.put("server.open_jobs", "§bOpen Server Jobs");
        en.put("server.info", "§7Treasury, jobs and taxes");

        en.put("admin.auto", "Auto");
        en.put("admin.manual", "Manual");
        en.put("admin.on", "ON");
        en.put("admin.off", "OFF");

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

        en.put("bank.title", "EconomyMC Bank");
        en.put("bank.target.server", "Server");
        en.put("bank.target.player", "Player");
        en.put("bank.target.all", "All Players");
        en.put("bank.command.request", "Create Loan Request");
        en.put("bank.command.offer", "Create Loan Offer");
        en.put("bank.command.accept", "Accept Loan");
        en.put("bank.command.repay", "Repay Loan");
        en.put("bank.loan_id", "Loan ID");
        en.put("bank.invalid_target", "§cUnknown target player.");
        en.put("bank.not_enough_money", "§cYou do not have enough money.");
        en.put("bank.not_found", "§cLoan not found.");
        en.put("bank.request.created", "§aLoan request created.");
        en.put("bank.offer.created", "§aLoan offer created.");
        en.put("bank.accepted", "§aLoan accepted.");
        en.put("bank.repaid", "§aLoan repaid.");

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

        de.put("menu.player.balance", "§6Kontostand: $%d");
        de.put("menu.player.normal", "§7Normale Credits: %d");
        de.put("menu.player.capital", "§7Hauptstadt-Credits: %d");

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
        de.put("plots.buy.normal.fail", "§cDu hast nicht genug Geld für einen normalen Claim-Credit.");
        de.put("plots.buy.normal.ok", "§a1 normaler Claim-Credit gekauft.");
        de.put("plots.buy.capital.fail", "§cDu hast nicht genug Geld für einen Hauptstadt-Claim-Credit.");
        de.put("plots.buy.capital.ok", "§a1 Hauptstadt-Claim-Credit gekauft.");

        de.put("shop.menu.title", "EconomyMC Shop");
        de.put("shop.sell.title", "EconomyMC Gegenstand verkaufen");
        de.put("shop.bundle", "§dShop-Bündel");
        de.put("shop.base_price", "§6Grundpreis: $");
        de.put("shop.tax", "§cSteuer: $");
        de.put("shop.total", "§aGesamt: $");
        de.put("shop.stacks_inside", "§7Stacks enthalten: ");
        de.put("shop.total_items", "§7Gesamtanzahl Items: ");
        de.put("shop.and_more", "§8...und mehr");
        de.put("shop.sell.set_price", "§ePreis setzen");
        de.put("shop.sell.publish", "§aVeröffentlichen");
        de.put("shop.sell.put_items_first", "§cLege zuerst Items hinein.");
        de.put("shop.sell.published", "§aShop-Angebot veröffentlicht.");
        de.put("shop.input.enter_price", "§eGib jetzt den Shop-Preis im Chat ein.");
        de.put("shop.input.no_draft", "§cKein Shop-Entwurf ausgewählt.");
        de.put("shop.input.price_positive", "§cDer Preis muss über 0 liegen.");
        de.put("shop.input.price_set", "§aShop-Preis gesetzt auf $");
        de.put("shop.input.invalid_number", "§cDas ist keine gültige Zahl.");

        de.put("checks.menu.title", "EconomyMC Checks");
        de.put("checks.create", "§aCheck erstellen");
        de.put("checks.entry", "%sCheck $%d - %s");
        de.put("checks.input.amount", "§eGib jetzt den Check-Betrag im Chat ein.");
        de.put("checks.input.code", "§eGib jetzt den Check-Code im Chat ein.");
        de.put("checks.input.invalid_number", "§cDas ist keine gültige Zahl.");
        de.put("checks.input.amount_positive", "§cDer Betrag muss über 0 liegen.");
        de.put("checks.input.not_enough", "§cDu hast nicht genug Geld.");
        de.put("checks.input.created", "§aCheck erstellt.");
        de.put("checks.input.invalid_code", "§cUngültiger Code oder Check bereits eingelöst.");
        de.put("checks.input.redeemed_success", "§aCheck erfolgreich eingelöst.");

        de.put("jobs.menu.title", "EconomyMC Jobs");
        de.put("jobs.input.title", "§eGib jetzt den Jobtitel im Chat ein.");
        de.put("jobs.input.server_title", "§eGib jetzt den Server-Jobtitel im Chat ein.");
        de.put("jobs.input.description", "§eGib jetzt die Jobbeschreibung im Chat ein.");
        de.put("jobs.input.reward", "§eGib jetzt die Belohnung im Chat ein.");
        de.put("jobs.input.due_days", "§eGib jetzt die Fälligkeit in vollen Tagen ein.");
        de.put("jobs.input.invalid_number", "§cDas ist keine gültige Zahl.");
        de.put("jobs.input.reward_positive", "§cDie Belohnung muss über 0 liegen.");
        de.put("jobs.input.days_positive", "§cDie Tage müssen über 0 liegen.");
        de.put("jobs.input.treasury_missing", "§cDas Treasury hat nicht genug Geld für diesen Server-Job.");
        de.put("jobs.input.player_missing_money", "§cDu hast nicht genug Geld, um diesen Job zu erstellen.");
        de.put("jobs.input.created", "§aJob erstellt.");

        de.put("server.mode.title", "EconomyMC Servermodus");
        de.put("server.treasury_balance", "§6Treasury: $");
        de.put("server.tax_minus", "§cSteuer -1%");
        de.put("server.tax_plus", "§aSteuer +1%");
        de.put("server.tax_rate", "§7Steuersatz: ");
        de.put("server.auto_tax", "Automatische Steuer");
        de.put("server.overdue_minus", "§cÜberzug -1%");
        de.put("server.overdue_plus", "§aÜberzug +1%");
        de.put("server.overdue_penalty", "§7Überzugsgebühr: ");
        de.put("server.cancel_minus", "§cAbbruch -1%");
        de.put("server.cancel_plus", "§aAbbruch +1%");
        de.put("server.cancel_penalty", "§7Abbruchgebühr: ");
        de.put("server.days_minus", "§cTage -1");
        de.put("server.days_plus", "§aTage +1");
        de.put("server.max_overdue_days", "§7Max. Überzugstage: ");
        de.put("server.start_hour_minus", "§cStartstunde -1");
        de.put("server.start_hour_plus", "§aStartstunde +1");
        de.put("server.job_open_hour", "§7Job-Startstunde: ");
        de.put("server.create_job", "§aServer-Job erstellen");
        de.put("server.open_jobs", "§bServer-Jobs öffnen");
        de.put("server.info", "§7Treasury, Jobs und Steuern");

        de.put("admin.auto", "Auto");
        de.put("admin.manual", "Manuell");
        de.put("admin.on", "AN");
        de.put("admin.off", "AUS");

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

        de.put("bank.title", "EconomyMC Bank");
        de.put("bank.target.server", "Server");
        de.put("bank.target.player", "Spieler");
        de.put("bank.target.all", "Alle Spieler");
        de.put("bank.command.request", "Kreditanfrage erstellen");
        de.put("bank.command.offer", "Kreditangebot erstellen");
        de.put("bank.command.accept", "Kredit annehmen");
        de.put("bank.command.repay", "Kredit zurückzahlen");
        de.put("bank.loan_id", "Kredit-ID");
        de.put("bank.invalid_target", "§cUnbekannter Zielspieler.");
        de.put("bank.not_enough_money", "§cDu hast nicht genug Geld.");
        de.put("bank.not_found", "§cKredit nicht gefunden.");
        de.put("bank.request.created", "§aKreditanfrage erstellt.");
        de.put("bank.offer.created", "§aKreditangebot erstellt.");
        de.put("bank.accepted", "§aKredit angenommen.");
        de.put("bank.repaid", "§aKredit zurückgezahlt.");

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
}