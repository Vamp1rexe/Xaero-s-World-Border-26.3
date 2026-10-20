package dev.alazi.xaeroworldborder;

import net.minecraft.world.level.border.WorldBorder;

public record WorldBorderSnapshot(double minX, double minZ, double maxX, double maxZ) {
    private static final double VANILLA_MAX_SIZE = 5.999999E7D;

    public static WorldBorderSnapshot from(WorldBorder border) {
        return new WorldBorderSnapshot(border.getMinX(), border.getMinZ(), border.getMaxX(), border.getMaxZ());
    }

    /** The default (vanilla max size) border is not worth drawing. */
    public boolean visible() {
        return maxX - minX < VANILLA_MAX_SIZE || maxZ - minZ < VANILLA_MAX_SIZE;
    }
}
