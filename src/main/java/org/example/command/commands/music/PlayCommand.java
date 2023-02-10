package org.example.command.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.PlayerManager;
import org.w3c.dom.Text;

public class PlayCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
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

        PlayerManager.getInstance().loadAndPlay(channel,"https://youtu.be/vBjzAdpZzf0");



    }

    @Override
    public String getName() {
        return "play";
    }
}
