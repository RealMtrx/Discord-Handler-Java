package discordhandler.core;

import discordhandler.Config;
import java.util.List;

public class JoinGuildWebhook {
    public static void sendJoinGuildWebhook(String guildName, String guildId, String ownerId, int memberCount, String iconUrl) {
        Config config = Config.getInstance();
        if (!config.isJoinGuildWebhookEnabled()) return;
        WebhookSender.Embed embed = new WebhookSender.Embed();
        embed.title = "\ud83c\udf89 Bot Joined New Server!";
        embed.description = "**Server:** " + guildName + "\n**ID:** " + guildId;
        embed.color = 0x57F287;
        embed.fields = List.of(
                new WebhookSender.Field("\ud83d\udc51 Owner", "<@" + ownerId + ">", true),
                new WebhookSender.Field("\ud83d\udc65 Members", memberCount + " members", true),
                new WebhookSender.Field("\ud83d\udcc5 Joined At", "<t:" + (System.currentTimeMillis() / 1000) + ":F>", true)
        );
        embed.footer = new WebhookSender.Footer(WebhookSender.footerText(config.botName, "Guild Join Logger"));
        embed.timestamp = WebhookSender.makeTimestamp();
        embed.thumbnail = new WebhookSender.Thumbnail(iconUrl != null ? iconUrl : "https://cdn.discordapp.com/embed/avatars/0.png");
        WebhookSender.sendWebhook(config.joinGuildWebhook, embed);
    }
}
