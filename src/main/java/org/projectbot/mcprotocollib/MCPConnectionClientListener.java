package org.projectbot.mcprotocollib;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.projectbot.inter.PlayerAttachment;
import org.projectbot.util.Location;
import org.spacehq.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityStatusPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.packet.Packet;

@EqualsAndHashCode(callSuper = false)
@Data
public final class MCPConnectionClientListener extends SessionAdapter {
    private final MCPConnection connection;

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet1 = event.getPacket();
        MCPPlayer currentConnection = connection.getCurrentConnection();
        if (packet1 instanceof ServerJoinGamePacket) {
            connection.joinedServerNotification();
            currentConnection.setEntityId(((ServerJoinGamePacket) packet1).getEntityId());
        }
        if (packet1 instanceof ServerSpawnPositionPacket) {
            ServerSpawnPositionPacket serverSpawnPositionPacket = (ServerSpawnPositionPacket) packet1;
            currentConnection.setLocation(new Location(
                    (double) serverSpawnPositionPacket.getX(),
                    (double) serverSpawnPositionPacket.getY(),
                    (double) serverSpawnPositionPacket.getZ(),
                    null, null));
        }
        if (packet1 instanceof ServerDisconnectPacket) {
            ServerDisconnectPacket packet = (ServerDisconnectPacket) packet1;
            System.out.println("Disconnected for " + packet.getReason());
            connection.forciblyClosed();
        }
        if (packet1 instanceof ServerEntityStatusPacket) {
            ServerEntityStatusPacket packet = (ServerEntityStatusPacket)packet1;
            if (!(packet.getEntityId() == currentConnection.getEntityId() && packet.getStatus() == ServerEntityStatusPacket.Status.DEAD)) {
                for (PlayerAttachment playerAttachment : currentConnection.getAttachments()) {
                    playerAttachment.onPlayerDeath(currentConnection);
                }
            }
        }
    }
}
