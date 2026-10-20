package dev.alazi.xaeroworldborder.client.map;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

final class WorldBorderRenderContext {
    ResourceKey<Level> mapDimension;
    double cameraX;
    double cameraZ;
    double scale = 1.0D;
    double backgroundCoordinateScale = 1.0D;
    int screenWidth;
    int screenHeight;
    Iterator<WorldBorderElement> elements = Collections.emptyIterator();

    void setElements(List<WorldBorderElement> list) {
        this.elements = list.iterator();
    }
}
