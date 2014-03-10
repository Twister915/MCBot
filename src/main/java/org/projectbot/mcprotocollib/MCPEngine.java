package org.projectbot.mcprotocollib;

import lombok.Data;
import org.projectbot.inter.*;

@EngineMeta(
        name = "MCProtocolLib",
        defaultEngine = true,
        engineAuthor = "org.spacehq"
)
@Data
public final class MCPEngine implements Engine {
    private final EngineMeta meta;
    @Override
    public Account getAccount(String username, String password) {
        return new MCPAccount(username, password);
    }

    @Override
    public Connection createConnection(Account account, Server server, Network network) {
        try {
            return new MCPConnection((MCPAccount) account, (MCPServer) server, network);
        } catch (ClassCastException ex) {
            return null;
        }
    }

    @Override
    public Server getServer(String address, Integer port) {
        return new MCPServer(address, port);
    }

    @Override
    public Network getNetwork() {
        return new Network() {};
    }
}
