package org.example.commands;

import net.dv8tion.jda.api.JDA;
import org.example.commands.ICommands;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.example.commands.CommandContext;

public class PingCommand implements ICommands{
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        Channel txtChannel= ctx.getChannel();
        txtChannel.sendMessage
    }

    @Override
    public String getName() {
        return null;
    }
}
