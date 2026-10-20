package dev.alazi.xaeroworldborder;

import dev.alazi.xaeroworldborder.network.BorderSyncPayload;
import dev.alazi.xaeroworldborder.server.ServerBorderSync;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XaeroWorldBorder implements ModInitializer {
    public static final String MOD_ID = "xaeroworldborder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.clientboundPlay().register(BorderSyncPayload.TYPE, BorderSyncPayload.CODEC);
        ServerBorderSync.register();
    }
}
