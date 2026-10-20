package dev.alazi.xaeroworldborder.client;

import dev.alazi.xaeroworldborder.XaeroWorldBorder;
import dev.alazi.xaeroworldborder.client.map.WorldBorderElementRenderer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/** Everything that touches Xaero classes lives here; only loaded when Xaero's World Map is present. */
final class XaeroIntegration {
    /** Last Xaero render handler we registered on (it can be recreated, e.g. on reloads). */
    private static Object registeredOn;

    private XaeroIntegration() {}

    static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tryRegister());
    }

    private static void tryRegister() {
        if (xaero.map.WorldMap.loaded && xaero.map.WorldMap.mapElementRenderHandler != null
                && registeredOn != xaero.map.WorldMap.mapElementRenderHandler) {
            xaero.map.WorldMap.mapElementRenderHandler.add(WorldBorderElementRenderer.create());
            registeredOn = xaero.map.WorldMap.mapElementRenderHandler;
            XaeroWorldBorder.LOGGER.info("Registered world border renderer on Xaero's World Map");
        }
    }
}
