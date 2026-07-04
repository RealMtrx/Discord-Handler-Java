package discordhandler.models;

public class StartupData {
    public int totalSlash;
    public int totalPrefix;
    public int totalEvents;

    public StartupData() {
        this(0, 0, 0);
    }

    public StartupData(int totalSlash, int totalPrefix, int totalEvents) {
        this.totalSlash = totalSlash;
        this.totalPrefix = totalPrefix;
        this.totalEvents = totalEvents;
    }
}
