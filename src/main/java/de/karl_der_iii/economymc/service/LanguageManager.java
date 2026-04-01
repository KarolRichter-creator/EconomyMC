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
        en.put("common.history", "§aOpen History");
        en.put("common.treasury", "Treasury");
        en.put("common.publish", "§bPublish");
        en.put("common.sell", "§aSell");
        en.put("common.page", "§7Page ");
        en.put("common.amount", "§7Amount");
        en.put("common.seller", "§7Seller");
        en.put("common.click_to_view", "§7Click to view");

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

        en.put("checks.menu.title", "EconomyMC Checks");
        en.put("check.redeem.title", "EconomyMC Redeem Check");

        en.put("shop.menu.title", "EconomyMC Shop");
        en.put("shop.bundle", "§dShop Bundle");
        en.put("shop.base_price", "§6Base Price: $");
        en.put("shop.tax", "§cTax: $");
        en.put("shop.total", "§aTotal: $");
        en.put("shop.stacks_inside", "§7Stacks inside: ");
        en.put("shop.total_items", "§7Total items: ");
        en.put("shop.and_more", "§8...and more");
        en.put("shop.sell.title", "EconomyMC Sell Item");
        en.put("shop.sell.set_price", "§eSet Price");
        en.put("shop.sell.publish", "§aPublish");
        en.put("shop.sell.put_items_first", "§cPut items in first.");
        en.put("shop.sell.published", "§aShop listing published.");
        en.put("shop.input.enter_price", "§eEnter the shop price in chat now.");
        en.put("shop.input.no_draft", "§cNo shop draft selected.");
        en.put("shop.input.price_positive", "§cPrice must be above 0.");
        en.put("shop.input.price_set", "§aShop price set to $");
        en.put("shop.input.invalid_number", "§cThat is not a valid number.");

        en.put("sale.menu.title", "EconomyMC Create Sale Listing");
        en.put("sale.no_draft", "§cNo sale draft selected");
        en.put("sale.selected_capital", "§6Selected Capital Plot");
        en.put("sale.selected_plot", "§7Selected Plot");
        en.put("sale.plot", "§bPlot: ");
        en.put("sale.chunks", " Chunks");
        en.put("sale.set_price", "§eSet Price: $");
        en.put("sale.set_description", "§7Set Description");
        en.put("sale.set_built", "§7Set Built On Plot");
        en.put("sale.set_justification", "§7Set Price Justification");
        en.put("sale.negotiable", "§aNegotiable");
        en.put("sale.fixed", "§6Fixed Price");
        en.put("sale.publish", "§bPublish Listing");
        en.put("sale.location", "§7Location: ");
        en.put("sale.clear_draft", "§cClear Draft");
        en.put("sale.already_listed", "§cThis plot is already listed in the market.");
        en.put("sale.published", "§aListing published.");
        en.put("sale.cleared", "§aSale draft cleared.");

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
        de.put("common.history", "§aVerlauf öffnen");
        de.put("common.treasury", "Treasury");
        de.put("common.publish", "§bVeröffentlichen");
        de.put("common.sell", "§aVerkaufen");
        de.put("common.page", "§7Seite ");
        de.put("common.amount", "§7Menge");
        de.put("common.seller", "§7Verkäufer");
        de.put("common.click_to_view", "§7Zum Anzeigen klicken");

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
        de.put("jobs.input.title", "§eGib jetzt den Jobtitel im Chat ein.");
        de.put("jobs.input.server_title", "§eGib jetzt den Server-Jobtitel im Chat ein.");
        de.put("jobs.input.description", "§eGib jetzt die Jobbeschreibung im Chat ein.");
        de.put("jobs.input.reward", "§eGib jetzt die Belohnung im Chat ein.");
        de.put("jobs.input.due_days", "§eGib jetzt die Laufzeit in vollen Tagen ein.");
        de.put("jobs.input.invalid_number", "§cDas ist keine gültige Zahl.");
        de.put("jobs.input.reward_positive", "§cDie Belohnung muss über 0 liegen.");
        de.put("jobs.input.days_positive", "§cDie Tage müssen über 0 liegen.");
        de.put("jobs.input.treasury_missing", "§cDas Treasury hat nicht genug Geld für diesen Server-Job.");
        de.put("jobs.input.player_missing_money", "§cDu hast nicht genug Geld, um diesen Job zu erstellen.");
        de.put("jobs.input.created", "§aJob erstellt.");

        de.put("checks.menu.title", "EconomyMC Checks");
        de.put("check.redeem.title", "EconomyMC Check einlösen");

        de.put("shop.menu.title", "EconomyMC Shop");
        de.put("shop.bundle", "§dShop-Bündel");
        de.put("shop.base_price", "§6Grundpreis: $");
        de.put("shop.tax", "§cSteuer: $");
        de.put("shop.total", "§aGesamt: $");
        de.put("shop.stacks_inside", "§7Stacks innen: ");
        de.put("shop.total_items", "§7Gesamtanzahl Items: ");
        de.put("shop.and_more", "§8...und mehr");
        de.put("shop.sell.title", "EconomyMC Item verkaufen");
        de.put("shop.sell.set_price", "§ePreis setzen");
        de.put("shop.sell.publish", "§aVeröffentlichen");
        de.put("shop.sell.put_items_first", "§cLege zuerst Items hinein.");
        de.put("shop.sell.published", "§aShop-Angebot veröffentlicht.");
        de.put("shop.input.enter_price", "§eGib jetzt den Shop-Preis im Chat ein.");
        de.put("shop.input.no_draft", "§cKein Shop-Entwurf ausgewählt.");
        de.put("shop.input.price_positive", "§cDer Preis muss über 0 liegen.");
        de.put("shop.input.price_set", "§aShop-Preis gesetzt auf $");
        de.put("shop.input.invalid_number", "§cDas ist keine gültige Zahl.");

        de.put("sale.menu.title", "EconomyMC Verkaufsangebot erstellen");
        de.put("sale.no_draft", "§cKein Verkaufsentwurf ausgewählt");
        de.put("sale.selected_capital", "§6Ausgewähltes Hauptstadt-Grundstück");
        de.put("sale.selected_plot", "§7Ausgewähltes Grundstück");
        de.put("sale.plot", "§bGrundstück: ");
        de.put("sale.chunks", " Chunks");
        de.put("sale.set_price", "§ePreis setzen: $");
        de.put("sale.set_description", "§7Beschreibung setzen");
        de.put("sale.set_built", "§7Bebauung setzen");
        de.put("sale.set_justification", "§7Preisbegründung setzen");
        de.put("sale.negotiable", "§aVerhandelbar");
        de.put("sale.fixed", "§6Festpreis");
        de.put("sale.publish", "§bAngebot veröffentlichen");
        de.put("sale.location", "§7Ort: ");
        de.put("sale.clear_draft", "§cEntwurf löschen");
        de.put("sale.already_listed", "§cDieses Grundstück ist bereits auf dem Markt gelistet.");
        de.put("sale.published", "§aAngebot veröffentlicht.");
        de.put("sale.cleared", "§aVerkaufsentwurf gelöscht.");

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

        Map<String, String> pl = new HashMap<>(de);
        pl.put("admin.language", "§7Język: Polski");
        pl.put("common.close", "§cZamknij");
        pl.put("common.back", "§cPowrót");
        pl.put("common.next", "§7Następna strona");
        pl.put("common.previous", "§7Poprzednia strona");
        pl.put("common.confirm", "§aPotwierdź");
        pl.put("common.cancel", "§cAnuluj");
        pl.put("common.create", "§aUtwórz");
        pl.put("common.edit", "§eEdytuj");
        pl.put("common.price", "§6Cena");
        pl.put("common.description", "§7Opis");
        pl.put("common.status", "§7Status");
        pl.put("common.history", "§aOtwórz historię");
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
        pl.put("shop.menu.title", "EconomyMC Sklep");
        pl.put("shop.sell.title", "EconomyMC Sprzedaj przedmiot");
        pl.put("history.title", "EconomyMC Historia");
        LANG.put("pl_pl", pl);

        Map<String, String> fr = new HashMap<>(en);
        fr.put("admin.language", "§7Langue : Français");
        fr.put("common.close", "§cFermer");
        fr.put("common.back", "§cRetour");
        fr.put("common.next", "§7Page suivante");
        fr.put("common.previous", "§7Page précédente");
        fr.put("common.confirm", "§aConfirmer");
        fr.put("common.cancel", "§cAnnuler");
        fr.put("common.create", "§aCréer");
        fr.put("common.edit", "§eModifier");
        fr.put("common.price", "§6Prix");
        fr.put("common.description", "§7Description");
        fr.put("common.status", "§7Statut");
        fr.put("common.history", "§aOuvrir l'historique");
        fr.put("plots.menu.title", "EconomyMC Parcelles");
        fr.put("shop.menu.title", "EconomyMC Boutique");
        fr.put("history.title", "Historique EconomyMC");
        LANG.put("fr_fr", fr);

        Map<String, String> es = new HashMap<>(en);
        es.put("admin.language", "§7Idioma: Español");
        es.put("common.close", "§cCerrar");
        es.put("common.back", "§cAtrás");
        es.put("common.next", "§7Página siguiente");
        es.put("common.previous", "§7Página anterior");
        es.put("common.confirm", "§aConfirmar");
        es.put("common.cancel", "§cCancelar");
        es.put("common.create", "§aCrear");
        es.put("common.edit", "§eEditar");
        es.put("common.price", "§6Precio");
        es.put("common.description", "§7Descripción");
        es.put("common.status", "§7Estado");
        es.put("common.history", "§aAbrir historial");
        es.put("plots.menu.title", "EconomyMC Parcelas");
        es.put("shop.menu.title", "EconomyMC Tienda");
        es.put("history.title", "Historial EconomyMC");
        LANG.put("es_es", es);

        Map<String, String> pt = new HashMap<>(en);
        pt.put("admin.language", "§7Idioma: Português");
        pt.put("common.close", "§cFechar");
        pt.put("common.back", "§cVoltar");
        pt.put("common.next", "§7Próxima página");
        pt.put("common.previous", "§7Página anterior");
        pt.put("common.confirm", "§aConfirmar");
        pt.put("common.cancel", "§cCancelar");
        pt.put("common.create", "§aCriar");
        pt.put("common.edit", "§eEditar");
        pt.put("common.price", "§6Preço");
        pt.put("common.description", "§7Descrição");
        pt.put("common.status", "§7Status");
        pt.put("common.history", "§aAbrir histórico");
        pt.put("plots.menu.title", "EconomyMC Terrenos");
        pt.put("shop.menu.title", "EconomyMC Loja");
        pt.put("history.title", "Histórico EconomyMC");
        LANG.put("pt_br", pt);

        Map<String, String> ru = new HashMap<>(en);
        ru.put("admin.language", "§7Язык: Русский");
        ru.put("common.close", "§cЗакрыть");
        ru.put("common.back", "§cНазад");
        ru.put("common.next", "§7Следующая страница");
        ru.put("common.previous", "§7Предыдущая страница");
        ru.put("common.confirm", "§aПодтвердить");
        ru.put("common.cancel", "§cОтмена");
        ru.put("common.create", "§aСоздать");
        ru.put("common.edit", "§eИзменить");
        ru.put("common.price", "§6Цена");
        ru.put("common.description", "§7Описание");
        ru.put("common.status", "§7Статус");
        ru.put("common.history", "§aОткрыть историю");
        ru.put("plots.menu.title", "EconomyMC Участки");
        ru.put("shop.menu.title", "EconomyMC Магазин");
        ru.put("history.title", "История EconomyMC");
        LANG.put("ru_ru", ru);

        Map<String, String> tr = new HashMap<>(en);
        tr.put("admin.language", "§7Dil: Türkçe");
        tr.put("common.close", "§cKapat");
        tr.put("common.back", "§cGeri");
        tr.put("common.next", "§7Sonraki sayfa");
        tr.put("common.previous", "§7Önceki sayfa");
        tr.put("common.confirm", "§aOnayla");
        tr.put("common.cancel", "§cİptal");
        tr.put("common.create", "§aOluştur");
        tr.put("common.edit", "§eDüzenle");
        tr.put("common.price", "§6Fiyat");
        tr.put("common.description", "§7Açıklama");
        tr.put("common.status", "§7Durum");
        tr.put("common.history", "§aGeçmişi aç");
        tr.put("plots.menu.title", "EconomyMC Arsalar");
        tr.put("shop.menu.title", "EconomyMC Mağaza");
        tr.put("history.title", "EconomyMC Geçmiş");
        LANG.put("tr_tr", tr);

        Map<String, String> zh = new HashMap<>(en);
        zh.put("admin.language", "§7语言: 中文");
        zh.put("common.close", "§c关闭");
        zh.put("common.back", "§c返回");
        zh.put("common.next", "§7下一页");
        zh.put("common.previous", "§7上一页");
        zh.put("common.confirm", "§a确认");
        zh.put("common.cancel", "§c取消");
        zh.put("common.create", "§a创建");
        zh.put("common.edit", "§e编辑");
        zh.put("common.price", "§6价格");
        zh.put("common.description", "§7说明");
        zh.put("common.status", "§7状态");
        zh.put("common.history", "§a打开历史");
        zh.put("plots.menu.title", "EconomyMC 地块");
        zh.put("shop.menu.title", "EconomyMC 商店");
        zh.put("shop.sell.title", "EconomyMC 出售物品");
        zh.put("history.title", "EconomyMC 历史");
        zh.put("server.mode.title", "EconomyMC 服务器模式");
        LANG.put("zh_cn", zh);

        Map<String, String> ja = new HashMap<>(en);
        ja.put("admin.language", "§7言語: 日本語");
        ja.put("common.close", "§c閉じる");
        ja.put("common.back", "§c戻る");
        ja.put("common.next", "§7次のページ");
        ja.put("common.previous", "§7前のページ");
        ja.put("common.confirm", "§a確認");
        ja.put("common.cancel", "§cキャンセル");
        ja.put("common.create", "§a作成");
        ja.put("common.edit", "§e編集");
        ja.put("common.price", "§6価格");
        ja.put("common.description", "§7説明");
        ja.put("common.status", "§7状態");
        ja.put("common.history", "§a履歴を開く");
        ja.put("plots.menu.title", "EconomyMC 区画");
        ja.put("shop.menu.title", "EconomyMC ショップ");
        ja.put("history.title", "EconomyMC 履歴");
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