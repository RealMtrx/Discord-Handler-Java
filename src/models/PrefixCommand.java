import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

@FunctionalInterface
public interface PrefixCommandHandler {
    void execute(MessageReceivedEvent event, String[] args) throws Exception;
}

public class PrefixCommand {
    public String name, description, category;
    public List<String> aliases;
    public PrefixCommandHandler handler;

    public PrefixCommand(String name, String description, String category, List<String> aliases, PrefixCommandHandler handler) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.aliases = aliases;
        this.handler = handler;
    }
}
