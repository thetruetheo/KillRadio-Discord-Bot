package org.example.commands;



import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandContext{
    private final MessageReceivedEvent event;
    private final List<String> args;
    private final JDA contextJDA;

    private final Channel txtChannel;

    public CommandContext(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
        this.contextJDA=event.getJDA();
        this.txtChannel=event.getChannel();
    }


    public Guild getGuild() {
        return this.getEvent().getGuild();
    }


    public MessageReceivedEvent getEvent() {
        return this.event;
    }
    public List<String> getArgs(){
        return this.args;
    }

    public JDA getJDA() {
        return contextJDA;
    }

    public Channel getChannel() {
        return txtChannel;
    }

}
