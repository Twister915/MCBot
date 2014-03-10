package org.projectbot.mcprotocollib;

import lombok.Data;
import org.projectbot.inter.PingResult;

import java.util.List;

@Data
public final class MCPPingResult implements PingResult {
    private final List<String> players;
    private final String motd;
    private final Integer maxPlayers;
    private final Integer playersOnline;
}
