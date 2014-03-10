package org.projectbot.inter;

import org.projectbot.util.Location;

public interface Player {
    void chat(String message);
    void move(Location location);
    void jump();
    void swingArm();
    void switchSlot(Integer slot);

    Connection getConnection();
    Account getAccount();

    String getUUID();
    String getUsername();
}
