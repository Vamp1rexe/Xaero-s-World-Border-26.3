package dev.alazi.xaeroworldborder.client.map;

import xaero.lib.client.graphics.XaeroBufferProvider;
import xaero.map.element.MapElementGraphics;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;

public final class WorldBorderElementRenderer
        extends ElementRenderer<WorldBorderElement, WorldBorderRenderContext, WorldBorderElementRenderer> {
    private static final int BORDER_COLOR = 0xD0FF3030;
    private static final int SHADOW_COLOR = 0x90000000;

    private WorldBorderElementRenderer() {
        super(new WorldBorderRenderContext(), new WorldBorderElementProvider(), new WorldBorderElementReader());
    }

    public static WorldBorderElementRenderer create() {
        return new WorldBorderElementRenderer();
    }

    @Override
    public void preRender(ElementRenderInfo info, XaeroBufferProvider buffers,
                          MultiTextureRenderTypeRendererProvider multiTextureRenderers, boolean shadow) {
        WorldBorderRenderContext ctx = getContext();
        ctx.mapDimension = info.mapDimension;
        ctx.cameraX = info.renderPos.x;
        ctx.cameraZ = info.renderPos.z;
        ctx.scale = info.scale;
        ctx.backgroundCoordinateScale = info.backgroundCoordinateScale;
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        ctx.screenWidth = mc.getWindow().getGuiScaledWidth();
        ctx.screenHeight = mc.getWindow().getGuiScaledHeight();
    }

    @Override
    public void postRender(ElementRenderInfo info, XaeroBufferProvider buffers,
                           MultiTextureRenderTypeRendererProvider multiTextureRenderers, boolean shadow) {}

    @Override
    public void renderElementShadow(WorldBorderElement element, boolean hovered, float optionalScale, double partialX,
                                    double partialY, ElementRenderInfo info, MapElementGraphics graphics,
                                    XaeroBufferProvider buffers,
                                    MultiTextureRenderTypeRendererProvider multiTextureRenderers) {}

    @Override
    public boolean renderElement(WorldBorderElement element, boolean hovered, double optionalDepth,
                                 float optionalScale, double partialX, double partialY, ElementRenderInfo info,
                                 MapElementGraphics graphics, XaeroBufferProvider buffers,
                                 MultiTextureRenderTypeRendererProvider multiTextureRenderers) {
        drawSegment(graphics, element, partialX, partialY, 4.0D, SHADOW_COLOR);
        drawSegment(graphics, element, partialX, partialY, 2.0D, BORDER_COLOR);
        return true;
    }

    @Override
    public boolean shouldRender(ElementRenderLocation location, boolean shadow) {
        return location == ElementRenderLocation.WORLD_MAP && !shadow;
    }

    @Override
    public int getOrder() {
        return 150;
    }

    @Override
    public boolean shouldBeDimScaled() {
        return false;
    }

    private void drawSegment(MapElementGraphics graphics, WorldBorderElement e, double partialX, double partialY,
                             double thickness, int color) {
        double scale = Math.max(getContext().scale, 1.0E-6D);
        double halfW = e.halfWidth() * scale;
        double halfH = e.halfHeight() * scale;
        double halfThickness = thickness * scale * 0.5D;
        int x0 = (int) Math.floor(partialX - halfW - halfThickness);
        int x1 = (int) Math.ceil(partialX + halfW + halfThickness);
        int y0 = (int) Math.floor(partialY - halfH - halfThickness);
        int y1 = (int) Math.ceil(partialY + halfH + halfThickness);
        if (x1 <= x0) {
            x1 = x0 + 1;
        }
        if (y1 <= y0) {
            y1 = y0 + 1;
        }
        graphics.fill(x0, y0, x1, y1, color);
    }
}
