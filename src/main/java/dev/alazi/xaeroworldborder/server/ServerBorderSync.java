package dev.alazi.xaeroworldborder.server;

import dev.alazi.xaeroworldborder.WorldBorderSnapshot;
import dev.alazi.xaeroworldborder.network.BorderSyncPayload;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public final class ServerBorderSync {
    private static final int RESYNC_INTERVAL_TICKS = 100;
    private static int ticks;

    private ServerBorderSync() {}

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sendTo(handler.getPlayer(), server));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (++ticks < RESYNC_INTERVAL_TICKS) {
                return;
            }
            ticks = 0;
            BorderSyncPayload payload = createPayload(server);
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                send(player, payload);
            }
        });
    }

    private static void sendTo(ServerPlayer player, MinecraftServer server) {
        send(player, createPayload(server));
    }

    private static void send(ServerPlayer player, BorderSyncPayload payload) {
        if (ServerPlayNetworking.canSend(player, BorderSyncPayload.TYPE)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    private static BorderSyncPayload createPayload(MinecraftServer server) {
        List<BorderSyncPayload.Entry> entries = new ArrayList<>();
        for (ServerLevel level : server.getAllLevels()) {
            entries.add(new BorderSyncPayload.Entry(level.dimension(), WorldBorderSnapshot.from(level.getWorldBorder())));
        }
        return new BorderSyncPayload(entries);
    }
}
