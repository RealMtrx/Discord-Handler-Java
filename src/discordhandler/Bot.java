package discordhandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import discordhandler.models.SlashCommand;
import discordhandler.models.PrefixCommand;

import java.util.HashMap;
import java.util.Map;

public class Bot {
    public JDA jda;
    public final Config config;
    public final Map<String, SlashCommand> slashCommands = new HashMap<>();
    public final Map<String, PrefixCommand> prefixCommands = new HashMap<>();

    public Bot() {
        this.config = Config.getInstance();
    }

    public void start(ListenerAdapter... listeners) throws Exception {
        JDABuilder builder = JDABuilder.createDefault(config.token)
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_PRESENCES
                )
                .enableCache(CacheFlag.VOICE_STATE);

        for (var listener : listeners) {
            builder.addEventListeners(listener);
        }

        jda = builder.build();
        jda.awaitReady();
    }

    public void shutdown() {
        if (jda != null) jda.shutdown();
    }
}
