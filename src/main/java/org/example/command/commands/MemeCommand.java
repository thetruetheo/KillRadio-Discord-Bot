package org.example.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.command.CommandContext;
import org.example.command.ICommands;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MemeCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event=ctx.getEvent();
        try{
            Document document = Jsoup.connect("https://www.anekdot.ru/random/mem/").get();
            Elements memes = document.select("img[src$=.jpg]");
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(":joy:Top russian memes: ");
            embedBuilder.setImage(memes.get(0).absUrl("src"));
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return "meme";
    }
}
