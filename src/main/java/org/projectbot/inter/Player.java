package org.projectbot.inter;

import org.projectbot.util.Location;

import java.util.List;

public interface Player {
    void chat(String message);

    void move(Location location);

    void jump();

    void swingArm();

    void switchSlot(Integer slot);

    void openInventory();

    void respawn();

    Connection getConnection();

    Account getAccount();

    String getUUID();

    String getUsername();

    List<PlayerAttachment> getAttachments();

    void addAttachment(PlayerAttachment attachment);

    void removeAttachment(PlayerAttachment attachment);
}
