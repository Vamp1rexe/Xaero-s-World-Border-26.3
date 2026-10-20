package dev.alazi.xaeroworldborder.client;

import dev.alazi.xaeroworldborder.XaeroWorldBorder;
import dev.alazi.xaeroworldborder.client.map.WorldBorderElementRenderer;
import dev.alazi.xaeroworldborder.network.BorderSyncPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

public final class ClientEntrypoint implements ClientModInitializer {
    /** Last Xaero render handler we registered on (it can be recreated, e.g. on reloads). */
    private static Object registeredOn;

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(BorderSyncPayload.TYPE,
                (payload, context) -> context.client().execute(() -> ClientWorldBorderCache.apply(payload)));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ClientWorldBorderCache.clear());

        if (!FabricLoader.getInstance().isModLoaded("xaeroworldmap")) {
            XaeroWorldBorder.LOGGER.info("Xaero's World Map not found, world border overlay disabled");
            return;
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> tryRegister());
    }

    private static void tryRegister() {
        // Only touches Xaero classes after we know the mod is present.
        if (xaero.map.WorldMap.loaded && xaero.map.WorldMap.mapElementRenderHandler != null
                && registeredOn != xaero.map.WorldMap.mapElementRenderHandler) {
            xaero.map.WorldMap.mapElementRenderHandler.add(WorldBorderElementRenderer.create());
            registeredOn = xaero.map.WorldMap.mapElementRenderHandler;
            XaeroWorldBorder.LOGGER.info("Registered world border renderer on Xaero's World Map");
        }
    }
}
