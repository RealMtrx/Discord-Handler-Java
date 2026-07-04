package discordhandler.core;

import discordhandler.Config;
import java.util.List;

public class LeaveGuildWebhook {
    public static void sendLeaveGuildWebhook(String guildId, String guildName, int memberCount, int remainingServers) {
        Config config = Config.getInstance();
        if (!config.isLeaveGuildWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = "\ud83d\udc4b Bot Left Server";
        embed.description = "**Server:** " + guildName + "\n**ID:** " + guildId;
        embed.color = 0xFF0000;
        embed.fields = List.of(
                new WebhookSender.Field("\ud83d\udc65 Members", memberCount + " members", true),
                new WebhookSender.Field("\ud83d\udcc5 Left At", "<t:" + (System.currentTimeMillis() / 1000) + ":F>", true),
                new WebhookSender.Field("\ud83d\udcca Remaining Servers", remainingServers + " servers", true)
        );
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "Guild Leave Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        WebhookSender.sendWebhook(config.leaveGuildWebhook, embed);
    }
}
