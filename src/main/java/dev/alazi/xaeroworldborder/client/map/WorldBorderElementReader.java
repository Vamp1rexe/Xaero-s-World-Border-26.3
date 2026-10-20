package dev.alazi.xaeroworldborder.client.map;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import xaero.map.element.render.ElementReader;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.RightClickOption;

final class WorldBorderElementReader
        extends ElementReader<WorldBorderElement, WorldBorderRenderContext, WorldBorderElementRenderer> {
    /** Large box so the element is never culled while any part of it can be visible. */
    private static final int BOX = 30000000;

    @Override
    public boolean isHidden(WorldBorderElement element, WorldBorderRenderContext context) {
        return false;
    }

    @Override
    public double getRenderX(WorldBorderElement element, WorldBorderRenderContext context, float partialTicks) {
        return element.centerX();
    }

    @Override
    public double getRenderZ(WorldBorderElement element, WorldBorderRenderContext context, float partialTicks) {
        return element.centerZ();
    }

    @Override
    public int getInteractionBoxLeft(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return getRenderBoxLeft(e, c, p);
    }

    @Override
    public int getInteractionBoxRight(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return getRenderBoxRight(e, c, p);
    }

    @Override
    public int getInteractionBoxTop(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return getRenderBoxTop(e, c, p);
    }

    @Override
    public int getInteractionBoxBottom(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return getRenderBoxBottom(e, c, p);
    }

    @Override
    public int getRenderBoxLeft(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return -BOX;
    }

    @Override
    public int getRenderBoxRight(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return BOX;
    }

    @Override
    public int getRenderBoxTop(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return -BOX;
    }

    @Override
    public int getRenderBoxBottom(WorldBorderElement e, WorldBorderRenderContext c, float p) {
        return BOX;
    }

    @Override
    public int getLeftSideLength(WorldBorderElement element, Minecraft mc) {
        return 0;
    }

    @Override
    public String getMenuName(WorldBorderElement element) {
        return "World Border";
    }

    @Override
    public String getFilterName(WorldBorderElement element) {
        return "World Border";
    }

    @Override
    public int getMenuTextFillLeftPadding(WorldBorderElement element) {
        return 0;
    }

    @Override
    public int getRightClickTitleBackgroundColor(WorldBorderElement element) {
        return 0;
    }

    @Override
    public boolean shouldScaleBoxWithOptionalScale() {
        return false;
    }

    @Override
    public boolean isInteractable(ElementRenderLocation location, WorldBorderElement element) {
        return false;
    }

    @Override
    public ArrayList<RightClickOption> getRightClickOptions(WorldBorderElement element, IRightClickableElement target) {
        return new ArrayList<>();
    }

    @Override
    public boolean isRightClickValid(WorldBorderElement element) {
        return false;
    }
}
