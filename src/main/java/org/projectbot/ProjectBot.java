package org.projectbot;

import lombok.Data;
import lombok.Getter;
import org.projectbot.mcprotocollib.MCPEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

@Data
public final class ProjectBot {
    private final EngineManager engineManager = new EngineManager();
    @Getter private static final Random random = new Random();

    {
        try {
            engineManager.registerEngine(MCPEngine.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        /*
        Test implementation here.
         */
        Test test = new Test();
        test.start();
    }
}
