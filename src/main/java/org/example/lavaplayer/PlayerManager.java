package org.example.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.command.CommandContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long,GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager(){
        this.musicManagers=new HashMap<>();
        this.audioPlayerManager=new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }
    public GuildMusicManager getMusicManager(Guild guild){
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId)->{
          final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
          guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
          return guildMusicManager;
        });
    }
    public void loadAndPlay(TextChannel channel, String trackurl, AudioManager audioManager,AudioChannel audioChannel){
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        audioManager.openAudioConnection(audioChannel);
        this.audioPlayerManager.loadItemOrdered(musicManager, trackurl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("\uD83C\uDFB5Adding track")
                        .addField("Name: ",track.getInfo().title,true)
                        .addField("by: ",track.getInfo().author,true);
                channel.sendMessageEmbeds(embedBuilder.build()).queue();

            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("\uD83C\uDFB5Adding to queue")
                        .addField("Number of tracks: ",String.valueOf(tracks.size()),true)
                        .addField("From playlist: ",playlist.getName(),true);
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                for(final AudioTrack track : tracks){
                    musicManager.scheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                //
            }

            @Override
            public void loadFailed(FriendlyException e) {
                //
            }
        });
    }
    public static PlayerManager getInstance(){
        if(INSTANCE==null){
            INSTANCE=new PlayerManager();
        }
        return INSTANCE;
    }
}
