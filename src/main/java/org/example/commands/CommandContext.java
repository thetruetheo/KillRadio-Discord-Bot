package org.example.commands;

import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import org.example.events.MessageReceivedEvent;


import java.util.List;


public class CommandContext implements ICommandContext {
    //private final  event;
    private final List<String> args;
    public CommandContext(/* event*/, List<String> args){
        this.event=event;
        this.args=args;
    }
    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    @Override
    public MessageReceivedEvent getEvent() {
        return this.event;
    }
}
