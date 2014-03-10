package org.projectbot;

import lombok.Data;
import org.projectbot.exception.ConnectException;
import org.projectbot.inter.Connection;
import org.projectbot.inter.Engine;
import org.projectbot.inter.Player;
import org.projectbot.mcprotocollib.MCPEngine;

import java.lang.reflect.InvocationTargetException;

@Data
public final class ProjectBot {
    private final EngineManager engineManager = new EngineManager();
    public static void main(String[] args) {
        /*
        Test implementation here.
         */
        ProjectBot projectBot = new ProjectBot();
        try {
            projectBot.getEngineManager().registerEngine(MCPEngine.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        Engine engine = projectBot.getEngineManager().getEngine();
        Connection connection = engine.createConnection(engine.getAccount("no@no.com", "nopassword"), engine.getServer("mc.tbnr.net", 25565), engine.getNetwork());
        Player connect;
        try {
            connect = connection.connect();
        } catch (ConnectException e) {
            e.printStackTrace();
            connection.disconnect();
            return;
        }
        //connect.chat("testing Joey's minecraft bot!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //connect.chat("Testing again a second later!");
        connection.disconnect();
    }
}
