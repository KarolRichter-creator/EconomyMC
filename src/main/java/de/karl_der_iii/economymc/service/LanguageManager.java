package de.karl_der_iii.economymc.service;

import java.util.HashMap;
import java.util.Map;

public final class LanguageManager {
    private static final Map<String, Map<String, String>> LANG = new HashMap<>();

    static {
        Map<String, String> en = new HashMap<>();

        en.put("help.header", "§6EconomyMC Commands");
        en.put("help.plots", "§e/ec opens the main menu");
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

        en.put("checks.menu.title", "EconomyMC Checks");
        en.put("checks.create", "§aCreate Check");
        en.put("checks.entry", "%sCheck $%d - %s");

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

        en.put("history.empty", "§7No entries yet.");
        en.put("history.daily", "§eDaily reward: $%d");
        en.put("history.pay.sent", "§aPay to %s: $%d");
        en.put("history.pay.received", "§aPay from %s: $%d");

        en.put("bank.target.server", "Server");

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
        de.put("help.pay", "§7Pay ist im Hauptmenü");
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

        de.put("checks.menu.title", "EconomyMC Checks");
        de.put("checks.create", "§aCheck erstellen");
        de.put("checks.entry", "%sCheck $%d - %s");

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

        de.put("history.empty", "§7Noch keine Einträge vorhanden.");
        de.put("history.daily", "§eTägliche Belohnung: $%d");
        de.put("history.pay.sent", "§aÜberweisung an %s: $%d");
        de.put("history.pay.received", "§aÜberweisung von %s: $%d");

        de.put("bank.target.server", "Server");

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
        pl.put("pay.menu.title", "EconomyMC Płatność");
        pl.put("pay.menu.player", "§eZapłać %s");
        pl.put("pay.menu.info", "§7Wybierz gracza");
        pl.put("pay.target_offline", "§cTen gracz nie jest już online.");
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
}