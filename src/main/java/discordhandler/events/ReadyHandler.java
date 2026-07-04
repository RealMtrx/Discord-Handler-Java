package discordhandler.events;

import discordhandler.Bot;
import discordhandler.Config;
import discordhandler.core.ReadyWebhook;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyHandler extends ListenerAdapter {
    private final Bot bot;
    private boolean readyCalled = false;

    public ReadyHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onReady(ReadyEvent event) {
        if (readyCalled) return;
        readyCalled = true;

        var jda = bot.jda;
        var selfUser = jda.getSelfUser();

        System.out.println("  \u2705 Logged in as " + selfUser.getName() + " (ID: " + selfUser.getId() + ")");

        jda.getPresence().setActivity(
                net.dv8tion.jda.api.activities.Activity.watching(
                        Config.getInstance().botName + " | " + Config.getInstance().prefix + "help"
                )
        );

        ReadyWebhook.sendReadyWebhook(
                selfUser.getName(),
                selfUser.getId(),
                jda.getGuilds().size()
        );

        registerSlashCommands();
    }

    private void registerSlashCommands() {
        var jda = bot.jda;
        for (var cmd : bot.slashCommands.values()) {
            jda.upsertCommand(cmd.name, cmd.description).queue();
        }
        System.out.println("  \u2705 Registered " + bot.slashCommands.size() + " global slash commands");
    }
}
