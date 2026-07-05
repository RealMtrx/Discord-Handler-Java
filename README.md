# Discord Handler Java

A modern, feature-rich Discord bot handler built with **JDA v5**, featuring both slash commands and prefix commands with a robust modular architecture designed for scalability and maintainability.

## 🚀 Features

- **Dual Command System**: Support for both slash commands and prefix commands
- **Modular Architecture**: Clean separation of concerns with dedicated handlers
- **Anti-Crash System**: Comprehensive error handling and monitoring
- **Event-Driven**: Fully event-driven architecture
- **Webhook Logging**: Real-time logging for errors and guild events
- **MongoDB Integration**: Persistent data storage with MongoDB Java driver
- **Cooldown System**: Per-command cooldown management
- **Environment Configuration**: Secure configuration with dotenv-java

## 📁 Project Structure

```
Discord-Handler-Java/
├── pom.xml                       # Maven project configuration
├── src/                          # Source code (no package declarations)
│   ├── Main.java                 # Main bot entry point
│   ├── Config.java               # Bot configuration from .env
│   ├── Bot.java                  # Bot initialization
│   ├── Core/                     # Core utilities
│   │   ├── CommandUtils.java     # Cooldown and utilities
│   │   ├── Emojis.java           # Centralized emoji definitions
│   │   └── WebhookUtil.java      # Webhook utility
│   ├── Database/
│   │   └── Mongo.java            # MongoDB connection setup
│   ├── Events/                   # Discord event handlers
│   │   ├── GuildCreate.java      # Handler when bot joins a server
│   │   ├── GuildDelete.java      # Handler when bot leaves a server
│   │   ├── InteractionCreate.java# Handles slash command interactions
│   │   ├── MessageCreate.java    # Handles prefix commands
│   │   └── Ready.java            # Bot ready event
│   ├── Handlers/                 # Handlers for modularity
│   │   ├── AntiCrash.java        # Crash prevention and error handling
│   │   └── Logger.java           # Logger for bot activity
│   ├── Models/
│   │   └── UserModel.java        # User data model
│   └── Commands/
│       ├── Prefix/               # Prefix commands
│       │   └── PingCommand.java  # Example prefix ping command
│       └── Slash/                # Slash commands
│           └── PingCommand.java  # Example slash ping command
```

## 🔧 Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/RealMtrx/Discord-Handler-Java.git
   cd Discord-Handler-Java
   ```

2. **Build dependencies**

   ```bash
   mvn clean compile
   ```

3. **Environment Setup**

   Copy `.env.example` to `.env` and fill in your values:

   ```env
   TOKEN=your_bot_token_here
   PREFIX=!
   BOT_NAME=Discord Handler
   MONGO_URI=mongodb://localhost:27017/discord-handler
   ERROR_WEBHOOK=https://discord.com/api/webhooks/your_webhook
   GUILD_LOG_WEBHOOK=https://discord.com/api/webhooks/your_webhook
   ```

4. **Run the bot**

   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   # or package and run
   mvn clean package
   java -jar target/discord-handler-1.0.0.jar
   ```

## 📋 Dependencies

- **JDA**: v5.0 - Discord API wrapper
- **mongodb-driver-sync**: v4.11 - MongoDB driver
- **dotenv-java**: v3.0 - Environment variable management
- **json**: v20231013 - JSON parsing for webhooks

## 📝 Command Development

### Creating Slash Commands

Create a new file in `src/Commands/Slash/[name]Command.java`:

```java
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand {
    public static String name = "ping";
    public static String description = "Replies with Pong!";

    public static void execute(SlashCommandInteractionEvent event) {
        event.reply("Pong! 🏓").queue();
    }
}
```

### Creating Prefix Commands

Create a new file in `src/Commands/Prefix/[name]Command.java`:

```java
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PrefixPingCommand {
    public static String name = "ping";

    public static void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong! 🏓").queue();
    }
}
```

---

**Discord Handler** - A modern, scalable Discord bot framework built with Java.
