package org.example.handlers;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.handlers.AudioPlayerSendHandler;

public class MessageReceivedEventHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(net.dv8tion.jda.api.events.message.MessageReceivedEvent event){
        Message message = event.getMessage();
        Member member = event.getMember();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        VoiceChannel userChannel=member.getVoiceState().getChannel().asVoiceChannel();
        if (!event.isFromGuild()) return;
        if(author.isBot()){
            return;
        }
        if(content!="!join"){return;}
        if(content.equals("!join")){

        }
    }
}
