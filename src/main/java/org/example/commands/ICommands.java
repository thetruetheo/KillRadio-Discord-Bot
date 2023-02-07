package org.example.commands;

import java.util.List;

public interface ICommands {
    void handle();

    String getName();

    default List<String> getAliases(){
        return List.of();
    }
}
