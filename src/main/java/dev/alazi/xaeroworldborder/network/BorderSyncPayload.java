package dev.alazi.xaeroworldborder.network;

import dev.alazi.xaeroworldborder.WorldBorderSnapshot;
import dev.alazi.xaeroworldborder.XaeroWorldBorder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record BorderSyncPayload(List<Entry> entries) implements CustomPacketPayload {
    public record Entry(ResourceKey<Level> dimension, WorldBorderSnapshot snapshot) {}

    public static final CustomPacketPayload.Type<BorderSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(XaeroWorldBorder.MOD_ID, "main"));

    public static final StreamCodec<FriendlyByteBuf, BorderSyncPayload> CODEC =
            StreamCodec.of(BorderSyncPayload::encode, BorderSyncPayload::decode);

    private static void encode(FriendlyByteBuf buf, BorderSyncPayload payload) {
        buf.writeVarInt(payload.entries.size());
        for (Entry entry : payload.entries) {
            buf.writeUtf(entry.dimension().identifier().toString());
            buf.writeDouble(entry.snapshot().minX());
            buf.writeDouble(entry.snapshot().minZ());
            buf.writeDouble(entry.snapshot().maxX());
            buf.writeDouble(entry.snapshot().maxZ());
        }
    }

    private static BorderSyncPayload decode(FriendlyByteBuf buf) {
        int count = buf.readVarInt();
        List<Entry> entries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Identifier id = Identifier.parse(buf.readUtf());
            WorldBorderSnapshot snapshot =
                    new WorldBorderSnapshot(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
            entries.add(new Entry(ResourceKey.create(Registries.DIMENSION, id), snapshot));
        }
        return new BorderSyncPayload(entries);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
