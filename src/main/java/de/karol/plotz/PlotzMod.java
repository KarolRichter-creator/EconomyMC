package de.karol.plotz;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(PlotzMod.MOD_ID)
public class PlotzMod {
    public static final String MOD_ID = "plotz";

    public PlotzMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        System.out.println("[Plotz] Mod gestartet.");
    }

    private void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("plotz")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();

                    source.sendSuccess(() -> Component.literal("§6======= [ Plotz ] ======="), false);
                    source.sendSuccess(() -> Component.literal("§eNormale Claim-Chunks kaufen"), false);
                    source.sendSuccess(() -> Component.literal(" §7Preis: 250$"), false);
                    source.sendSuccess(() -> Component.literal("§eHauptstadt-Claim-Chunks kaufen"), false);
                    source.sendSuccess(() -> Component.literal(" §7Preis: 500$"), false);
                    source.sendSuccess(() -> Component.literal("§7Mindestabstand: 700 Blöcke"), false);
                    source.sendSuccess(() -> Component.literal("§7Server-Claims: gesperrt"), false);
                    source.sendSuccess(() -> Component.literal("§bDas echte Menü bauen wir im nächsten Schritt ein."), false);
                    source.sendSuccess(() -> Component.literal("§6========================"), false);
                    return 1;
                })
        );
    }
}
