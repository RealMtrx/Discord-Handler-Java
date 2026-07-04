import java.util.List;

public class SlashCommandWebhook {
    public static void sendUsage(String userId, String userName, String commandName, String guildName, String avatarUrl) {
        Config config = Config.getInstance();
        if (!config.isSlashCommandWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = Emojis.SLASH + " Slash Command Used";
        embed.description = "**Command:** `/" + commandName + "`";
        embed.color = 0x5865F2;
        embed.fields = List.of(
                new WebhookSender.Field(Emojis.USER + " User Info", "**UserName:** " + userName + "\n**ID:** " + userId, true),
                new WebhookSender.Field(Emojis.SERVER + " Server", guildName, true),
                new WebhookSender.Field(Emojis.LOADING + " Time", "<t:" + (System.currentTimeMillis() / 1000) + ":R>", true)
        );
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "Slash Command Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        embed.thumbnail = new WebhookSender.Thumbnail(avatarUrl != null ? avatarUrl : "https://cdn.discordapp.com/embed/avatars/0.png");
        WebhookSender.sendWebhook(config.slashCommandWebhook, embed);
    }

    public static void sendError(String userId, String userName, String commandName, String guildName, String errorMsg) {
        Config config = Config.getInstance();
        if (!config.isSlashCommandWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = Emojis.ERROR + " Slash Command Error";
        embed.description = "**Command:** `/" + commandName + "`\n**Error:** " + errorMsg;
        embed.color = 0xFF0000;
        embed.fields = List.of(
                new WebhookSender.Field(Emojis.USER + " User Info", userName + " (" + userId + ")", true),
                new WebhookSender.Field(Emojis.SERVER + " Server", guildName, true),
                new WebhookSender.Field(Emojis.LOADING + " Time", "<t:" + (System.currentTimeMillis() / 1000) + ":F>", true)
        );
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "Error Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        WebhookSender.sendWebhook(config.slashCommandWebhook, embed);
    }
}
