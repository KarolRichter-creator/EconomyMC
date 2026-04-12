package de.karl_der_iii.economymc.mixin;

import de.karl_der_iii.economymc.data.PlotzStore;
import de.karl_der_iii.economymc.menu.PlotzClaimConfirmMenu;
import de.karl_der_iii.economymc.menu.PlotzCreateSaleMenu;
import de.karl_der_iii.economymc.service.LanguageManager;
import de.karl_der_iii.economymc.service.PendingPlotSelectionManager;
import de.karl_der_iii.economymc.service.PendingPlotSelectionManager.ActionType;
import de.karl_der_iii.economymc.service.PlotzLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.pac.common.claims.player.request.ClaimActionRequest;
import xaero.pac.common.server.claims.player.request.PlayerClaimActionRequestHandler;

import java.lang.reflect.Method;
import java.util.Collection;

@Mixin(PlayerClaimActionRequestHandler.class)
public class PlayerClaimActionRequestHandlerMixin {

    @Inject(method = "handle", at = @At("HEAD"), cancellable = true, remap = false)
    private void plotz$interceptClaimAction(ServerPlayer player, ClaimActionRequest request, CallbackInfo ci) {
        try {
            String actionName = detectActionName(request);
            if (actionName == null) {
                return;
            }

            boolean isClaim = actionName.contains("CLAIM") && !actionName.contains("UNCLAIM");
            boolean isUnclaim = actionName.contains("UNCLAIM");

            if (!isClaim && !isUnclaim) {
                return;
            }

            Collection<ChunkPos> chunks = detectChunks(request);
            if (chunks == null || chunks.isEmpty()) {
                return;
            }

            int minX = Integer.MAX_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxZ = Integer.MIN_VALUE;
            int totalPrice = 0;
            boolean capital = false;

            for (ChunkPos chunk : chunks) {
                minX = Math.min(minX, chunk.x);
                minZ = Math.min(minZ, chunk.z);
                maxX = Math.max(maxX, chunk.x);
                maxZ = Math.max(maxZ, chunk.z);

                BlockPos pos = new BlockPos(chunk.x * 16 + 8, 64, chunk.z * 16 + 8);
                totalPrice += PlotzLogic.getClaimPrice(pos);
                if (PlotzLogic.isCapital(pos)) {
                    capital = true;
                }
            }

            ResourceLocation dimension = player.level().dimension().location();

            if (isUnclaim) {
                PendingPlotSelectionManager.PendingSelection selection =
                    new PendingPlotSelectionManager.PendingSelection(
                        ActionType.SALE_DRAFT, dimension, minX, minZ, maxX, maxZ, chunks.size(), capital, totalPrice
                    );
                PendingPlotSelectionManager.set(player, selection);

                String location = PendingPlotSelectionManager.locationString(selection);
                PlotzStore.setDraft(new PlotzStore.SaleDraft(
                    player.getUUID(),
                    player.getGameProfile().getName(),
                    capital ? "Capital Plot" : "Plot",
                    capital,
                    chunks.size(),
                    location,
                    Math.max(totalPrice, 1),
                    LanguageManager.tr("myplots.edit_later"),
                    LanguageManager.tr("myplots.edit_later"),
                    LanguageManager.tr("myplots.edit_later"),
                    false
                ));

                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                    LanguageManager.tr("plots.sell.draft.created")
                ));
                PlotzCreateSaleMenu.open(player);
                ci.cancel();
                return;
            }

            if (isClaim) {
                PendingPlotSelectionManager.set(player, new PendingPlotSelectionManager.PendingSelection(
                    ActionType.BUY, dimension, minX, minZ, maxX, maxZ, chunks.size(), capital, totalPrice
                ));

                PlotzClaimConfirmMenu.open(player);
                ci.cancel();
            }
        } catch (Exception ignored) {
        }
    }

    private static String detectActionName(ClaimActionRequest request) {
        try {
            for (Method m : request.getClass().getMethods()) {
                if (m.getParameterCount() != 0) {
                    continue;
                }
                Class<?> rt = m.getReturnType();
                if (rt == String.class || rt.isEnum()) {
                    Object value = m.invoke(request);
                    if (value != null) {
                        String s = String.valueOf(value).toUpperCase();
                        if (s.contains("CLAIM") || s.contains("UNCLAIM")) {
                            return s;
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Collection<ChunkPos> detectChunks(ClaimActionRequest request) {
        try {
            for (Method m : request.getClass().getMethods()) {
                if (m.getParameterCount() != 0) {
                    continue;
                }
                if (!Collection.class.isAssignableFrom(m.getReturnType())) {
                    continue;
                }
                Object value = m.invoke(request);
                if (!(value instanceof Collection<?> col) || col.isEmpty()) {
                    continue;
                }
                Object first = col.iterator().next();
                if (first instanceof ChunkPos) {
                    return (Collection<ChunkPos>) col;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
