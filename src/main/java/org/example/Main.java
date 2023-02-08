package org.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.events.MessageReceivedEventHandler;


public class Main{
    public static void main(String[] args) {
        JDABuilder jda = JDABuilder.createDefault(Config.get("TOKEN"),
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceivedEventHandler());
        jda.build();

    }
}