<div align="center">
  <h1>Discord Handler — Java</h1>
  <p><strong>A production-ready Discord bot framework built with JDA v5 and MongoDB — slash commands, prefix commands, anti-crash, webhook logging, and a modular src/ architecture.</strong></p>

  <p>
    <a href="https://github.com/RealMtrx/Discord-Handler-Java/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="License"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Java/releases"><img src="https://img.shields.io/badge/version-0.9.0--beta-yellow" alt="Version 0.9.0 Beta"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Java/stargazers"><img src="https://img.shields.io/github/stars/RealMtrx/Discord-Handler-Java" alt="Stars"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Java/issues"><img src="https://img.shields.io/github/issues/RealMtrx/Discord-Handler-Java" alt="Issues"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Java/network"><img src="https://img.shields.io/github/forks/RealMtrx/Discord-Handler-Java" alt="Forks"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler/graphs/contributors"><img src="https://img.shields.io/badge/ecosystem-26%20repos-brightgreen" alt="26 Repos"></a>
    <a href="https://discord.gg/0hu2"><img src="https://img.shields.io/badge/discord-0hu2-5865F2" alt="Discord"></a>
  </p>

  <br>

  <p>
    <a href="#-features">Features</a> •
    <a href="#-quick-start">Quick Start</a> •
    <a href="#-project-structure">Structure</a> •
    <a href="#-api-reference">API</a> •
    <a href="#-database-edition">SQL Edition</a> •
    <a href="#-related-repositories">Ecosystem</a>
  </p>
</div>

---

## Overview

Discord Handler Java is a production-ready bot framework using **JDA 5.2.0** with Java 21. It provides a dual command system (slash + prefix), a comprehensive anti-crash layer, rich webhook logging (error, slash, prefix, guild join/leave, ready), MongoDB persistence, and per-command cooldowns — all organized in a flat `src/` structure without package declarations for simplicity.

## Features

- **Dual Command System** — Full support for both slash and prefix commands with automatic registration
- **Anti-Crash System** — Global exception catching and error reporting via webhook
- **Webhook Logging** — Dedicated webhooks for errors, slash commands, prefix commands, guild join/leave, and bot ready events
- **MongoDB Integration** — Persistent user data via `mongodb-driver-sync` 5.2.0
- **Cooldown Manager** — Per-command cooldown enforcement
- **Environment Configuration** — `dotenv-java` 3.1.0 for secure config management
- **Event-Driven** — Modular event listeners for all major Discord gateway events
- **Startup Banner** — Clean console report of loaded commands, events, and startup time

## Quick Start

```bash
# Clone the repository
git clone https://github.com/RealMtrx/Discord-Handler-Java.git
cd Discord-Handler-Java

# Build dependencies
mvn clean compile

# Configure environment
cp .env.example .env
# Edit .env with your bot token, MongoDB URI, and webhook URLs

# Run the bot
mvn exec:java -Dexec.mainClass="Main"
# Or build and run
mvn clean package
java -jar target/discord-handler-1.0.0.jar
```

### Environment Variables

```env
TOKEN=your_bot_token
CLIENT_ID=your_client_id
BOT_NAME=Discord Handler
PREFIX=$
OWNER_IDS=owner_id_1,owner_id_2
MONGODB_URI=mongodb://localhost:27017/discord-handler
ERROR_WEBHOOK=https://discord.com/api/webhooks/...
SLASH_WEBHOOK=https://discord.com/api/webhooks/...
PREFIX_WEBHOOK=https://discord.com/api/webhooks/...
JOIN_WEBHOOK=https://discord.com/api/webhooks/...
LEAVE_WEBHOOK=https://discord.com/api/webhooks/...
READY_WEBHOOK=https://discord.com/api/webhooks/...
```

## Project Structure

```
Discord-Handler-Java/
├── pom.xml                           # Maven build (Java 21, shade plugin)
├── src/                              # Flat source directory
│   ├── Main.java                     # Entry point — boots anti-crash, loads commands/events, starts bot
│   ├── Bot.java                      # JDA builder with gateway intents & cache config
│   ├── Config.java                   # Singleton .env config parser
│   ├── Core/
│   │   ├── CommandUtils.java         # Cooldown map & utility methods
│   │   ├── CooldownManager.java      # Per-user, per-command cooldown tracker
│   │   ├── Emojis.java               # Centralized emoji constants
│   │   ├── ErrorWebhook.java         # Error event webhook sender
│   │   ├── JoinGuildWebhook.java     # Guild join notification webhook
│   │   ├── LeaveGuildWebhook.java    # Guild leave notification webhook
│   │   ├── PrefixCommandWebhook.java # Prefix command usage webhook
│   │   ├── ReadyWebhook.java         # Bot ready event webhook
│   │   ├── SlashCommandWebhook.java  # Slash command usage webhook
│   │   └── WebhookSender.java        # Low-level webhook HTTP sender
│   ├── Database/
│   │   └── Mongo.java                # MongoDB client & collection setup
│   ├── Events/
│   │   ├── GuildCreateHandler.java   # onGuildJoin
│   │   ├── GuildDeleteHandler.java   # onGuildLeave
│   │   ├── InteractionCreateHandler.java  # Slash command dispatcher
│   │   ├── MessageCreateHandler.java # Prefix command dispatcher
│   │   └── ReadyHandler.java         # onReady
│   ├── Handlers/
│   │   ├── AntiCrash.java            # Global exception handler
│   │   ├── CommandHandler.java       # Slash command loader & registrar
│   │   ├── EventHandler.java         # Event listener loader
│   │   ├── Logger.java               # Startup banner & logging utilities
│   │   └── PrefixHandler.java        # Prefix command loader
│   ├── Models/
│   │   ├── PrefixCommand.java        # Prefix command data model
│   │   ├── SlashCommand.java         # Slash command data model
│   │   ├── StartupData.java          # Startup statistics container
│   │   └── User.java                 # MongoDB user document model
│   └── Commands/
│       ├── Prefix/
│       │   └── PingCommand.java      # Example prefix command
│       └── Slash/
│           └── PingCommand.java      # Example slash command
```

## API Reference

### Bot Initialization

```java
// Main.java — boots the bot
Config config = Config.getInstance();
AntiCrash.setup();
Bot bot = new Bot();
StartupData slashData = CommandHandler.loadSlashCommands(bot);
StartupData prefixData = PrefixHandler.loadPrefixCommands(bot);
Mongo.connect();
bot.start(listeners);
```

### Creating Slash Commands

Create a file in `src/Commands/Slash/<Name>Command.java`:

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

Create a file in `src/Commands/Prefix/<Name>Command.java`:

```java
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand {
    public static String name = "ping";

    public static void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong! 🏓").queue();
    }
}
```

## Adding Commands

Commands are auto-discovered via reflection — no manual registration needed. Simply drop a new class in the correct `Commands/Slash/` or `Commands/Prefix/` directory following the naming convention `<Name>Command.java` with the required `name`, `description` (slash only), and `execute` method.

## Database Edition

This repository uses **MongoDB** for persistence. If you prefer a relational database, check out the **SQL Edition**:

[![Discord-Handler-Java-Sequelize](https://img.shields.io/badge/Discord--Handler--Java--Sequelize-Hibernate%20ORM%20%2B%20SQLite-blue)](https://github.com/RealMtrx/Discord-Handler-Java-Sequelize)

The SQL edition replaces `Mongo.java` with `HibernateUtil.java` and uses JPA/Hibernate annotations on the `User` entity, with SQLite as the default dialect.

## Related Repositories

The Discord Handler ecosystem spans **26 repositories** across 13 programming languages — each with a MongoDB edition and a SQL (Sequelize) edition.

### Core Framework (MongoDB)

| Language | Repository |
|---|---|
| TypeScript | [Discord-Handler-Ts](https://github.com/RealMtrx/Discord-Handler-Ts) |
| JavaScript | [Discord-Handler-Js](https://github.com/RealMtrx/Discord-Handler-Js) |
| Python | [Discord-Handler-Py](https://github.com/RealMtrx/Discord-Handler-Py) |
| Java | [Discord-Handler-Java](https://github.com/RealMtrx/Discord-Handler-Java) |
| Kotlin | [Discord-Handler-Kt](https://github.com/RealMtrx/Discord-Handler-Kt) |
| C++ | [Discord-Handler-Cpp](https://github.com/RealMtrx/Discord-Handler-Cpp) |
| C# | [Discord-Handler-Cs](https://github.com/RealMtrx/Discord-Handler-Cs) |
| Go | [Discord-Handler-Go](https://github.com/RealMtrx/Discord-Handler-Go) |
| Rust | [Discord-Handler-Rs](https://github.com/RealMtrx/Discord-Handler-Rs) |
| Dart | [Discord-Handler-Dart](https://github.com/RealMtrx/Discord-Handler-Dart) |
| PHP | [Discord-Handler-Php](https://github.com/RealMtrx/Discord-Handler-Php) |
| Ruby | [Discord-Handler-Rb](https://github.com/RealMtrx/Discord-Handler-Rb) |
| Lua | [Discord-Handler-Lua](https://github.com/RealMtrx/Discord-Handler-Lua) |

### Database Editions (SQL)

| Language | Repository |
|---|---|
| TypeScript | [Discord-Handler-Ts-Sequelize](https://github.com/RealMtrx/Discord-Handler-Ts-Sequelize) |
| JavaScript | [Discord-Handler-Js-Sequelize](https://github.com/RealMtrx/Discord-Handler-Js-Sequelize) |
| Python | [Discord-Handler-Py-Sequelize](https://github.com/RealMtrx/Discord-Handler-Py-Sequelize) |
| Java | [Discord-Handler-Java-Sequelize](https://github.com/RealMtrx/Discord-Handler-Java-Sequelize) |
| Kotlin | [Discord-Handler-Kt-Sequelize](https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize) |
| C++ | [Discord-Handler-Cpp-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cpp-Sequelize) |
| C# | [Discord-Handler-Cs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cs-Sequelize) |
| Go | [Discord-Handler-Go-Sequelize](https://github.com/RealMtrx/Discord-Handler-Go-Sequelize) |
| Rust | [Discord-Handler-Rs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rs-Sequelize) |
| Dart | [Discord-Handler-Dart-Sequelize](https://github.com/RealMtrx/Discord-Handler-Dart-Sequelize) |
| PHP | [Discord-Handler-Php-Sequelize](https://github.com/RealMtrx/Discord-Handler-Php-Sequelize) |
| Ruby | [Discord-Handler-Rb-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rb-Sequelize) |
| Lua | [Discord-Handler-Lua-Sequelize](https://github.com/RealMtrx/Discord-Handler-Lua-Sequelize) |

> **Hub repository:** [Discord-Handler](https://github.com/RealMtrx/Discord-Handler) — the multi-language overview and documentation hub.

## License

This project is licensed under the MIT License — see the [LICENSE](https://github.com/RealMtrx/Discord-Handler-Java/blob/main/LICENSE) file for details.

---

Built by **Mtrx** — Discord: **0hu2**

[![Discord-Handler](https://img.shields.io/badge/Discord--Handler-Ecosystem%20Hub-5865F2)](https://github.com/RealMtrx/Discord-Handler)
