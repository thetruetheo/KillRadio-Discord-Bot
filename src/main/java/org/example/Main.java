package org.example;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.example.events.MessageReceivedEventHandler;
import reactor.core.publisher.Mono;


public class Main{

    public static void main(String[] args) {

        JDABuilder jda = JDABuilder.createDefault(Config.get("TOKEN"),
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_VOICE_STATES)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new MessageReceivedEventHandler());
        jda.build();



    }

}