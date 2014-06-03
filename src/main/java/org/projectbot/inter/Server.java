package org.projectbot.inter;

import java.net.ConnectException;

public interface Server {
    String getAddress();

    Integer getPort();

    PingResult ping() throws ConnectException;
}
