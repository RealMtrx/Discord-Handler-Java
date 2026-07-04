package discordhandler.models;

import org.bson.Document;

public class User {
    public String id;
    public long createdAt;

    public User(String id) {
        this.id = id;
        this.createdAt = System.currentTimeMillis();
    }

    public User(Document doc) {
        this.id = doc.getString("_id");
        this.createdAt = doc.getLong("createdAt");
    }

    public Document toDocument() {
        return new Document("_id", id).append("createdAt", createdAt);
    }
}
