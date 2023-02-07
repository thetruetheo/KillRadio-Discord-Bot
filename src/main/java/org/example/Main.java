package org.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.events.MessageReceivedEvent;


public class Main{
    public static void main(String[] args) {
        JDABuilder jda = JDABuilder.createDefault("MTA3MjExODQzMDQ3MzM4ODA0Mg.Gat3_I.7ysS2h_d_5CZTT_M1qEWJQpdMvoy9afkYb9VnE",
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceivedEvent());
        jda.build();

    }
}