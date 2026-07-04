package discordhandler.core;

import java.time.Instant;

public class CommandUtils {
    public static class ErrorReport {
        public final String message, commandName, timestamp;
        public ErrorReport(String message, String commandName) {
            this.message = message;
            this.commandName = commandName;
            this.timestamp = Instant.now().toString();
        }
    }

    public static ErrorReport formatError(Exception ex, String commandName) {
        return new ErrorReport(ex.getMessage(), commandName);
    }

    public static void logCommandUsage(String userId, String userName, String commandName, String guildName) {
        System.out.println("[Command] " + userName + " (" + userId + ") used " + commandName + " in " + guildName);
    }
}
