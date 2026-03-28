package de.karol.plotz;

import com.mojang.brigadier.CommandDispatcher;
import de.karol.plotz.menu.PlotzMainMenu;
import de.karol.plotz.service.CapitalAreaManager;
import de.karol.plotz.service.DraftInputManager;
import de.karol.plotz.service.OpacBridge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import xaero.pac.common.event.api.OPACServerAddonRegisterEvent;

@Mod(PlotzMod.MOD_ID)
public class PlotzMod {
    public static final String MOD_ID = "plotz";

    public PlotzMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener(this::onOpacAddonRegister);
        NeoForge.EVENT_BUS.addListener(this::onServerChat);
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

        dispatcher.register(
            Commands.literal("plotzadmin")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("pos1").executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                        source.sendFailure(Component.literal("Nur Spieler können diesen Befehl benutzen."));
                        return 0;
                    }

                    BlockPos pos = player.blockPosition();
                    CapitalAreaManager.setPos1(pos);
                    source.sendSuccess(() -> Component.literal("§aCapital pos1 set to: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), false);
                    return 1;
                }))
                .then(Commands.literal("pos2").executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                        source.sendFailure(Component.literal("Nur Spieler können diesen Befehl benutzen."));
                        return 0;
                    }

                    BlockPos pos = player.blockPosition();
                    CapitalAreaManager.setPos2(pos);
                    source.sendSuccess(() -> Component.literal("§aCapital pos2 set to: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), false);
                    return 1;
                }))
                .then(Commands.literal("setcapital").executes(ctx -> {
                    if (!CapitalAreaManager.canCreateArea()) {
                        ctx.getSource().sendFailure(Component.literal("§cSetze zuerst /plotzadmin pos1 und /plotzadmin pos2."));
                        return 0;
                    }

                    CapitalAreaManager.applyArea();
                    ctx.getSource().sendSuccess(() -> Component.literal("§aCapital area saved."), false);
                    return 1;
                }))
                .then(Commands.literal("clearcapital").executes(ctx -> {
                    CapitalAreaManager.clearArea();
                    ctx.getSource().sendSuccess(() -> Component.literal("§aCapital area cleared."), false);
                    return 1;
                }))
        );
    }

    private void onOpacAddonRegister(OPACServerAddonRegisterEvent event) {
        OpacBridge.registerClaimsTracker(event);
        System.out.println("[Plotz] OPAC tracker registered.");
    }

    private void onServerChat(ServerChatEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            boolean handled = DraftInputManager.handleChat(player, event.getRawText());
            if (handled) {
                event.setCanceled(true);
            }
        }
    }
}