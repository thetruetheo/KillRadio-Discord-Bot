package org.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.handlers.MessageReceivedEventHandler;


public class Main{
    public static void main(String[] args) {
        JDABuilder jda = JDABuilder.createDefault("MTA3MjExODQzMDQ3MzM4ODA0Mg.Gai2ls.6iYBESIOy1pdZxKKvXjkm4IKWu48ENmoKkZjQU",
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceivedEventHandler());
        jda.build();

    }
}