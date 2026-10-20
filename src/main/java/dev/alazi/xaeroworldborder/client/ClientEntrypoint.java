package dev.alazi.xaeroworldborder.client;

import dev.alazi.xaeroworldborder.XaeroWorldBorder;
import dev.alazi.xaeroworldborder.network.BorderSyncPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

public final class ClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(BorderSyncPayload.TYPE,
                (payload, context) -> context.client().execute(() -> ClientWorldBorderCache.apply(payload)));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ClientWorldBorderCache.clear());

        if (FabricLoader.getInstance().isModLoaded("xaeroworldmap")) {
            XaeroIntegration.init();
        } else {
            XaeroWorldBorder.LOGGER.info("Xaero's World Map not found, world border overlay disabled");
        }
    }
}
