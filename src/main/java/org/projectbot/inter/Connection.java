package org.projectbot.inter;

import org.projectbot.exception.ConnectException;

public interface Connection {
    Player connect() throws ConnectException;
    void disconnect();
    Account getAccount();
    Network getNetwork();
    Server getServer();
    boolean isConnected();
    Player getCurrentConnection();
}
