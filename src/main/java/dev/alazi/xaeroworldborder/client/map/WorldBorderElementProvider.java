package dev.alazi.xaeroworldborder.client.map;

import dev.alazi.xaeroworldborder.WorldBorderSnapshot;
import dev.alazi.xaeroworldborder.client.ClientWorldBorderCache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderProvider;

final class WorldBorderElementProvider extends ElementRenderProvider<WorldBorderElement, WorldBorderRenderContext> {
    private static final double SCREEN_MARGIN_MULTIPLIER = 1.0D;

    @Override
    public void begin(ElementRenderLocation location, WorldBorderRenderContext context) {
        if (location != ElementRenderLocation.WORLD_MAP || context.mapDimension == null) {
            context.setElements(Collections.emptyList());
            return;
        }
        WorldBorderSnapshot border = getBorder(context.mapDimension);
        if (border == null || !border.visible()) {
            context.setElements(Collections.emptyList());
            return;
        }

        double bg = Math.max(context.backgroundCoordinateScale, 1.0E-6D);
        double scale = Math.max(context.scale, 1.0E-6D);
        double halfW = context.screenWidth * 0.5D * bg / scale;
        double halfH = context.screenHeight * 0.5D * bg / scale;
        double margin = Math.max(context.screenWidth, context.screenHeight) * SCREEN_MARGIN_MULTIPLIER * bg / scale;
        double visX0 = context.cameraX - halfW - margin;
        double visX1 = context.cameraX + halfW + margin;
        double visZ0 = context.cameraZ - halfH - margin;
        double visZ1 = context.cameraZ + halfH + margin;

        List<WorldBorderElement> list = new ArrayList<>(4);
        addVertical(list, border.minX(), border.minZ(), border.maxZ(), visX0, visX1, visZ0, visZ1);
        addVertical(list, border.maxX(), border.minZ(), border.maxZ(), visX0, visX1, visZ0, visZ1);
        addHorizontal(list, border.minZ(), border.minX(), border.maxX(), visX0, visX1, visZ0, visZ1);
        addHorizontal(list, border.maxZ(), border.minX(), border.maxX(), visX0, visX1, visZ0, visZ1);
        context.setElements(list);
    }

    @Override
    public boolean hasNext(ElementRenderLocation location, WorldBorderRenderContext context) {
        return context.elements.hasNext();
    }

    @Override
    public WorldBorderElement getNext(ElementRenderLocation location, WorldBorderRenderContext context) {
        return context.elements.next();
    }

    @Override
    public void end(ElementRenderLocation location, WorldBorderRenderContext context) {
        context.setElements(Collections.emptyList());
    }

    private static WorldBorderSnapshot getBorder(ResourceKey<Level> dimension) {
        WorldBorderSnapshot cached = ClientWorldBorderCache.get(dimension);
        if (cached != null) {
            return cached;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension().equals(dimension)) {
            return WorldBorderSnapshot.from(mc.level.getWorldBorder());
        }
        return null;
    }

    private static void addVertical(List<WorldBorderElement> out, double x, double z0, double z1,
                                    double visX0, double visX1, double visZ0, double visZ1) {
        if (x < visX0 || x > visX1) {
            return;
        }
        double a = Math.max(z0, visZ0);
        double b = Math.min(z1, visZ1);
        if (b > a) {
            out.add(new WorldBorderElement(x, a, x, b));
        }
    }

    private static void addHorizontal(List<WorldBorderElement> out, double z, double x0, double x1,
                                      double visX0, double visX1, double visZ0, double visZ1) {
        if (z < visZ0 || z > visZ1) {
            return;
        }
        double a = Math.max(x0, visX0);
        double b = Math.min(x1, visX1);
        if (b > a) {
            out.add(new WorldBorderElement(a, z, b, z));
        }
    }
}
