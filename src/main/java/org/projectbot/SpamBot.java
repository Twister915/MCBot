package org.projectbot;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.projectbot.exception.ConnectException;
import org.projectbot.inter.Account;
import org.projectbot.inter.Connection;
import org.projectbot.inter.Engine;
import org.projectbot.inter.Player;
import org.projectbot.inter.PlayerAttachment;
import org.projectbot.inter.Server;

public class SpamBot
        extends Thread {
    private volatile boolean running;
    private final Random random;
    private boolean shouldStartSpamming;
    private int index;
    private final String[] song;
    String ip;

    public boolean isRunning() {
        return this.running;
    }

    public Random getRandom() {
        return this.random;
    }

    public void setShouldStartSpamming(boolean shouldStartSpamming) {
        this.shouldStartSpamming = shouldStartSpamming;
    }

    public SpamBot(String ip) {
        this.random = new Random();
        this.shouldStartSpamming = false;
        this.index = 0;
        this.ip = ip;


        this.song = "Yo waddup!_I got swag\n".split("\n");
    }

    private static String alts = "slayerzoomba@gmail.com:chasel123\nvikram901:nautica\nzoro2011@live.ca:wwe123\nbbstar5613@gmail.com:pierce04\nTheTechPony@yahoo.com:freemcaccount1\ncoolmasterkane:63sen1\ntemperrre@gmail.com:isaiah123\npokemonbeastincod@gmail.com:firefox1234\nlausteven39@hotmail.se:steven123\npdsterling@comcast.net:PdsterlingcreatorofSuperDev\nMadsmotor100:23260427\nkq1003@gmail.com:kaiquander1003\nMakyMeje:12345t\nmatemate999@gmail.com:mate123\nApplehuman:maasikas\nJiSkAvH:hyvess123\nS1L3N7123@GMAIL.COM:dejesus2003\njude.cresswell@btinternet.com:trains123412\nUndeadxIdiot07:christos714\n";
    private List<BotThread> threads;

    private static class BotThread
            extends Thread {
        private final Engine engine;
        private final Account account;
        private final Server server;
        private final SpamBot spamBot;

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof BotThread)) {
                return false;
            }
            BotThread other = (BotThread) o;
            if (!other.canEqual(this)) {
                return false;
            }
            Object this$engine = getEngine();
            Object other$engine = other.getEngine();
            if (this$engine == null ? other$engine != null : !this$engine.equals(other$engine)) {
                return false;
            }
            Object this$account = getAccount();
            Object other$account = other.getAccount();
            if (this$account == null ? other$account != null : !this$account.equals(other$account)) {
                return false;
            }
            Object this$server = getServer();
            Object other$server = other.getServer();
            if (this$server == null ? other$server != null : !this$server.equals(other$server)) {
                return false;
            }
            Object this$test = getSpamBot();
            Object other$test = other.getSpamBot();
            return this$test == null ? other$test == null : this$test.equals(other$test);
        }

        public boolean canEqual(Object other) {
            return other instanceof BotThread;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $engine = getEngine();
            result = result * 59 + ($engine == null ? 0 : $engine.hashCode());
            Object $account = getAccount();
            result = result * 59 + ($account == null ? 0 : $account.hashCode());
            Object $server = getServer();
            result = result * 59 + ($server == null ? 0 : $server.hashCode());
            Object $test = getSpamBot();
            result = result * 59 + ($test == null ? 0 : $test.hashCode());
            return result;
        }

        @ConstructorProperties({"engine", "account", "server", "test"})
        public BotThread(Engine engine, Account account, Server server, SpamBot spamBot) {
            this.engine = engine;
            this.account = account;
            this.server = server;
            this.spamBot = spamBot;
        }

        public String toString() {
            return "Test.BotThread(engine=" + getEngine() + ", account=" + getAccount() + ", server=" + getServer() + ", test=" + getSpamBot() + ")";
        }

        public Engine getEngine() {
            return this.engine;
        }

        public Account getAccount() {
            return this.account;
        }

        public Server getServer() {
            return this.server;
        }

        public SpamBot getSpamBot() {
            return this.spamBot;
        }

        public void run() {
            while (this.spamBot.isRunning()) {
                try {
                    Connection connection = this.engine.createConnection(this.account, this.server, this.engine.getNetwork());
                    try {
                        connection.connect();
                    } catch (ConnectException e) {
                        if (e.getFailureReason() == ConnectException.Cause.ACCOUNT_INVALID) {
                            System.out.println("Account invalid " + this.account.toString());
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
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (this.spamBot.shouldStartSpamming) {
                                player.chat(this.spamBot.getMessage().replaceAll("%r", String.valueOf(this.spamBot.getRandom().nextInt(10000))));
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
    }

    private static class Attachment
            extends PlayerAttachment {
        public void onPlayerDeath(Player player) {
            try {
                Thread.sleep(60L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.respawn();
        }
    }

    String getMessage() {
        String s = this.song[this.index];
        this.index += 1;
        if (this.index == this.song.length) {
            this.index = 0;
        }
        return s;
    }

    public void run() {
        ProjectBot bot = new ProjectBot();
        Engine engine = bot.getEngineManager().getEngine();
        this.threads = new ArrayList();
        List<Account> accounts = new ArrayList();
        for (String s : alts.split("\n")) {
            String[] split = s.split(":");
            if (split.length != 2) {
                accounts.add(engine.getAccount(split[0]));
            } else {
                accounts.add(engine.getAccount(split[0], split[1]));
            }
        }
        Server server = engine.getServer(this.ip, Integer.valueOf(25565));
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

    private static String randomAlts(Integer amount) {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < amount.intValue(); x++) {
            builder.append(getRandomString(Integer.valueOf(5))).append(x == amount.intValue() - 1 ? "" : "\n");
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    public static String getRandomString(Integer length) {
        return Integer.toString(ProjectBot.getRandom().nextInt((int) Math.pow(10.0D, length.intValue())));
    }
}