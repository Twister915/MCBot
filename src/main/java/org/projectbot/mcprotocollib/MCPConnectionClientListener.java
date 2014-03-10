package org.projectbot.mcprotocollib;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.projectbot.util.Location;
import org.spacehq.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

@EqualsAndHashCode(callSuper = false)
@Data
public final class MCPConnectionClientListener extends SessionAdapter {
    private final MCPConnection connection;

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerJoinGamePacket) {
            connection.joinedServerNotification();
        }
        if (event.getPacket() instanceof ServerSpawnPositionPacket) {
            ServerSpawnPositionPacket serverSpawnPositionPacket = event.getPacket();
            connection.getCurrentConnection().setLocation(new Location(
                    (double)serverSpawnPositionPacket.getX(),
                    (double)serverSpawnPositionPacket.getY(),
                    (double)serverSpawnPositionPacket.getZ(),
                    null, null));
        }
        if (event.getPacket() instanceof ServerDisconnectPacket) {
            ServerDisconnectPacket packet = event.getPacket();
            connection.forciblyClosed();
        }
    }
}
