package org.example.command.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.command.CommandContext;
import org.example.command.ICommands;

public class JoinCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getTxtChannel();
        final Member self=ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(selfVoiceState.inAudioChannel()){
            channel.sendMessage("I'm already in a voice channel").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState= member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }
        final AudioManager audioManager=ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel().asVoiceChannel();

        audioManager.openAudioConnection(memberChannel);
        channel.sendMessageFormat("Connecting to '\uD83D\uDD0A %s'",memberChannel.getName()).queue();
    }

    @Override
    public String getName() {
        return "join";
    }
}
