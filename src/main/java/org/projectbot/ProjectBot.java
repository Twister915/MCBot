package org.projectbot;

import lombok.Data;
import lombok.Getter;
import org.projectbot.mcprotocollib.MCPEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Scanner;

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
        Test test = new Test(args[0]);
        test.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("start")) test.setShouldStartSpamming(true);
            if (s.equalsIgnoreCase("pause")) test.setShouldStartSpamming(false);
        }
    }
}
