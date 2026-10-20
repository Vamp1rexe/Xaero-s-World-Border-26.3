# Xaero's World Border (Fabric, Minecraft 26.3)

Fabric port for Minecraft **26.3** of the Forge mod *Xaero's World Border* by Alazi (MIT).
Draws the world border on **Xaero's World Map** (>= 1.47.0).

- Client only: the border of the loaded level is drawn from the client's own world border data.
- Client + server: with the mod on the server too, the server sends the border of every dimension
  (on join and every 5 seconds), so the border is also shown for dimensions you are not currently in.

Requires Fabric Loader, Fabric API and Xaero's World Map. The server does not need Xaero.
