package org.projectbot.mcprotocollib.packets;

import org.projectbot.util.Location;
import org.spacehq.mc.protocol.packet.ingame.client.entity.player.ClientPlayerMovementPacket;

public final class MCPMovePacket extends ClientPlayerMovementPacket {
    private MCPMovePacket() {}
    public static MCPMovePacket movePacket(Location location, Location oldLocation) {
        MCPMovePacket mcpMovePacket = new MCPMovePacket();
        if (!oldLocation.getPitch().equals(location.getPitch()) || !oldLocation.getYaw().equals(location.getYaw())) {
            //Must do a rotate packet
            mcpMovePacket.rot = true;
            mcpMovePacket.yaw = location.getYaw();
            mcpMovePacket.pitch = location.getPitch();
        }
        if (!oldLocation.getX().equals(location.getX()) || !oldLocation.getY().equals(location.getY()) || !oldLocation.getZ().equals(location.getZ())) {
            mcpMovePacket.pos = true;
            mcpMovePacket.x = location.getX();
            mcpMovePacket.feetY = location.getY();
            mcpMovePacket.headY = location.getY() + 1.0d;
            mcpMovePacket.z = location.getZ();
        }
        return mcpMovePacket;
    }
}
