public class PrefixHandler {
    public static StartupData loadPrefixCommands(Bot bot) {
        var data = new StartupData();
        try {
            var pingCmd = PrefixPingCommand.getCommand();
            bot.prefixCommands.put(pingCmd.name, pingCmd);
            System.out.println("  \u2705 Prefix command loaded: " + bot.config.prefix + pingCmd.name + " (" + pingCmd.category + ")");
            data.totalPrefix++;
        } catch (Exception e) {
            System.out.println("  \u274c Failed to load prefix command: " + e.getMessage());
        }
        return data;
    }
}
