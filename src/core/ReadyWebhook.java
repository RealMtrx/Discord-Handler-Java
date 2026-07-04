import java.util.List;

public class ReadyWebhook {
    public static void sendReadyWebhook(String botUsername, String botId, int serverCount) {
        Config config = Config.getInstance();
        if (!config.isReadyWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = "\ud83d\udfe2 Bot is Online!";
        embed.description = "**Bot:** " + botUsername + "\n**Status:** Online and Ready";
        embed.color = 0x00FF00;
        embed.fields = List.of(
                new WebhookSender.Field("\ud83e\udd16 Bot Info", "**ID:** " + botId, true),
                new WebhookSender.Field("\ud83c\udfe0 Servers", serverCount + " servers", true)
        );
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "System Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        WebhookSender.sendWebhook(config.readyWebhook, embed);
    }
}
