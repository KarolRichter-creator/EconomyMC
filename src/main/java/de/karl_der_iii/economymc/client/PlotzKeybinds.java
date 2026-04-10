package de.karl_der_iii.economymc.client;

import com.mojang.blaze3d.platform.InputConstants;
import de.karl_der_iii.economymc.PlotzMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = PlotzMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
public final class PlotzKeybinds {
    public static final Lazy<KeyMapping> OPEN_MENU = Lazy.of(() -> new KeyMapping(
        "key.plotz.open_menu",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_C,
        "key.categories.plotz"
    ));

    private PlotzKeybinds() {}

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_MENU.get());
    }

    @EventBusSubscriber(modid = PlotzMod.MOD_ID, value = net.neoforged.api.distmarker.Dist.CLIENT)
    public static final class RuntimeEvents {
        private RuntimeEvents() {}

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            while (OPEN_MENU.get().consumeClick()) {
                if (mc.screen == null && mc.player.connection != null) {
                    mc.player.connection.sendCommand("ec");
                }
            }
        }
    }
}
