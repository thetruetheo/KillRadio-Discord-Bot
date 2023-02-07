package org.example.events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(net.dv8tion.jda.api.events.message.MessageReceivedEvent event){
        Message message = event.getMessage();
        Member member = event.getMember();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();

        if (!event.isFromGuild()) {
            return;
        }
        if(author.isBot()) {
            return;
        }

    }

    public Guild getGuildFromMessage(net.dv8tion.jda.api.events.message.MessageReceivedEvent event){
        Message message = event.getMessage();
        User author = message.getAuthor();
        Guild guild = event.getGuild();
        return guild;
    }
}
