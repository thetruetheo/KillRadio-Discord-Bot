package org.example.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.example.CommandManager;
import org.example.Config;


public class MessageReceivedEventHandler extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message message = event.getMessage();
        Member member = event.getMember();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        String prefix = Config.get("PREFIX");
        JDA eventJDA = event.getJDA();
        Channel txtChannel= event.getChannel();
        if (!event.isFromGuild()) {
            return;
        }
        if(author.isBot()) {
            return;
        }
        if(event.isWebhookMessage()){
            return;
        }

        if(content.equalsIgnoreCase(prefix+"shutdown")&&author.getId().equals(Config.get("OWNER_ID"))){
            txtChannel.sendMessage("");
            eventJDA.getHttpClient().connectionPool().evictAll();
            eventJDA.getHttpClient().dispatcher().executorService().shutdown();
            eventJDA.shutdown();
            System.out.println("SHUTDOWN");
        }
        if(content.startsWith(prefix)){
            manager.handle(event);
        }

    }

}
