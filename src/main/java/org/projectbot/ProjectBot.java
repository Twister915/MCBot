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
        int port = 25565;
        if (args.length == 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                port = 25565;
            }
        }
        SpamBot spamBot = new SpamBot(args[0], port);
        spamBot.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("start")) spamBot.setShouldStartSpamming(true);
            if (s.equalsIgnoreCase("pause")) spamBot.setShouldStartSpamming(false);
            if (s.equalsIgnoreCase("end")) System.exit(0);
        }
    }
}
