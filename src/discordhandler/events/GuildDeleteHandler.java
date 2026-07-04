package discordhandler.events;

import discordhandler.core.LeaveGuildWebhook;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.function.Supplier;

public class GuildDeleteHandler extends ListenerAdapter {
    private final Supplier<Integer> remainingServers;
    public GuildDeleteHandler(Supplier<Integer> remainingServers) { this.remainingServers = remainingServers; }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        try {
            var guild = event.getGuild();
            System.out.println("  \ud83d\udc4b Left guild: " + guild.getName() + " (" + guild.getId() + ")");
            LeaveGuildWebhook.sendLeaveGuildWebhook(guild.getId(), guild.getName(), guild.getMemberCount(), remainingServers.get());
        } catch (Exception e) {
            System.out.println("  \u274c Error in guild leave event: " + e.getMessage());
        }
    }
}
