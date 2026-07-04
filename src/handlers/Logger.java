public class Logger {
    public static void printStartupBanner(StartupData data, double elapsed) {
        var now = java.time.Instant.now().toString().substring(0, 19) + " UTC";
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("  " + Config.getInstance().botName);
        System.out.println("=".repeat(50));
        System.out.println("  \u23f0 Started at: " + now);
        System.out.println("  \u2699\ufe0f Loaded " + data.totalSlash + " slash commands");
        System.out.println("  \ud83d\udce0 Loaded " + data.totalPrefix + " prefix commands");
        System.out.println("  \ud83c\udf89 Loaded " + data.totalEvents + " events");
        System.out.println("  \u26a1 Ready in " + String.format("%.2f", elapsed) + "s");
        System.out.println("=".repeat(50));
        System.out.println();
    }
}
