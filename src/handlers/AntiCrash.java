public class AntiCrash {
    public static void setup() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println("  \u274c Unhandled Exception: " + throwable.getMessage());
            throwable.printStackTrace();
            ErrorWebhook.sendErrorWebhook("**Unhandled Exception**\n```" + truncate(throwable.getMessage(), 1900) + "```");
        });
        System.out.println("  \u2705 AntiCrash handler set up");
    }

    private static String truncate(String str, int max) {
        return str != null && str.length() > max ? str.substring(0, max) : str;
    }
}
