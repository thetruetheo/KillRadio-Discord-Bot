package org.example.command.commands;

import net.dv8tion.jda.api.JDA;
import org.example.command.CommandContext;
import org.example.command.ICommands;

public class PingCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        if(ctx.getEvent().getAuthor().isBot()){
            return;
        }
        ctx.getEvent().getChannel().asTextChannel().sendMessage("First command").queue();


    }

    @Override
    public String getName() {

        return "ping";
    }
}
