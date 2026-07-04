package discordhandler.events;

import discordhandler.core.JoinGuildWebhook;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildCreateHandler extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        try {
            var guild = event.getGuild();
            String icon = guild.getIconUrl();
            System.out.println("  \ud83c\udfe5 Joined guild: " + guild.getName() + " (" + guild.getId() + ")");

            JoinGuildWebhook.sendJoinGuildWebhook(
                    guild.getName(),
                    guild.getId(),
                    guild.getOwnerId(),
                    guild.getMemberCount(),
                    icon
            );
        } catch (Exception e) {
            System.out.println("  \u274c Error in guild join event: " + e.getMessage());
        }
    }
}
