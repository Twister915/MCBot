package org.projectbot.mcprotocollib;

import lombok.Data;
import org.projectbot.inter.PingResult;
import org.projectbot.inter.Server;

import java.net.ConnectException;

@Data
public final class MCPServer implements Server {
    private final String address;
    private final Integer port;

    @Override
    public PingResult ping() throws ConnectException {
        return null;
    }
}
