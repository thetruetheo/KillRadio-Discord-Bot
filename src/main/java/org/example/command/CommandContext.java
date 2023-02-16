package org.example.command;



import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandContext{
    private final MessageReceivedEvent event;
    private final List<String> args;
    private final JDA contextJDA;
    private final Channel channel;
    private final TextChannel txtChannel;
    private final long txtChannelId;

    private final Member member;
    private final User author;
    private final GuildVoiceState memberVoiceState;


    public CommandContext(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
        this.contextJDA=event.getJDA();
        this.channel=event.getChannel().asTextChannel();
        this.txtChannel=event.getChannel().asTextChannel();
        this.member=event.getMember();
        this.author=event.getMessage().getAuthor();
        this.txtChannelId=event.getChannel().getIdLong();
        this.memberVoiceState=event.getMember().getVoiceState();
    }


    public Guild getGuild() {
        return this.getEvent().getGuild();
    }


    public MessageReceivedEvent getEvent() {
        return this.event;
    }
    public List<String> getArgs(){
        return this.args;
    }

    public JDA getJDA() {
        return contextJDA;
    }

    public Channel getChannel() {
        return channel;
    }
    public TextChannel getTxtChannel(){
        return txtChannel;
    }

    public Member getMember() {
        return member;
    }



    public long getTxtChannelId() {
        return txtChannelId;
    }

    public GuildVoiceState getMemberVoiceState() {
        if(memberVoiceState!=null){
            return memberVoiceState;
        }
        return null;
    }
}
