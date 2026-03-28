package de.karol.plotz;

import com.mojang.brigadier.CommandDispatcher;
import de.karol.plotz.menu.PlotzMainMenu;
import de.karol.plotz.service.OpacBridge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xaero.pac.common.event.api.OPACServerAddonRegisterEvent;

@Mod(PlotzMod.MOD_ID)
public class PlotzMod {
    public static final String MOD_ID = "plotz";

    public PlotzMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener(this::onOpacAddonRegister);
        System.out.println("[Plotz] Mod gestartet.");
    }

    private void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("plotz")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();

                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                        source.sendFailure(Component.literal("Nur Spieler können /plotz benutzen."));
                        return 0;
                    }

                    PlotzMainMenu.open(player);
                    return 1;
                })
        );
    }

    private void onOpacAddonRegister(OPACServerAddonRegisterEvent event) {
        OpacBridge.registerClaimsTracker(event);
        System.out.println("[Plotz] OPAC tracker registered.");
    }
}