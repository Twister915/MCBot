package org.projectbot.inter;

public interface Engine {
    Account getAccount(String username, String password);

    Account getAccount(String username);

    Connection createConnection(Account account, Server server, Network network);

    Server getServer(String address, Integer port);

    Network getNetwork(); //TODO implement some stuff

    EngineMeta getMeta();
}
