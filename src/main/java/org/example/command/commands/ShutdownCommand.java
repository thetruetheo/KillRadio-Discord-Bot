package org.example.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.Config;
import org.example.command.CommandContext;
import org.example.command.ICommands;

public class ShutdownCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
        final JDA eventJDA=ctx.getJDA();
        final User author=ctx.getAuthor();
        final MessageReceivedEvent event=ctx.getEvent();
        if(author.getId().equals(Config.get("OWNER_ID"))){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(":zap:Shutting down");
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            eventJDA.getHttpClient().connectionPool().evictAll();
            eventJDA.getHttpClient().dispatcher().executorService().shutdown();
            eventJDA.shutdown();
            System.out.println("SHUTDOWN");
        }
    }

    @Override
    public String getName() {
        return "shutdown";
    }
}
