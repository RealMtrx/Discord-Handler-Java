package discordhandler.handlers;

import discordhandler.Bot;
import discordhandler.models.StartupData;

public class CommandHandler {
    public static StartupData loadSlashCommands(Bot bot) {
        var data = new StartupData();
        try {
            var pingCmd = discordhandler.commands.slash.public.PingCommand.getCommand();
            bot.slashCommands.put(pingCmd.name, pingCmd);
            System.out.println("  \u2705 Slash command loaded: /" + pingCmd.name + " (" + pingCmd.category + ")");
            data.totalSlash++;
        } catch (Exception e) {
            System.out.println("  \u274c Failed to load slash command: " + e.getMessage());
        }
        return data;
    }
}
