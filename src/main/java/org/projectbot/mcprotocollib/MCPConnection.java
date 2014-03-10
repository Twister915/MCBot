package org.projectbot.mcprotocollib;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.projectbot.exception.ConnectException;
import org.projectbot.inter.Connection;
import org.projectbot.inter.Network;
import org.projectbot.inter.Player;
import org.spacehq.mc.auth.exceptions.AuthenticationException;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

@Data
public final class MCPConnection implements Connection {
    private final MCPAccount account;
    private final MCPServer server;
    private final Network network;
    @Setter(AccessLevel.NONE) private MCPPlayer currentConnection = null;

    /*
     MCProtocolSpecifics
     */

    @Setter(AccessLevel.NONE) private MinecraftProtocol protocol;
    @Setter(AccessLevel.NONE) private Client client;
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.PACKAGE) private boolean joinedServer = false;

    @Override
    public Player connect() throws ConnectException {
        try {
            protocol = new MinecraftProtocol(account.getLogin(), account.getPassword(), false);
        } catch (AuthenticationException e) {
            throw new ConnectException("Could not login with account provided!", ConnectException.Cause.ACCOUNT_INVALID);
        }
        client = new Client(server.getAddress(), server.getPort(), protocol, new TcpSessionFactory());
        Session session = client.getSession();
        session.addListener(new MCPConnectionClientListener(this));
        MCPConnectionThread mcpConnectionThread = new MCPConnectionThread(this);
        mcpConnectionThread.start();
        while (mcpConnectionThread.getPlayer() == null) try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.currentConnection = mcpConnectionThread.getPlayer();
        return this.currentConnection;
    }

    void joinedServerNotification() {
        this.joinedServer = true;
    }

    @Override
    public void disconnect() {
        joinedServer = false;
        this.client.getSession().disconnect("quit");
    }

    @Override
    public boolean isConnected() {
        return currentConnection != null;
    }

    void forciblyClosed() {
        this.currentConnection = null;
        this.joinedServer = false;
    }
}
