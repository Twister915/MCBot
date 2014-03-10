package org.projectbot.util;

import lombok.Data;

@Data
public class Location {
    private final Double x;
    private final Double y;
    private final Double z;
    private final Float pitch;
    private final Float yaw;

    public static Location nextLocation(Location past, Direction direction) {
        return past;
    }
}