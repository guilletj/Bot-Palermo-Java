package com.palermo;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ListenerChat extends ListenerAdapter {
    private final ArrayList<String> insultos = new ArrayList<>();
    private final ArrayList<String> fotos_palermo = new ArrayList<>();
    private final Random rand = new Random();

    public ListenerChat(){
        insultos.add("ANDÁ A LAVAR LOS PLATOS");
        insultos.add("LA CONCHA DE TU MADRE");
        insultos.add("TE VOY A CAGAR A TROMPADAS");
        fotos_palermo.add("https://cdn.discordapp.com/attachments/543845595379400747/889498553284702208/lcdp1-3.png");
        fotos_palermo.add("https://cdn.discordapp.com/attachments/735130570463641681/889503812602200124/9k.png");
        fotos_palermo.add("https://cdn.discordapp.com/attachments/735130570463641681/889503841882607657/images.png");
        fotos_palermo.add("https://cdn.discordapp.com/attachments/735130570463641681/889503863730741288/Z.png");
        fotos_palermo.add("https://cdn.discordapp.com/attachments/543845595379400747/889498413345964032/palermo.png");
        fotos_palermo.add("https://tenor.com/view/eyebrow-raise-rodrigo-de-la-serna-palermo-la-casa-de-papel-hey-there-gif-17053846");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent evento) {
        if (evento.getAuthor().isBot()) return;

        Message mensaje = evento.getMessage();
        Guild servidor = evento.getGuild();

        if (!servidor.getId().equals("876951476436619284")) {
            mensaje.getChannel().sendMessage("NO TENGO JURISDICCION EN ESTE SERVIDOR BOLUDO, ME VOY").queue();
            servidor.leave().queue();
            return;
        }

        MessageChannel canal = evento.getChannel();
        String contenido = mensaje.getContentDisplay().toLowerCase();
        Member palermo = servidor.getMemberById("889477307306225675");
        Role estrellita = servidor.getRoleById("877150702516985897");
        Role castigo = servidor.getRoleById("888803290257698896");


        if (mensaje.mentionsEveryone()){
            String mencion_autor = mensaje.getAuthor().getAsMention();
            String res = insultos.get(rand.nextInt(insultos.size())) + " " + mencion_autor;
            canal.sendMessage(res).queue();
            servidor.addRoleToMember(mensaje.getMember(), castigo).queue();
            servidor.removeRoleFromMember(mensaje.getMember(), estrellita).queue();
            cagarUsuario(mensaje.getAuthor());
            return;
        }

        if (contenido.contains("palermo") && contenido.contains("ayudame")) {
            if (mensaje.getMember().hasPermission(Permission.ADMINISTRATOR)){
                if (!mensaje.getMentionedMembers().isEmpty()) {
                    if (mensaje.getMentionedMembers().contains(palermo)) {
                        String mencion_autor = mensaje.getAuthor().getAsMention();
                        String res = insultos.get(rand.nextInt(insultos.size())) + " " + mencion_autor;
                        canal.sendMessage(res).queue();
                        servidor.addRoleToMember(mensaje.getMember(), castigo).queue();
                        servidor.removeRoleFromMember(mensaje.getMember(), estrellita).queue();
                        cagarUsuario(mensaje.getAuthor());
                        return;
                    }
                    mensaje.getMentionedMembers().forEach( (m) -> {
                        if (rand.nextInt(2) == 1) {
                            servidor.kickVoiceMember(m).queue();
                            String msg = "A LA MIERDA CON EL RETARDADO DE " + m.getAsMention();
                            mensaje.getChannel().sendMessage(msg).queue();
                        } else {
                            m.mute(true).queue();
                            String msg = "YA AMORDACÉ AL GIL DE " + m.getAsMention();
                            mensaje.getChannel().sendMessage(msg).queue();
                        }
                    });
                } else {
                    mensaje.getChannel().sendMessage("¿QUE MIERDA QUERÉS?").queue();
                }
            } else {
                mensaje.getChannel().sendMessage("TU A MI NO ME DAS ORDENES").queue();
            }
            return;
        }
        
        if (contenido.contains("palermo") && contenido.contains("defcon") && contenido.contains("2")) {
            if (mensaje.getMember().hasPermission(Permission.ADMINISTRATOR)){
                if (mensaje.getMember().getVoiceState().inVoiceChannel()) {
                    mensaje.getChannel().sendMessage("DEFCON 2 LA CONCHA DE TU MADRE").queue();
                    mensaje.getChannel().sendMessage("https://cdn.discordapp.com/attachments/543845595379400747/890676236563652640/disparar-palermo.gif").queue();
                    mensaje.getMember().getVoiceState().getChannel().getMembers().forEach( m -> servidor.kickVoiceMember(m).queue());
                } else {
                    mensaje.getChannel().sendMessage("¿PARA QUE MIERDA QUERÉS ACTIVAR DEFCON 2?").queue();
                }
            } else {
                mensaje.getChannel().sendMessage("¿PERO QUIEN MIERDAS TE CREÉS TÚ?").queue();
            }
            return;
        }

        if (contenido.contains("palermo") && contenido.contains("reventalo")) {
            if (!mensaje.getMentionedMembers().isEmpty()) {
                if (mensaje.getMentionedMembers().contains(palermo)) {
                    String mencion_autor = mensaje.getAuthor().getAsMention();
                    String res = insultos.get(rand.nextInt(insultos.size())) + " " + mencion_autor;
                    canal.sendMessage(res).queue();
                    servidor.addRoleToMember(mensaje.getMember(), castigo).queue();
                    servidor.removeRoleFromMember(mensaje.getMember(), estrellita).queue();
                    cagarUsuario(mensaje.getAuthor());
                    return;
                }
                if (mensaje.getMentionedMembers().get(0).getVoiceState().inVoiceChannel()) {
                    mensaje.getChannel().sendMessage("Lo cago a piñas").queue();
                    VoiceChannel canal_voz = mensaje.getMentionedMembers().get(0).getVoiceState().getChannel();
                    AudioManager manager = servidor.getAudioManager();
                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    playerManager.registerSourceManager(new HttpAudioSourceManager());
                    AudioPlayer player = playerManager.createPlayer();
                    ResultHandler handler = new ResultHandler(player);
                    TrackScheduler scheduler = new TrackScheduler(manager);
                    player.addListener(scheduler);
                    playerManager.loadItem("https://cdn.discordapp.com/attachments/543845595379400747/891338752079835146/palermo_a_trompadas.mp3", handler);
                    manager.setSendingHandler(new AudioHandler(player));
                    manager.openAudioConnection(canal_voz);
                } else {
                    mensaje.getChannel().sendMessage("El pelotudo no está en un canal de voz").queue();
                }
            } else {
                mensaje.getChannel().sendMessage("¿A QUIÉN?").queue();
            }
            return;
        }

        if (contenido.contains("palermo") && contenido.contains("canta") && contenido.contains("nana")){
            if (mensaje.getMember().getVoiceState().inVoiceChannel()) {
                VoiceChannel canal_voz = mensaje.getMember().getVoiceState().getChannel();
                AudioManager manager = servidor.getAudioManager();
                AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                playerManager.registerSourceManager(new HttpAudioSourceManager());
                AudioPlayer player = playerManager.createPlayer();
                ResultHandler handler = new ResultHandler(player);
                TrackScheduler scheduler = new TrackScheduler(manager);
                player.addListener(scheduler);
                playerManager.loadItem("https://cdn.discordapp.com/attachments/543845595379400747/891338438652092487/cuatro_esquinitas.mp3", handler);
                manager.setSendingHandler(new AudioHandler(player));
                manager.openAudioConnection(canal_voz);
            } else {
                mensaje.getChannel().sendMessage("No estás en un canal de voz gil").queue();
            }
            return;
        }

        if (contenido.contains("gay") || contenido.contains("gays") || contenido.contains("gei") || contenido.contains("geis")) {
            mensaje.getChannel().sendMessage("https://cdn.discordapp.com/attachments/735130570463641681/889582551239360542/ezgif.com-gif-maker.gif").queue();
            return;
        }

        if (contenido.endsWith("ado")) {
            mensaje.getChannel().sendMessage("https://cdn.discordapp.com/attachments/735130570463641681/889571534912487454/ezgif.com-gif-maker.gif").queue();
            return;
        }

        if (contenido.contains("palermo")){
            canal.sendMessage("QUE MIERDA QUERÉS").queue();
        }
    }


    private void cagarUsuario(User usuario){
        mensajeAutoBorrado(usuario).queue();
        mensajeAutoBorrado(usuario, "¿Te creés bien gracioso hijo de la re mil putas?").queue();
        mensajeAutoBorrado(usuario, "Ahora te quedas calladito hasta que Yun o Freigmare se den cuenta").queue();
    }

    private RestAction<Void> mensajeAutoBorrado(User usuario) {
        return usuario.openPrivateChannel()
                .flatMap(canal -> canal.sendMessage(fotos_palermo.get(rand.nextInt(fotos_palermo.size()))))
                .delay(120, TimeUnit.SECONDS)
                .flatMap(Message::delete);
    }

    private RestAction<Void> mensajeAutoBorrado(User usuario, String msg) {
        return usuario.openPrivateChannel()
                .flatMap(canal -> canal.sendMessage(msg))
                .delay(120, TimeUnit.SECONDS)
                .flatMap(Message::delete);
    }

}
