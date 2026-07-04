package discordhandler.events;

import discordhandler.Bot;
import discordhandler.core.CooldownManager;
import discordhandler.core.SlashCommandWebhook;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionCreateHandler extends ListenerAdapter {
    private final Bot bot;
    private final CooldownManager cooldownManager = new CooldownManager();

    public InteractionCreateHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        var commandName = event.getName();

        if (event.getGuild() == null) {
            event.reply("Commands are only available in servers!").setEphemeral(true).queue();
            return;
        }

        var cmd = bot.slashCommands.get(commandName);
        if (cmd == null) return;

        var user = event.getUser();
        var userId = user.getId();
        var guildName = event.getGuild().getName();

        var result = cooldownManager.check(userId, commandName);
        if (result.onCooldown) {
            event.reply("Please wait " + result.remaining + "s before using this command again.")
                    .setEphemeral(true).queue();
            return;
        }

        try {
            cmd.handler.execute(event);

            SlashCommandWebhook.sendUsage(
                    userId, user.getName(), commandName, guildName,
                    user.getAvatarUrl()
            );
        } catch (Exception e) {
            System.out.println("  \u274c Error in slash command " + commandName + ": " + e.getMessage());

            var embed = new EmbedBuilder()
                    .setTitle("\u274c An error occurred")
                    .setDescription("**Error:** " + e.getMessage())
                    .setColor(0xFF0000)
                    .build();

            if (!event.isAcknowledged()) {
                event.replyEmbeds(embed).setEphemeral(true).queue();
            } else {
                event.getHook().sendMessageEmbeds(embed).setEphemeral(true).queue();
            }

            SlashCommandWebhook.sendError(
                    userId, user.getName(), commandName, guildName, e.getMessage()
            );
        }
    }
}
