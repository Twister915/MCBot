package org.projectbot.inter;

import lombok.Data;

@Data
public abstract class PlayerAttachment {
    public void onPlayerDeath(Player player) {
    }

    public void onPlayerHit(Player player) {
    }

    public void onPlayerPickup(Player player) {
    }

    public void onPlayerKick(Player player) {
    }
}
