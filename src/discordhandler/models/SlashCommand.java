package discordhandler.models;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

@FunctionalInterface
public interface SlashCommandHandler {
    void execute(SlashCommandInteraction event) throws Exception;
}

public class SlashCommand {
    public String name, description, category;
    public SlashCommandHandler handler;

    public SlashCommand(String name, String description, String category, SlashCommandHandler handler) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.handler = handler;
    }
}
