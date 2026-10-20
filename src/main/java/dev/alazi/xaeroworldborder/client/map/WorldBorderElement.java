package dev.alazi.xaeroworldborder.client.map;

/** One straight border segment (either vertical or horizontal) in world coordinates. */
record WorldBorderElement(double startX, double startZ, double endX, double endZ) {
    double centerX() {
        return (startX + endX) * 0.5D;
    }

    double centerZ() {
        return (startZ + endZ) * 0.5D;
    }

    double halfWidth() {
        return Math.abs(endX - startX) * 0.5D;
    }

    double halfHeight() {
        return Math.abs(endZ - startZ) * 0.5D;
    }
}
