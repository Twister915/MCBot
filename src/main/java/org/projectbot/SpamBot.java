package org.projectbot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Synchronized;
import org.projectbot.exception.ConnectException;
import org.projectbot.inter.*;
import org.projectbot.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpamBot extends Thread {
    private volatile boolean running;
    private boolean shouldStartSpamming = false;
    private int index = 0;
    private final String[] lines;
    private String alts = "slayerzoomba@gmail.com:chasel123\nvikram901:nautica\nzoro2011@live.ca:wwe123\nbbstar5613@gmail.com:pierce04\nTheTechPony@yahoo.com:freemcaccount1\ncoolmasterkane:63sen1\ntemperrre@gmail.com:isaiah123\npokemonbeastincod@gmail.com:firefox1234\nlausteven39@hotmail.se:steven123\npdsterling@comcast.net:PdsterlingcreatorofSuperDev\nMadsmotor100:23260427\nkq1003@gmail.com:kaiquander1003\nMakyMeje:12345t\nmatemate999@gmail.com:mate123\nApplehuman:maasikas\nJiSkAvH:hyvess123\nS1L3N7123@GMAIL.COM:dejesus2003\njude.cresswell@btinternet.com:trains123412\nUndeadxIdiot07:christos714\n";
    private List<BotThread> threads;
    private String ip;
    private int port;

    public SpamBot(String ip, int port) {
        this.ip = ip;
        this.port = port;

        String line = null;
        String altLine = null;
        try {
            line = FileUtil.getFileLines("lines.txt");
            altLine = FileUtil.getFileLines("alts.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line == null || altLine == null) {
            System.out.println("Failed to load text from either lines.txt or alts.txt, exiting.");
            System.exit(0);
        }
        this.lines = line.split("\n");
        this.alts = altLine;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private class BotThread extends Thread {
        private final Engine engine;
        private final Account account;
        private final Server server;
        private final SpamBot spamBot;

        public void run() {
            while (this.spamBot.isRunning()) {
                try {
                    Connection connection = this.engine.createConnection(this.account, this.server, this.engine.getNetwork());
                    try {
                        connection.connect();
                    } catch (ConnectException e) {
                        if (e.getFailureReason() == ConnectException.Cause.ACCOUNT_INVALID) {
                            System.out.println("Account invalid " + this.account.toString());
                            invalidateThread(connection);
                            return;
                        }
                        e.printStackTrace();
                        connection.disconnect();
                    }
                    Player player = connection.getCurrentConnection();
                    player.addAttachment(new SpamBot.Attachment());
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while ((this.spamBot.isRunning()) && (connection.isConnected())) {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (this.spamBot.shouldStartSpamming) {
                                player.chat(this.spamBot.getMessage().replaceAll("%r", String.valueOf(ProjectBot.getRandom().nextInt(1000))));
                            }
                        } catch (IllegalStateException ex) {
                            ex.printStackTrace();
                        }
                    }
                    connection.disconnect();
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("Trying to reconnect!");
            }
        }

        @Synchronized
        public void invalidateThread(Connection connection) {
            BotThread toRemove = null;
            for (BotThread thread : SpamBot.this.getThreads()) {
                if (thread.getAccount().equals(connection.getAccount())) {
                    toRemove = thread;
                }
            }
            if (toRemove == null) return;
            SpamBot.this.getThreads().remove(toRemove);
        }
    }

    private static class Attachment extends PlayerAttachment {
        public void onPlayerDeath(Player player) {
            try {
                Thread.sleep(60L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.respawn();
        }
    }

    private String getMessage() {
        String s = this.lines[this.index];
        this.index += 1;
        if (this.index == this.lines.length) {
            this.index = 0;
        }
        return s;
    }

    @Synchronized
    public void run() {
        ProjectBot bot = new ProjectBot();
        Engine engine = bot.getEngineManager().getEngine();
        this.threads = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        for (String s : alts.split("\n")) {
            String[] split = s.split(":");
            if (split.length != 2) {
                accounts.add(engine.getAccount(split[0]));
            } else {
                accounts.add(engine.getAccount(split[0], split[1]));
            }
        }
        Server server = engine.getServer(this.ip, this.port);
        Collections.shuffle(accounts);
        for (Account account : accounts) {
            this.threads.add(new BotThread(engine, account, server, this));
        }
        this.running = true;
        for (BotThread thread : this.threads) {
            thread.start();
            System.out.println("At a total of " + (this.threads.indexOf(thread) + 1) + " account(s)!");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}