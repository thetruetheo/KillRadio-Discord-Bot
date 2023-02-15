package org.example.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.example.command.CommandContext;
import org.example.command.ICommands;

public class EmbedsTestCommand implements ICommands {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder embedBuilder= new EmbedBuilder();
        embedBuilder.setTitle("Test",null)
                .setDescription("Description")
                .addField("Title of field","Text of field",true)
                .setFooter("Footer");
        ctx.getTxtChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "embedstest";
    }
}
