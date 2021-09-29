package com.palermo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

import javax.security.auth.login.LoginException;

public class Bot {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No has pasado una token");
            System.exit(1);
        }

        try {
            JDABuilder constructor = JDABuilder.createDefault(args[0]);
            constructor.enableIntents(
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                    GatewayIntent.DIRECT_MESSAGE_TYPING,
                    GatewayIntent.GUILD_MEMBERS
            );
            constructor.addEventListeners(new ListenerChat(), new ListenerOnTyping());
            constructor.setChunkingFilter(ChunkingFilter.ALL);
            constructor.setActivity(Activity.watching("la concha de tu madre"));
            JDA cliente = constructor.build().awaitReady();
            GhostPingThread ghostPingThread = new GhostPingThread(cliente);
            ghostPingThread.start();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("No se ha podido logear");
        }


    }
}
