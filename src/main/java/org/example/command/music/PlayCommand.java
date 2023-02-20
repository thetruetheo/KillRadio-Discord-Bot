package org.example.command.music;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {


        final TextChannel channel = ctx.getTxtChannel();

        if(ctx.getArgs().isEmpty()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("Correct usage is \'!!play <youtube link>\'");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();

            return;
        }

        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState=self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("I need to be in a voice channel for this command to work");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState=member.getVoiceState();
        if(!member.getVoiceState().inAudioChannel()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("You need to be in a voice channel for this command to work");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("You need to be in the same voice channel as me for this command to work");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        String link=String.join(" ", ctx.getArgs());
        if(!isUrl(link)){
            link="ytsearch:" + link;
        }
        /*
        try{
            PlayerManager.getInstance().loadAndPlay(channel,link,ctx.getGuild().getAudioManager());
        }
        catch(NullPointerException e){
            channel.sendMessage("Please, give me a link to the track or a search prompt").queue();
        }
        */

        PlayerManager.getInstance().loadAndPlay(channel,link,ctx.getGuild().getAudioManager(), member.getVoiceState().getChannel().asVoiceChannel());



    }

    @Override
    public String getName() {
        return "play";
    }

    private boolean isUrl(String url){
        try{
            new URI(url);
            return true;
        }catch(URISyntaxException e){
            return false;
        }
    }
}
