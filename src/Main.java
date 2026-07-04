public class Main {
    public static void main(String[] args) throws Exception {
        Config config = Config.getInstance();

        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("  Starting " + config.botName + "...");
        System.out.println("=".repeat(50));
        System.out.println();

        long startTime = System.currentTimeMillis();

        System.out.println("  \ud83d\udee1\ufe0f Setting up anti-crash...");
        AntiCrash.setup();

        Bot bot = new Bot();

        System.out.println("  \u26a1 Loading slash commands...");
        StartupData slashData = CommandHandler.loadSlashCommands(bot);

        System.out.println("  \ud83d\udcac Loading prefix commands...");
        StartupData prefixData = PrefixHandler.loadPrefixCommands(bot);

        System.out.println("  \ud83d\udce0 Loading events...");
        var listeners = EventHandler.getListeners(bot);
        StartupData eventData = new StartupData(0, 0, listeners.size());

        System.out.println("  \ud83c\udfdb\ufe0f Connecting to MongoDB...");
        Mongo.connect();

        System.out.println("  \u26a1 Starting bot...");
        bot.start(listeners.toArray(new net.dv8tion.jda.api.hooks.ListenerAdapter[0]));

        long elapsed = System.currentTimeMillis() - startTime;
        StartupData total = new StartupData(slashData.totalSlash, prefixData.totalPrefix, eventData.totalEvents);
        Logger.printStartupBanner(total, elapsed / 1000.0);
    }
}
