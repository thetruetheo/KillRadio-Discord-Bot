package org.example.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.GuildMusicManager;
import org.example.lavaplayer.PlayerManager;

public class NowPlayingCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getTxtChannel();
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

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();
        if(audioPlayer.getPlayingTrack()==null){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(":x:There is no track playing currently");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":notes:Now playing: ")
                .addField("Title: ", track.getInfo().title,true)
                .addField("By : ",track.getInfo().author,true)
                .addField("Link : ",track.getInfo().uri,false);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
        //channel.sendMessageFormat("Now playing "+track.getInfo().title +" by "+track.getInfo().author+"Link : "+track.getInfo().uri).queue();
    }

    @Override
    public String getName() {
        return "nowplaying";
    }
}
