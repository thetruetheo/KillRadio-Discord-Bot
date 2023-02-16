package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.command.commands.music.*;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommands> commands = new ArrayList<>();
    public CommandManager(){
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new LeaveCommand());
    }

    private void addCommand(ICommands cmd){
        boolean nameFound = this.commands.stream().anyMatch((it)->it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound){
            throw new IllegalArgumentException("A command with this name already exists");
        }

        commands.add(cmd);
    }
    @Nullable
    private ICommands getCommand(String search){
        String searchLower = search.toLowerCase();

        for(ICommands cmd:this.commands){
            if(cmd.getName().equals(searchLower)||cmd.getAliases().contains(searchLower)){
                return cmd;
            }
        }
        return null;
    }

    public void handle(MessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)"+ Pattern.quote(Config.get("PREFIX")),"")
                .split("\\s+");
        String invoke = split[0].toLowerCase();
        ICommands cmd=this.getCommand(invoke);
        if(cmd!=null){
            event.getChannel().sendTyping().queue();
            List<String> args= Arrays.asList(split).subList(1,split.length);

            CommandContext ctx=new CommandContext(event,args);

            cmd.handle(ctx);
        }
    }


}
