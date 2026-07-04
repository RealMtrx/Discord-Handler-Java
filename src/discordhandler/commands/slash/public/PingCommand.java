package discordhandler.commands.slash.public;

import discordhandler.models.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class PingCommand {
    public static SlashCommand getCommand() {
        return new SlashCommand("ping", "\ud83c\udfd3 Show bot latency", "public",
                (SlashCommandInteraction event) -> {
                    long latency = event.getJDA().getGatewayPing();
                    var embed = new EmbedBuilder()
                            .setTitle("\ud83c\udfd3 Pong!")
                            .setDescription("> **WebSocket Latency:** `" + latency + "ms`\n> **API Latency:** `" + latency + "ms`")
                            .setColor(0x5865F2).setFooter(event.getJDA().getSelfUser().getName() + " \u2022 Ping").build();
                    event.replyEmbeds(embed).queue();
                });
    }
}
