package org.projectbot.mcprotocollib;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.projectbot.inter.Player;
import org.projectbot.inter.PlayerAttachment;
import org.projectbot.mcprotocollib.packets.MCPMovePacket;
import org.projectbot.util.Location;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.packetlib.Client;

import java.util.ArrayList;
import java.util.List;

@Data
public final class MCPPlayer implements Player {
    private final MCPConnection connection;
    private final MCPAccount account;
    private final String username;
    private final String UUID;

    private final List<PlayerAttachment> attachments = new ArrayList<>();

    /*
     Player
     */
    private final Client mcpClient;
    @Setter(AccessLevel.PACKAGE) private Location location;
    @Setter(AccessLevel.PACKAGE) private Integer entityId;

    private void validateSendPacket() {
        if (!connection.isJoinedServer()) throw new IllegalStateException("You cannot send a packet before you join!");
    }

    @Override
    public void chat(String message) {
        validateSendPacket();
        getMcpClient().getSession().send(new ClientChatPacket(message));
    }

    @Override
    public void move(Location location) {
        validateSendPacket();
        getMcpClient().getSession().send(MCPMovePacket.movePacket(this.location, location));
    }

    @Override
    public void jump() {

    }

    @Override
    public void swingArm() {

    }

    @Override
    public void switchSlot(Integer slot) {

    }

    @Override
    public void openInventory() {
        getMcpClient().getSession().send(new ClientRequestPacket(ClientRequestPacket.Request.OPEN_INVENTORY_ACHIEVEMENT));
    }

    @Override
    public void respawn() {
        getMcpClient().getSession().send(new ClientRequestPacket(ClientRequestPacket.Request.RESPAWN));
    }

    @Override
    public void addAttachment(PlayerAttachment attachment) {
        this.attachments.add(attachment);
    }

    @Override
    public void removeAttachment(PlayerAttachment attachment) {
        this.attachments.remove(attachment);
    }
}
