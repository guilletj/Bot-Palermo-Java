package com.palermo;

import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class ListenerOnTyping extends ListenerAdapter {
    private final Random rand;

    public ListenerOnTyping(){
        this.rand = new Random();
    }

    @Override
    public void onUserTyping(UserTypingEvent evento) {
        if (evento.getUser().isBot()) return;

        if (rand.nextInt(10) == 9){
            String respuesta = "Che " + evento.getMember().getAsMention() + " no te molestés en escribir, nadie quiere saber tu opinión pelotudo";
            evento.getChannel().sendMessage(respuesta).queue();
        }
    }
}
