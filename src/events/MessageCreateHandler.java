import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageCreateHandler extends ListenerAdapter {
    private final Bot bot;
    private final CooldownManager cooldownManager = new CooldownManager();

    public MessageCreateHandler(Bot bot) { this.bot = bot; }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.isFromGuild()) return;

        var prefix = Config.getInstance().prefix;
        var content = event.getMessage().getContentRaw();
        if (!content.startsWith(prefix)) return;

        var parts = content.substring(prefix.length()).trim().split("\\s+");
        var cmdName = parts[0].toLowerCase();
        var args = parts.length > 1 ? java.util.Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

        var cmd = bot.prefixCommands.get(cmdName);
        if (cmd == null) {
            for (var c : bot.prefixCommands.values()) {
                if (c.aliases.contains(cmdName)) { cmd = c; break; }
            }
            if (cmd == null) return;
        }

        var userId = event.getAuthor().getId();
        var result = cooldownManager.check(userId, cmd.name);
        if (result.onCooldown) {
            event.getChannel().sendMessage("Please wait " + result.remaining + "s before using this command again.").queue();
            return;
        }

        try {
            cmd.handler.execute(event, args);
            PrefixCommandWebhook.sendUsage(userId, event.getAuthor().getName(), cmd.name, event.getGuild().getName(), event.getAuthor().getAvatarUrl());
        } catch (Exception e) {
            System.out.println("  \u274c Error in prefix command " + cmd.name + ": " + e.getMessage());
            event.getChannel().sendMessage("\u274c **Error:** " + e.getMessage()).queue();
            PrefixCommandWebhook.sendError(userId, event.getAuthor().getName(), cmd.name, event.getGuild().getName(), e.getMessage());
        }
    }
}
