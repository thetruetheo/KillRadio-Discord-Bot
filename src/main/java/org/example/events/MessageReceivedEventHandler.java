package org.example.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.CommandManager;
import org.example.Config;
import org.jetbrains.annotations.Nullable;


public class MessageReceivedEventHandler extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();
    @Override
    @Nullable
    public void onMessageReceived(MessageReceivedEvent event){
        Message message = event.getMessage();
        Member member = event.getMember();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        String prefix = Config.get("PREFIX");
        JDA eventJDA = event.getJDA();
        Channel txtChannel= event.getChannel().asTextChannel();
        if (!event.isFromGuild()) {
            return;
        }
        if(author.isBot()) {
            return;
        }
        if(event.isWebhookMessage()){
            return;
        }

        if(content.startsWith(prefix)){
            event.getMessage().delete().queue();
            manager.handle(event);
        }


    }

}
