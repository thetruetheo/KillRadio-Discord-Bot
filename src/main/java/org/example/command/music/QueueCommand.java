package org.example.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.example.lavaplayer.GuildMusicManager;
import org.example.lavaplayer.PlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx){
        final TextChannel channel = ctx.getTxtChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if(queue.isEmpty()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(":negative_squared_cross_mark:The queue is currently empty");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        final int trackCount=Math.min(queue.size(),20);
        final List<AudioTrack> trackList=new ArrayList<>(queue);
        final EmbedBuilder embedBuilderQueue=new EmbedBuilder();
        embedBuilderQueue.setTitle(":scroll:Current queue: ");
        for(int i=0;i<trackCount;i++){
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();
            embedBuilderQueue.addField("Track № "+String.valueOf(i+1),"'"+info.title+" by "+info.author+" ' ['"+formatTime(track.getDuration())+"']",false);

        }

        if(trackList.size()>trackCount){

            embedBuilderQueue.setFooter("And '"+String.valueOf(trackList.size()-trackCount)+"' more...");

        }
        channel.sendMessageEmbeds(embedBuilderQueue.build()).queue();

    }


    private String formatTime(long timeInMillis){
        final long hours = timeInMillis/TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis/TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis%TimeUnit.MINUTES.toMillis(1)/ TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d",hours,minutes,seconds);
    }


    @Override
    public String getName() {
        return "queue";
    }
}
