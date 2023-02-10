package org.example.command;

import java.util.List;

public interface ICommands {
    void handle(CommandContext ctx);

    String getName();

    default List<String> getAliases(){
        return List.of();
    }
}
