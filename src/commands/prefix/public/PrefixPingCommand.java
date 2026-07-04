import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class PrefixPingCommand {
    public static PrefixCommand getCommand() {
        return new PrefixCommand("ping", "\ud83c\udfd3 Show bot latency", "public", List.of("pong"),
                (MessageReceivedEvent event, String[] args) -> {
                    event.getChannel().sendMessage("Pinging...").queue(sent -> {
                        long latency = event.getMessage().getTimeCreated().toInstant().toEpochMilli();
                        var embed = new EmbedBuilder()
                                .setTitle("\ud83c\udfd3 Pong!")
                                .setDescription("> **Message Latency:** `" + latency + "ms`")
                                .setColor(0x5865F2).setFooter(event.getAuthor().getName() + " \u2022 Ping").build();
                        sent.editMessageEmbeds(embed).queue();
                    });
                });
    }
}
