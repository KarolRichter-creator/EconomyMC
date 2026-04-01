package de.karl_der_iii.economymc.service;

import java.util.HashMap;
import java.util.Map;

public final class LanguageManager {
    private static final Map<String, Map<String, String>> LANG = new HashMap<>();

    static {
        register("de_de",
            "help.header", "§6EconomyMC Befehle",
            "help.plots", "§e/ec plots §7- öffnet das Grundstücksmenü",
            "help.shop", "§e/ec shop §7- öffnet das Shop-Menü",
            "help.jobs", "§e/ec jobs §7- öffnet das Jobs-Menü",
            "help.checks", "§e/ec checks §7- öffnet das Checks-Menü",
            "help.history", "§e/ec history §7- öffnet den Zahlungsverlauf",
            "help.bank", "§e/ec bank §7- öffnet das Bank- und Kreditsystem",
            "help.daily", "§e/ec daily §7- holt die tägliche Belohnung",
            "help.pay", "§e/ec pay <Spieler/Treasury> <Betrag> §7- sendet Geld",
            "help.servermode", "§e/ec servermode §7- öffnet den Servermodus",
            "help.adminmode", "§e/ec adminmode §7- öffnet den Adminmodus",
            "help.admin", "§e/ec admin ... §7- Hauptstadt und Kontoverwaltung",
            "help.language", "§7Sprachen: Deutsch, Englisch, Polnisch, Französisch, Spanisch, Portugiesisch, Russisch, Türkisch, Chinesisch, Japanisch",

            "cmd.only_players", "Nur Spieler können diesen Befehl benutzen.",
            "msg.shop_disabled", "§cShop ist vom Admin deaktiviert.",
            "msg.jobs_disabled", "§cJobs sind vom Admin deaktiviert.",
            "msg.checks_disabled", "§cChecks sind vom Admin deaktiviert.",
            "msg.servermode_disabled", "§cServer-Modus ist vom Admin deaktiviert.",

            "admin.language", "§7Sprache: Deutsch",
            "admin.language.toggle", "§bSprache wechseln",

            "money.label", "Geld",
            "credits.normal", "Normale Credits",
            "credits.capital", "Hauptstadt-Credits",

            "plots.menu.title", "EconomyMC Grundstücke",
            "plots.current.capital", "§6Aktuelle Position: Hauptstadt",
            "plots.current.normal", "§7Aktuelle Position: Normal",
            "plots.buy.normal", "§eNormale Claim-Chunks kaufen §7(%d | %d$)",
            "plots.buy.capital", "§6Hauptstadt-Claim-Chunks kaufen §7(%d | %d$)",
            "plots.create.sale", "§aNeuen Verkauf anlegen",
            "plots.market", "§3Marktangebote §7(%d)",
            "plots.owned", "§bMein Besitz §7(%d)",
            "plots.sales", "§dMeine Verkäufe §7(%d)",
            "plots.history", "§aVerlauf öffnen",

            "market.menu.title", "EconomyMC Markt",
            "market.back", "§cZurück",
            "market.entry", "%s §7| %d$ | %d Chunks",

            "listing.menu.title", "EconomyMC Grundstücksdetails",
            "listing.missing", "§cAngebot nicht mehr vorhanden",
            "listing.price", "§6Preis: %d$",
            "listing.description", "§7Beschreibung: %s",
            "listing.location", "§bLage: %s",
            "listing.building", "§7Bebauung: %s",
            "listing.reason", "§7Preisbegründung: %s",
            "listing.chunks", "§bChunks: %d",
            "listing.buy", "§aKaufen",
            "listing.buy.own", "§cDu kannst dein eigenes Grundstück nicht kaufen.",
            "listing.buy.success", "§aDu hast das Grundstück gekauft: %s",
            "listing.not.exists", "§cAngebot existiert nicht mehr.",
            "listing.not.enough", "§cNicht genug Geld.",

            "history.title", "EconomyMC Verlauf",
            "history.empty", "§7Noch keine Einträge vorhanden.",
            "history.mine", "§bMein Verlauf",
            "history.treasury", "§6Treasury-Verlauf",
            "history.open", "§aVerlauf öffnen",

            "history.pay.sent", "§aPay an %s: $%d",
            "history.pay.received", "§aPay von %s: $%d",
            "history.daily", "§eDaily Belohnung: $%d",
            "history.check.create", "§dCheck erstellt: $%d",
            "history.check.redeem", "§dCheck eingelöst: $%d",
            "history.job.reward", "§bJob ausgezahlt: $%d",
            "history.job.refund", "§6Job-Rückzahlung: $%d",
            "history.treasury.tax", "§6Steuer erhalten: $%d",
            "history.admin.set", "§cAdmin setzte Guthaben: $%d",
            "history.loan.request", "§9Kreditanfrage erstellt: $%d",
            "history.loan.funded", "§9Kredit vergeben: $%d",
            "history.loan.received", "§9Kredit erhalten: $%d",
            "history.loan.repaid", "§9Kredit zurückgezahlt: $%d",
            "history.loan.interest", "§9Zinsen bezahlt: $%d",

            "bank.title", "EconomyMC Bank",
            "bank.request.created", "§aKreditanfrage erstellt.",
            "bank.offer.created", "§aKreditangebot erstellt.",
            "bank.accepted", "§aKredit angenommen.",
            "bank.repaid", "§aKredit zurückgezahlt.",
            "bank.not_enough_money", "§cNicht genug Geld.",
            "bank.not_found", "§cEintrag nicht gefunden.",
            "bank.invalid_target", "§cUngültiges Kredit-Ziel.",
            "bank.target.all", "Alle Spieler",
            "bank.target.server", "Server",
            "bank.target.player", "Spieler",
            "bank.command.list", "§e/ec bank list §7- zeigt offene Kredite",
            "bank.command.request", "§e/ec bank request <server|all|player> [name] <amount> <days> §7- erstellt eine Anfrage",
            "bank.command.offer", "§e/ec bank offer <loanId> <interestPercent> §7- erstellt ein Angebot",
            "bank.command.accept", "§e/ec bank accept <loanId> §7- nimmt ein Angebot an",
            "bank.command.repay", "§e/ec bank repay <loanId> §7- zahlt einen Kredit zurück"
        );

        register("en_us",
            "help.header", "§6EconomyMC Commands",
            "help.plots", "§e/ec plots §7- opens the plots menu",
            "help.shop", "§e/ec shop §7- opens the shop menu",
            "help.jobs", "§e/ec jobs §7- opens the jobs menu",
            "help.checks", "§e/ec checks §7- opens the checks menu",
            "help.history", "§e/ec history §7- opens the payment history",
            "help.bank", "§e/ec bank §7- opens the banking and loan system",
            "help.daily", "§e/ec daily §7- claims the daily reward",
            "help.pay", "§e/ec pay <player/treasury> <amount> §7- sends money",
            "help.servermode", "§e/ec servermode §7- opens server mode",
            "help.adminmode", "§e/ec adminmode §7- opens admin mode",
            "help.admin", "§e/ec admin ... §7- capital and account management",
            "help.language", "§7Languages: German, English, Polish, French, Spanish, Portuguese, Russian, Turkish, Chinese, Japanese",

            "cmd.only_players", "Only players can use this command.",
            "msg.shop_disabled", "§cShop is disabled by admin.",
            "msg.jobs_disabled", "§cJobs are disabled by admin.",
            "msg.checks_disabled", "§cChecks are disabled by admin.",
            "msg.servermode_disabled", "§cServer mode is disabled by admin.",

            "admin.language", "§7Language: English",
            "admin.language.toggle", "§bSwitch language",

            "money.label", "Money",
            "credits.normal", "Normal Credits",
            "credits.capital", "Capital Credits",

            "plots.menu.title", "EconomyMC Plots",
            "plots.current.capital", "§6Current Position: Capital",
            "plots.current.normal", "§7Current Position: Normal",
            "plots.buy.normal", "§eBuy normal claim chunks §7(%d | %d$)",
            "plots.buy.capital", "§6Buy capital claim chunks §7(%d | %d$)",
            "plots.create.sale", "§aCreate new sale",
            "plots.market", "§3Market listings §7(%d)",
            "plots.owned", "§bMy property §7(%d)",
            "plots.sales", "§dMy sales §7(%d)",
            "plots.history", "§aOpen history",

            "market.menu.title", "EconomyMC Market",
            "market.back", "§cBack",
            "market.entry", "%s §7| %d$ | %d chunks",

            "listing.menu.title", "EconomyMC Plot Details",
            "listing.missing", "§cListing no longer available",
            "listing.price", "§6Price: %d$",
            "listing.description", "§7Description: %s",
            "listing.location", "§bLocation: %s",
            "listing.building", "§7Building: %s",
            "listing.reason", "§7Price reason: %s",
            "listing.chunks", "§bChunks: %d",
            "listing.buy", "§aBuy",
            "listing.buy.own", "§cYou cannot buy your own listing.",
            "listing.buy.success", "§aYou bought the plot: %s",
            "listing.not.exists", "§cListing no longer exists.",
            "listing.not.enough", "§cNot enough money.",

            "history.title", "EconomyMC History",
            "history.empty", "§7No entries yet.",
            "history.mine", "§bMy History",
            "history.treasury", "§6Treasury History",
            "history.open", "§aOpen History",

            "history.pay.sent", "§aPay to %s: $%d",
            "history.pay.received", "§aPay from %s: $%d",
            "history.daily", "§eDaily reward: $%d",
            "history.check.create", "§dCreated check: $%d",
            "history.check.redeem", "§dRedeemed check: $%d",
            "history.job.reward", "§bJob payout: $%d",
            "history.job.refund", "§6Job refund: $%d",
            "history.treasury.tax", "§6Tax received: $%d",
            "history.admin.set", "§cAdmin set balance: $%d",
            "history.loan.request", "§9Loan request created: $%d",
            "history.loan.funded", "§9Loan funded: $%d",
            "history.loan.received", "§9Loan received: $%d",
            "history.loan.repaid", "§9Loan repaid: $%d",
            "history.loan.interest", "§9Interest paid: $%d",

            "bank.title", "EconomyMC Bank",
            "bank.request.created", "§aLoan request created.",
            "bank.offer.created", "§aLoan offer created.",
            "bank.accepted", "§aLoan accepted.",
            "bank.repaid", "§aLoan repaid.",
            "bank.not_enough_money", "§cNot enough money.",
            "bank.not_found", "§cEntry not found.",
            "bank.invalid_target", "§cInvalid loan target.",
            "bank.target.all", "All players",
            "bank.target.server", "Server",
            "bank.target.player", "Player",
            "bank.command.list", "§e/ec bank list §7- shows open loans",
            "bank.command.request", "§e/ec bank request <server|all|player> [name] <amount> <days> §7- creates a request",
            "bank.command.offer", "§e/ec bank offer <loanId> <interestPercent> §7- creates an offer",
            "bank.command.accept", "§e/ec bank accept <loanId> §7- accepts an offer",
            "bank.command.repay", "§e/ec bank repay <loanId> §7- repays a loan"
        );

        copyLanguage("pl_pl", "en_us", "§7Język: Polski");
        copyLanguage("fr_fr", "en_us", "§7Langue : Français");
        copyLanguage("es_es", "en_us", "§7Idioma: Español");
        copyLanguage("pt_br", "en_us", "§7Idioma: Português");
        copyLanguage("ru_ru", "en_us", "§7Язык: Русский");
        copyLanguage("tr_tr", "en_us", "§7Dil: Türkçe");
        copyLanguage("zh_cn", "en_us", "§7语言：中文");
        copyLanguage("ja_jp", "en_us", "§7言語: 日本語");
    }

    private LanguageManager() {}

    private static void register(String language, String... kv) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < kv.length; i += 2) {
            map.put(kv[i], kv[i + 1]);
        }
        LANG.put(language, map);
    }

    private static void copyLanguage(String language, String from, String languageLabel) {
        Map<String, String> base = new HashMap<>(LANG.get(from));
        base.put("admin.language", languageLabel);
        LANG.put(language, base);
    }

    public static String tr(String key) {
        return tr(AdminSettingsManager.language(), key);
    }

    public static String tr(String lang, String key) {
        Map<String, String> source = LANG.getOrDefault(lang, LANG.get("en_us"));
        return source.getOrDefault(key, key);
    }

    public static String format(String key, Object... args) {
        return String.format(tr(key), args);
    }

    public static String currentLanguageLabel() {
        return tr("admin.language");
    }
}