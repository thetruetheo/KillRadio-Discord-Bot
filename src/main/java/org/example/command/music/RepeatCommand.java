package org.example.command.music;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.GuildMusicManager;
import org.example.lavaplayer.PlayerManager;

public class RepeatCommand implements ICommands {
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
        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }
        if(!member.getVoiceState().inAudioChannel()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("You need to be in a voice channel for this command to work");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        final boolean newRepeating=!musicManager.scheduler.repeating;
        musicManager.scheduler.repeating=newRepeating;
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle(":repeat:The player has been set to: ")
                        .setDescription(newRepeating ? "repeating":"not repeating");
        channel.sendMessageEmbeds(embedBuilder.build()).queue();

    }

    @Override
    public String getName() {
        return "repeat";
    }
}
