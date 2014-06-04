package org.projectbot.inter;

import java.util.List;

public interface PingResult {
    String getMotd();

    List<String> getPlayers();

    Integer getMaxPlayers();

    Integer getPlayersOnline();
}
