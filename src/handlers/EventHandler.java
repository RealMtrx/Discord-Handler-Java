import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    public static List<ListenerAdapter> getListeners(Bot bot) {
        var list = new ArrayList<ListenerAdapter>();
        list.add(new ReadyHandler(bot));
        list.add(new InteractionCreateHandler(bot));
        list.add(new MessageCreateHandler(bot));
        list.add(new GuildCreateHandler());
        list.add(new GuildDeleteHandler(() -> bot.jda != null ? bot.jda.getGuilds().size() : 0));
        System.out.println("  \u2705 Loaded 5 events: on_ready, on_interaction, on_message, on_guild_join, on_guild_remove");
        return list;
    }
}
