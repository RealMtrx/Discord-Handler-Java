package discordhandler.core;

import discordhandler.Config;
import java.util.List;

public class ErrorWebhook {
    public static void sendErrorWebhook(String errorMsg) {
        Config config = Config.getInstance();
        if (!config.isErrorWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = "\u274c Bot Error Report";
        embed.description = "**Error:** " + errorMsg;
        embed.color = 0xFF0000;
        embed.fields = List.of(new WebhookSender.Field("\ud83d\udcc5 Timestamp", WebhookSender.makeTimestamp(), true));
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "Error Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        WebhookSender.sendWebhook(config.errorWebhook, embed);
    }
}
