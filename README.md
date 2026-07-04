# Discord Handler (Java)

A modern, feature-rich Discord bot handler built with **Java** and **JDA v5**, featuring both slash commands and prefix commands with a robust modular architecture designed for scalability and maintainability.

## Features

- **Dual Command System**: Support for both slash commands (`/ping`) and prefix commands (`$ping`)
- **Modular Architecture**: Clean separation of concerns with dedicated handlers
- **Anti-Crash System**: Comprehensive error handling and monitoring
- **Event-Driven**: Fully event-driven architecture via `ListenerAdapter`
- **Webhook Logging**: Real-time logging for errors, commands, guild events, and bot status
- **MongoDB Integration**: Persistent data storage with MongoDB Driver
- **Cooldown System**: Per-command cooldown management
- **Environment Configuration**: Secure configuration management with dotenv-java

## Project Structure

```
Discord-Handler-Java/
├── pom.xml                       # Maven project file
├── .env.example                  # Environment configuration template
├── .gitignore
├── LICENSE
├── README.md
└── src/main/java/discordhandler/
    ├── Main.java                 # Entry point
    ├── Config.java               # Bot configuration
    ├── Bot.java                  # Bot class (wraps JDA)
    ├── core/                     # Core utilities and webhooks
    │   ├── Emojis.java
    │   ├── CooldownManager.java
    │   ├── CommandUtils.java
    │   ├── WebhookSender.java    # Webhook base sender
    │   ├── ErrorWebhook.java
    │   ├── JoinGuildWebhook.java
    │   ├── LeaveGuildWebhook.java
    │   ├── PrefixCommandWebhook.java
    │   ├── ReadyWebhook.java
    │   └── SlashCommandWebhook.java
    ├── database/
    │   └── Mongo.java            # MongoDB connection
    ├── events/                   # Discord event listeners
    │   ├── ReadyHandler.java
    │   ├── InteractionCreateHandler.java
    │   ├── MessageCreateHandler.java
    │   ├── GuildCreateHandler.java
    │   └── GuildDeleteHandler.java
    ├── handlers/                 # Loaders and registrars
    │   ├── AntiCrash.java
    │   ├── CommandHandler.java
    │   ├── EventHandler.java
    │   ├── Logger.java
    │   └── PrefixHandler.java
    ├── models/                   # Data models
    │   ├── StartupData.java
    │   ├── SlashCommand.java
    │   ├── PrefixCommand.java
    │   └── User.java
    └── commands/
        ├── slash/public/PingCommand.java
        └── prefix/public/PingCommand.java
```

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/RealMtrx/Discord-Handler-Java.git
   cd Discord-Handler-Java
   ```

2. **Build with Maven**

   ```bash
   mvn clean package
   ```

3. **Environment Setup**

   Copy `.env.example` to `.env` and fill in your values:

   ```env
   TOKEN=your_bot_token
   CLIENT_ID=your_client_id
   BOT_NAME=Discord Handler
   PREFIX=$
   OWNER_IDS=owner_id_1,owner_id_2
   MONGODB_URI=your_mongo_uri
   ERROR_WEBHOOK=#
   SLASH_WEBHOOK=#
   PREFIX_WEBHOOK=#
   JOIN_WEBHOOK=#
   LEAVE_WEBHOOK=#
   READY_WEBHOOK=#
   ```

4. **Run the bot**

   ```bash
   java -jar target/discord-handler-1.0.0.jar
   ```

## Requirements

- **Java**: 21+
- **JDA**: 5.2.0 — Discord API wrapper
- **MongoDB Driver**: 5.2.0 — MongoDB driver
- **dotenv-java**: 3.1.0 — Environment variable management
- **Gson**: 2.11.0 — JSON serialization for webhooks

## Command Development

### Creating Slash Commands

Create a new file in `src/main/java/discordhandler/commands/slash/[category]/[Name].java`:

```java
package discordhandler.commands.slash.[category];

import discordhandler.models.SlashCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class [Name] {
    public static SlashCommand getCommand() {
        return new SlashCommand(
                "commandname",
                "Command description",
                "[category]",
                (SlashCommandInteraction event) -> {
                    event.reply("Response").queue();
                }
        );
    }
}
```

### Creating Prefix Commands

Create a new file in `src/main/java/discordhandler/commands/prefix/[category]/[Name].java`:

```java
package discordhandler.commands.prefix.[category];

import discordhandler.models.PrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public class [Name] {
    public static PrefixCommand getCommand() {
        return new PrefixCommand(
                "commandname",
                "Command description",
                "[category]",
                List.of("alias1"),
                (MessageReceivedEvent event, String[] args) -> {
                    event.getChannel().sendMessage("Response").queue();
                }
        );
    }
}
```

---

**Discord Handler** - A modern, scalable Discord bot framework built with Java.
