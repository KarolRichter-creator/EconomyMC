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
        en.put("main.disabled.shop", "§8Shop Disabled");
        en.put("main.disabled.jobs", "§8Jobs Disabled");
        en.put("main.disabled.checks", "§8Checks Disabled");
        en.put("main.disabled.servermode", "§8Server Mode Disabled");

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
        en.put("admin.on", "ON");
        en.put("admin.off", "OFF");

        en.put("jobs.menu.title", "EconomyMC Jobs");
        en.put("jobs.server.title", "EconomyMC Server Jobs");
        en.put("jobs.add", "§aCreate Job");
        en.put("jobs.create_hint", "§7Create only in this menu");
        en.put("jobs.status.open", "§aOpen");
        en.put("jobs.status.opens_at", "§eOpens at: ");
        en.put("jobs.status.in_progress", "§6In progress: ");
        en.put("jobs.status.completed", "§bCompleted");
        en.put("jobs.status.confirmed", "§aConfirmed");
        en.put("jobs.status.cancelled", "§cCancelled");
        en.put("jobs.status.failed", "§4Failed");

        en.put("jobs.input.title", "Enter the job title in chat now.");
        en.put("jobs.input.server_title", "Enter the server job title in chat now.");
        en.put("jobs.input.description", "Enter the job description in chat now.");
        en.put("jobs.input.reward", "Enter the reward amount in chat now.");
        en.put("jobs.input.due_days", "Enter the due time in full days now.");
        en.put("jobs.input.invalid_number", "That is not a valid number.");
        en.put("jobs.input.reward_positive", "Reward must be above 0.");
        en.put("jobs.input.days_positive", "Due days must be above 0.");
        en.put("jobs.input.treasury_missing", "The treasury does not have enough money for this server job.");
        en.put("jobs.input.player_missing_money", "You do not have enough money to create this job.");
        en.put("jobs.input.created", "Job created.");

        en.put("checks.input.amount", "Enter the check amount in chat now.");
        en.put("checks.input.code", "Enter the check code in chat now.");
        en.put("checks.input.invalid_number", "That is not a valid number.");
        en.put("checks.input.amount_positive", "Amount must be above 0.");
        en.put("checks.input.not_enough", "You do not have enough money.");
        en.put("checks.input.created", "Check created.");
        en.put("checks.input.invalid_code", "Invalid code or check already redeemed.");
        en.put("checks.input.redeemed_success", "Check redeemed successfully.");

        en.put("daily.already", "§cDaily already claimed for today. Come back in %dh %dm.");
        en.put("daily.claimed", "§aYou claimed your daily $100.");

        en.put("history.daily", "§eDaily reward: $%d");
        en.put("history.pay.sent", "§aPay to %s: $%d");
        en.put("history.pay.received", "§aPay from %s: $%d");

        en.put("msg.shop_disabled", "§cShop is disabled by admin.");
        en.put("msg.jobs_disabled", "§cJobs are disabled by admin.");
        en.put("msg.checks_disabled", "§cChecks are disabled by admin.");
        en.put("msg.servermode_disabled", "§cServer mode is disabled by admin.");

        LANG.put("en_us", en);

        Map<String, String> de = new HashMap<>(en);
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
        de.put("main.disabled.shop", "§8Shop deaktiviert");
        de.put("main.disabled.jobs", "§8Jobs deaktiviert");
        de.put("main.disabled.checks", "§8Checks deaktiviert");
        de.put("main.disabled.servermode", "§8Servermodus deaktiviert");

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

        de.put("jobs.input.title", "Gib jetzt den Jobtitel im Chat ein.");
        de.put("jobs.input.server_title", "Gib jetzt den Server-Jobtitel im Chat ein.");
        de.put("jobs.input.description", "Gib jetzt die Jobbeschreibung im Chat ein.");
        de.put("jobs.input.reward", "Gib jetzt die Belohnung im Chat ein.");
        de.put("jobs.input.due_days", "Gib jetzt die Fälligkeit in vollen Tagen ein.");
        de.put("jobs.input.invalid_number", "Das ist keine gültige Zahl.");
        de.put("jobs.input.reward_positive", "Die Belohnung muss über 0 liegen.");
        de.put("jobs.input.days_positive", "Die Tage müssen über 0 liegen.");
        de.put("jobs.input.treasury_missing", "Das Treasury hat nicht genug Geld für diesen Server-Job.");
        de.put("jobs.input.player_missing_money", "Du hast nicht genug Geld, um diesen Job zu erstellen.");
        de.put("jobs.input.created", "Job erstellt.");

        de.put("checks.input.amount", "Gib jetzt den Check-Betrag im Chat ein.");
        de.put("checks.input.code", "Gib jetzt den Check-Code im Chat ein.");
        de.put("checks.input.invalid_number", "Das ist keine gültige Zahl.");
        de.put("checks.input.amount_positive", "Der Betrag muss über 0 liegen.");
        de.put("checks.input.not_enough", "Du hast nicht genug Geld.");
        de.put("checks.input.created", "Check erstellt.");
        de.put("checks.input.invalid_code", "Ungültiger Code oder Check bereits eingelöst.");
        de.put("checks.input.redeemed_success", "Check erfolgreich eingelöst.");

        de.put("daily.already", "§cDaily für heute schon abgeholt. Komm in %dh %dm zurück.");
        de.put("daily.claimed", "§aDu hast dein tägliches $100 abgeholt.");

        de.put("history.daily", "§eTägliche Belohnung: $%d");
        de.put("history.pay.sent", "§aÜberweisung an %s: $%d");
        de.put("history.pay.received", "§aÜberweisung von %s: $%d");

        de.put("msg.shop_disabled", "§cShop ist vom Admin deaktiviert.");
        de.put("msg.jobs_disabled", "§cJobs sind vom Admin deaktiviert.");
        de.put("msg.checks_disabled", "§cChecks sind vom Admin deaktiviert.");
        de.put("msg.servermode_disabled", "§cServer-Modus ist vom Admin deaktiviert.");

        LANG.put("de_de", de);

        Map<String, String> pl = new HashMap<>(en);
        pl.put("common.close", "§cZamknij");
        pl.put("common.back", "§cPowrót");
        pl.put("main.menu.title", "EconomyMC");
        pl.put("main.plots", "§aDziałki");
        pl.put("main.shop", "§eSklep");
        pl.put("main.jobs", "§bZadania");
        pl.put("main.checks", "§dCzeki");
        pl.put("main.bank", "§6Bank");
        pl.put("main.history", "§7Historia");
        pl.put("main.daily", "§aNagroda dzienna");
        pl.put("main.pay", "§6Płać");
        pl.put("main.servermode", "§cTryb serwera");
        pl.put("main.servermode.disabled", "§8Tryb serwera wyłączony");
        pl.put("main.adminmode", "§4Tryb administratora");
        pl.put("plots.menu.title", "EconomyMC Działki");
        pl.put("plots.connector", "§7OpenPants Connect");
        pl.put("admin.mode.title", "EconomyMC Tryb administratora");
        pl.put("admin.language", "Język");
        pl.put("admin.language.previous", "§ePoprzedni język");
        pl.put("admin.language.next", "§eNastępny język");
        pl.put("admin.on", "WŁ");
        pl.put("admin.off", "WYŁ");
        pl.put("jobs.input.title", "Wpisz teraz tytuł zadania na czacie.");
        pl.put("checks.input.amount", "Wpisz teraz kwotę czeku na czacie.");
        LANG.put("pl_pl", pl);

        Map<String, String> fr = new HashMap<>(en);
        fr.put("common.close", "§cFermer");
        fr.put("common.back", "§cRetour");
        fr.put("main.menu.title", "EconomyMC");
        fr.put("main.plots", "§aParcelles");
        fr.put("main.shop", "§eBoutique");
        fr.put("main.jobs", "§bJobs");
        fr.put("main.checks", "§dChèques");
        fr.put("main.bank", "§6Banque");
        fr.put("main.history", "§7Historique");
        fr.put("main.daily", "§aRécompense quotidienne");
        fr.put("main.pay", "§6Payer");
        fr.put("main.servermode", "§cMode serveur");
        fr.put("main.servermode.disabled", "§8Mode serveur désactivé");
        fr.put("main.adminmode", "§4Mode admin");
        fr.put("plots.menu.title", "EconomyMC Parcelles");
        fr.put("plots.connector", "§7OpenPants Connect");
        fr.put("admin.mode.title", "EconomyMC Mode admin");
        fr.put("admin.language", "Langue");
        fr.put("admin.language.previous", "§eLangue précédente");
        fr.put("admin.language.next", "§eLangue suivante");
        fr.put("admin.on", "ACTIF");
        fr.put("admin.off", "INACTIF");
        fr.put("jobs.input.title", "Entrez maintenant le titre du job dans le chat.");
        fr.put("checks.input.amount", "Entrez maintenant le montant du chèque dans le chat.");
        LANG.put("fr_fr", fr);

        Map<String, String> es = new HashMap<>(en);
        es.put("common.close", "§cCerrar");
        es.put("common.back", "§cAtrás");
        es.put("main.menu.title", "EconomyMC");
        es.put("main.plots", "§aParcelas");
        es.put("main.shop", "§eTienda");
        es.put("main.jobs", "§bTrabajos");
        es.put("main.checks", "§dCheques");
        es.put("main.bank", "§6Banco");
        es.put("main.history", "§7Historial");
        es.put("main.daily", "§aRecompensa diaria");
        es.put("main.pay", "§6Pagar");
        es.put("main.servermode", "§cModo servidor");
        es.put("main.servermode.disabled", "§8Modo servidor desactivado");
        es.put("main.adminmode", "§4Modo admin");
        es.put("plots.menu.title", "EconomyMC Parcelas");
        es.put("plots.connector", "§7OpenPants Connect");
        es.put("admin.mode.title", "EconomyMC Modo admin");
        es.put("admin.language", "Idioma");
        es.put("admin.language.previous", "§eIdioma anterior");
        es.put("admin.language.next", "§eSiguiente idioma");
        es.put("admin.on", "ENC");
        es.put("admin.off", "APAG");
        es.put("jobs.input.title", "Introduce ahora el título del trabajo en el chat.");
        es.put("checks.input.amount", "Introduce ahora la cantidad del cheque en el chat.");
        LANG.put("es_es", es);

        Map<String, String> pt = new HashMap<>(en);
        pt.put("common.close", "§cFechar");
        pt.put("common.back", "§cVoltar");
        pt.put("main.menu.title", "EconomyMC");
        pt.put("main.plots", "§aTerrenos");
        pt.put("main.shop", "§eLoja");
        pt.put("main.jobs", "§bTrabalhos");
        pt.put("main.checks", "§dCheques");
        pt.put("main.bank", "§6Banco");
        pt.put("main.history", "§7Histórico");
        pt.put("main.daily", "§aRecompensa diária");
        pt.put("main.pay", "§6Pagar");
        pt.put("main.servermode", "§cModo servidor");
        pt.put("main.servermode.disabled", "§8Modo servidor desativado");
        pt.put("main.adminmode", "§4Modo admin");
        pt.put("plots.menu.title", "EconomyMC Terrenos");
        pt.put("plots.connector", "§7OpenPants Connect");
        pt.put("admin.mode.title", "EconomyMC Modo admin");
        pt.put("admin.language", "Idioma");
        pt.put("admin.language.previous", "§eIdioma anterior");
        pt.put("admin.language.next", "§ePróximo idioma");
        pt.put("admin.on", "LIG");
        pt.put("admin.off", "DESL");
        pt.put("jobs.input.title", "Digite agora o título do trabalho no chat.");
        pt.put("checks.input.amount", "Digite agora o valor do cheque no chat.");
        LANG.put("pt_br", pt);

        Map<String, String> ru = new HashMap<>(en);
        ru.put("common.close", "§cЗакрыть");
        ru.put("common.back", "§cНазад");
        ru.put("main.menu.title", "EconomyMC");
        ru.put("main.plots", "§aУчастки");
        ru.put("main.shop", "§eМагазин");
        ru.put("main.jobs", "§bЗадания");
        ru.put("main.checks", "§dЧеки");
        ru.put("main.bank", "§6Банк");
        ru.put("main.history", "§7История");
        ru.put("main.daily", "§aЕжедневная награда");
        ru.put("main.pay", "§6Оплатить");
        ru.put("main.servermode", "§cРежим сервера");
        ru.put("main.servermode.disabled", "§8Режим сервера отключён");
        ru.put("main.adminmode", "§4Режим администратора");
        ru.put("plots.menu.title", "EconomyMC Участки");
        ru.put("plots.connector", "§7OpenPants Connect");
        ru.put("admin.mode.title", "EconomyMC Режим администратора");
        ru.put("admin.language", "Язык");
        ru.put("admin.language.previous", "§eПредыдущий язык");
        ru.put("admin.language.next", "§eСледующий язык");
        ru.put("admin.on", "ВКЛ");
        ru.put("admin.off", "ВЫКЛ");
        ru.put("jobs.input.title", "Введите название задания в чат.");
        ru.put("checks.input.amount", "Введите сумму чека в чат.");
        LANG.put("ru_ru", ru);

        Map<String, String> tr = new HashMap<>(en);
        tr.put("common.close", "§cKapat");
        tr.put("common.back", "§cGeri");
        tr.put("main.menu.title", "EconomyMC");
        tr.put("main.plots", "§aArsalar");
        tr.put("main.shop", "§eMağaza");
        tr.put("main.jobs", "§bİşler");
        tr.put("main.checks", "§dÇekler");
        tr.put("main.bank", "§6Banka");
        tr.put("main.history", "§7Geçmiş");
        tr.put("main.daily", "§aGünlük ödül");
        tr.put("main.pay", "§6Öde");
        tr.put("main.servermode", "§cSunucu modu");
        tr.put("main.servermode.disabled", "§8Sunucu modu devre dışı");
        tr.put("main.adminmode", "§4Yönetici modu");
        tr.put("plots.menu.title", "EconomyMC Arsalar");
        tr.put("plots.connector", "§7OpenPants Connect");
        tr.put("admin.mode.title", "EconomyMC Yönetici modu");
        tr.put("admin.language", "Dil");
        tr.put("admin.language.previous", "§eÖnceki dil");
        tr.put("admin.language.next", "§eSonraki dil");
        tr.put("admin.on", "AÇIK");
        tr.put("admin.off", "KAPALI");
        tr.put("jobs.input.title", "Şimdi iş başlığını sohbete yaz.");
        tr.put("checks.input.amount", "Şimdi çek miktarını sohbete yaz.");
        LANG.put("tr_tr", tr);

        Map<String, String> zh = new HashMap<>(en);
        zh.put("common.close", "§c关闭");
        zh.put("common.back", "§c返回");
        zh.put("main.menu.title", "EconomyMC");
        zh.put("main.plots", "§a地块");
        zh.put("main.shop", "§e商店");
        zh.put("main.jobs", "§b工作");
        zh.put("main.checks", "§d支票");
        zh.put("main.bank", "§6银行");
        zh.put("main.history", "§7历史");
        zh.put("main.daily", "§a每日奖励");
        zh.put("main.pay", "§6支付");
        zh.put("main.servermode", "§c服务器模式");
        zh.put("main.servermode.disabled", "§8服务器模式已禁用");
        zh.put("main.adminmode", "§4管理模式");
        zh.put("plots.menu.title", "EconomyMC 地块");
        zh.put("plots.connector", "§7OpenPants Connect");
        zh.put("admin.mode.title", "EconomyMC 管理模式");
        zh.put("admin.language", "语言");
        zh.put("admin.language.previous", "§e上一个语言");
        zh.put("admin.language.next", "§e下一个语言");
        zh.put("admin.on", "开");
        zh.put("admin.off", "关");
        zh.put("jobs.input.title", "现在请在聊天中输入工作标题。");
        zh.put("checks.input.amount", "现在请在聊天中输入支票金额。");
        LANG.put("zh_cn", zh);

        Map<String, String> ja = new HashMap<>(en);
        ja.put("common.close", "§c閉じる");
        ja.put("common.back", "§c戻る");
        ja.put("main.menu.title", "EconomyMC");
        ja.put("main.plots", "§a区画");
        ja.put("main.shop", "§eショップ");
        ja.put("main.jobs", "§bジョブ");
        ja.put("main.checks", "§d小切手");
        ja.put("main.bank", "§6銀行");
        ja.put("main.history", "§7履歴");
        ja.put("main.daily", "§aデイリー報酬");
        ja.put("main.pay", "§6支払う");
        ja.put("main.servermode", "§cサーバーモード");
        ja.put("main.servermode.disabled", "§8サーバーモード無効");
        ja.put("main.adminmode", "§4管理モード");
        ja.put("plots.menu.title", "EconomyMC 区画");
        ja.put("plots.connector", "§7OpenPants Connect");
        ja.put("admin.mode.title", "EconomyMC 管理モード");
        ja.put("admin.language", "言語");
        ja.put("admin.language.previous", "§e前の言語");
        ja.put("admin.language.next", "§e次の言語");
        ja.put("admin.on", "オン");
        ja.put("admin.off", "オフ");
        ja.put("jobs.input.title", "今すぐチャットにジョブ名を入力してください。");
        ja.put("checks.input.amount", "今すぐチャットに小切手金額を入力してください。");
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
        return tr("help.language");
    }

    public static String languageName(String code) {
        return LANGUAGE_NAMES.getOrDefault(code, code);
    }
}