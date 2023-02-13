package org.example.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
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
        final AudioChannel voiceChannel=ctx.getAudioChannel();
        final TextChannel channel = ctx.getTxtChannel();
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState=self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            channel.sendMessage("I need to be in a voice channel for this command to work").queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState=member.getVoiceState();
        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }
        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel as me for this command to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();
        if(audioPlayer.getPlayingTrack()==null){
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }
        final AudioTrackInfo info = track.getInfo();
        channel.sendMessageFormat("Now playing \'$s\' by \'$s\'(Link : <$s>)",info.title,info.author,info.uri).queue();
    }

    @Override
    public String getName() {
        return "nowplaying";
    }
}
