package discordhandler.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import discordhandler.Config;
import discordhandler.models.User;
import org.bson.Document;
import java.util.concurrent.TimeUnit;

public class Mongo {
    private static MongoClient client;
    private static MongoDatabase db;

    public static boolean connect() {
        Config config = Config.getInstance();
        String uri = config.mongodbUri;
        if (uri == null || uri.equals("#")) {
            System.out.println("  \u274c MongoDB URI not configured, skipping");
            return false;
        }
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(uri))
                    .applyToClusterSettings(b -> b.serverSelectionTimeout(10, TimeUnit.SECONDS))
                    .build();
            client = MongoClients.create(settings);
            db = client.getDatabase("discord_bot");
            db.runCommand(new Document("ping", 1));
            System.out.println("  \u2705 MongoDB connected successfully");
            return true;
        } catch (Exception e) {
            System.out.println("  \u274c MongoDB connection failed: " + e.getMessage());
            return false;
        }
    }

    public static User getUser(String userId) {
        if (db == null) return null;
        MongoCollection<Document> collection = db.getCollection("users");
        Document doc = collection.find(new Document("_id", userId)).first();
        return doc != null ? new User(doc) : null;
    }

    public static void createOrUpdateUser(String userId) {
        if (db == null) return;
        db.getCollection("users").updateOne(
                new Document("_id", userId),
                new Document("$setOnInsert", new Document("_id", userId).append("createdAt", System.currentTimeMillis())),
                new UpdateOptions().upsert(true)
        );
    }
}
