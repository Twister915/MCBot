package org.projectbot.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ConnectException extends Exception {
    private final String message;
    private final Cause failureReason;
    public static enum Cause {
        NETWORK,
        ACCOUNT_INVALID,
        SERVER_INVALID;
    }
}
