package org.example.command.commands.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.GuildMusicManager;
import org.example.lavaplayer.PlayerManager;

public class LeaveCommand implements ICommands {
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

        final Guild guild = ctx.getGuild();

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.scheduler.repeating=false;
        musicManager.scheduler.queue.clear();
        musicManager.audioPlayer.stopTrack();

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.closeAudioConnection();
        channel.sendMessage("I have left the voice channel").queue();
    }

    @Override
    public String getName() {
        return "leave";
    }
}
