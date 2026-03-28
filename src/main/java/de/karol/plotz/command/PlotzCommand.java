package de.karol.plotz.command;

import com.mojang.brigadier.CommandDispatcher;
import de.karol.plotz.menu.PlotzMenuService;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class PlotzCommand {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PlotzCommand::onRegisterCommands);
    }

    private static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("plotz")
                .requires(source -> source.hasPermission(0))
                .executes(ctx -> PlotzMenuService.openMainOverview(ctx.getSource()))
        );
    }
}
