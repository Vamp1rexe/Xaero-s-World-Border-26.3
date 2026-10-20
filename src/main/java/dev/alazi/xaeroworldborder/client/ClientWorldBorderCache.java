package dev.alazi.xaeroworldborder.client;

import dev.alazi.xaeroworldborder.WorldBorderSnapshot;
import dev.alazi.xaeroworldborder.network.BorderSyncPayload;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class ClientWorldBorderCache {
    private static final Map<ResourceKey<Level>, WorldBorderSnapshot> SERVER_BORDERS = new HashMap<>();

    private ClientWorldBorderCache() {}

    public static void apply(BorderSyncPayload payload) {
        SERVER_BORDERS.clear();
        for (BorderSyncPayload.Entry entry : payload.entries()) {
            SERVER_BORDERS.put(entry.dimension(), entry.snapshot());
        }
    }

    public static WorldBorderSnapshot get(ResourceKey<Level> dimension) {
        return SERVER_BORDERS.get(dimension);
    }

    public static void clear() {
        SERVER_BORDERS.clear();
    }
}
