package org.projectbot.mcprotocollib;

import lombok.Data;
import org.projectbot.inter.Account;

@Data
public final class MCPAccount implements Account {
    private final String login;
    private final String password;
    private final boolean offline;
}
