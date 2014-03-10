package org.projectbot.mcprotocollib;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Data
public class MCPConnectionThread extends Thread {
    private final MCPConnection connection;
    @Setter(AccessLevel.NONE) private MCPPlayer player = null;
    public MCPConnectionThread(MCPConnection connection) {
        super("ConnectionThread");
        this.connection = connection;
    }
    public void run() {
        this.player = connect();
    }
    private MCPPlayer connect() {
        getConnection().getClient().getSession().connect();
        while (!connection.isJoinedServer()) try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MCPPlayer(connection, connection.getAccount(), connection.getAccount().getLogin(), null, connection.getClient());
    }
}
