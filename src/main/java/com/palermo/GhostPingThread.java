package com.palermo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;

public class GhostPingThread extends Thread {
    private final Guild servidorAguacates;
    private final SplittableRandom rand;
    private final List<Member> miembros;

    public GhostPingThread(JDA cliente){
        servidorAguacates = cliente.getGuildById("876951476436619284");
        rand = new SplittableRandom();
        miembros = servidorAguacates.getMembers();
    }

    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (rand.nextInt(1,101) <= 10) {
                    Member usuario = miembros.get(rand.nextInt(miembros.size()));
                    ping(servidorAguacates.getDefaultChannel(), usuario).queue();
                    System.out.println("Pingeando a " + usuario.getNickname() + " porque le ha tocado el ghostping xD" +
                            "que pringado");
                }
                sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private RestAction<Void> ping(TextChannel canal, Member usuario) {
        return canal.sendMessage(usuario.getAsMention())
                .delay(50, TimeUnit.MILLISECONDS)
                .flatMap(Message::delete);
    }
}
