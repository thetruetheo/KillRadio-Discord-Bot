package org.example.command.commands.music;

import net.dv8tion.jda.api.EmbedBuilder;
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
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                            .setDescription("I'm already in a voice channel");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState= ctx.getMemberVoiceState();

        if(!member.getVoiceState().inAudioChannel()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ERROR")
                    .setDescription("You need to be in a voice channel for this command to work");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        final AudioManager audioManager=ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel().asVoiceChannel();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("\uD83D\uDD0A Connecting to voice channel")
                .addField("Channel: ",member.getVoiceState().getChannel().asVoiceChannel().getName(),true);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
        audioManager.openAudioConnection(memberChannel);

    }

    @Override
    public String getName() {
        return "join";
    }
}
