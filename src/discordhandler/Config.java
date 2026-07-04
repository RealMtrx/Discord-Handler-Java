package discordhandler;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;

public class Config {
    private static Config instance;

    public final String token;
    public final String clientId;
    public final String botName;
    public final String prefix;
    public final List<String> ownerIds;
    public final String mongodbUri;
    public final String errorWebhook;
    public final String slashCommandWebhook;
    public final String prefixCommandWebhook;
    public final String joinGuildWebhook;
    public final String leaveGuildWebhook;
    public final String readyWebhook;

    private Config() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.token = dotenv.get("TOKEN", "#");
        this.clientId = dotenv.get("CLIENT_ID", "#");
        this.botName = dotenv.get("BOT_NAME", "Discord Handler");
        this.prefix = dotenv.get("PREFIX", "$");
        this.ownerIds = List.of(dotenv.get("OWNER_IDS", "#,#").split(","));
        this.mongodbUri = dotenv.get("MONGODB_URI", "#");
        this.errorWebhook = dotenv.get("ERROR_WEBHOOK", "#");
        this.slashCommandWebhook = dotenv.get("SLASH_WEBHOOK", "#");
        this.prefixCommandWebhook = dotenv.get("PREFIX_WEBHOOK", "#");
        this.joinGuildWebhook = dotenv.get("JOIN_WEBHOOK", "#");
        this.leaveGuildWebhook = dotenv.get("LEAVE_WEBHOOK", "#");
        this.readyWebhook = dotenv.get("READY_WEBHOOK", "#");
    }

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }

    public boolean isErrorWebhookEnabled() { return !errorWebhook.equals("#"); }
    public boolean isSlashCommandWebhookEnabled() { return !slashCommandWebhook.equals("#"); }
    public boolean isPrefixCommandWebhookEnabled() { return !prefixCommandWebhook.equals("#"); }
    public boolean isJoinGuildWebhookEnabled() { return !joinGuildWebhook.equals("#"); }
    public boolean isLeaveGuildWebhookEnabled() { return !leaveGuildWebhook.equals("#"); }
    public boolean isReadyWebhookEnabled() { return !readyWebhook.equals("#"); }
}
