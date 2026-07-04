package discordhandler.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class WebhookSender {
    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final Gson gson = new Gson();

    public static class Field {
        public String name;
        public String value;
        public boolean inline;

        public Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }
    }

    public static class Footer {
        public String text;
        public Footer(String text) { this.text = text; }
    }

    public static class Thumbnail {
        public String url;
        public Thumbnail(String url) { this.url = url; }
    }

    public static class Embed {
        public String title;
        public String description;
        public int color;
        public List<Field> fields;
        public Footer footer;
        public String timestamp;
        public Thumbnail thumbnail;

        public JsonObject toJson() {
            JsonObject obj = new JsonObject();
            obj.addProperty("title", title != null ? title : "");
            obj.addProperty("description", description != null ? description : "");
            obj.addProperty("color", color);
            obj.addProperty("timestamp", timestamp != null ? timestamp : makeTimestamp());

            if (fields != null && !fields.isEmpty()) {
                JsonArray arr = new JsonArray();
                for (Field f : fields) {
                    JsonObject fObj = new JsonObject();
                    fObj.addProperty("name", f.name);
                    fObj.addProperty("value", f.value);
                    fObj.addProperty("inline", f.inline);
                    arr.add(fObj);
                }
                obj.add("fields", arr);
            }

            if (footer != null) {
                JsonObject fObj = new JsonObject();
                fObj.addProperty("text", footer.text);
                obj.add("footer", fObj);
            }

            if (thumbnail != null) {
                JsonObject tObj = new JsonObject();
                tObj.addProperty("url", thumbnail.url);
                obj.add("thumbnail", tObj);
            }

            return obj;
        }
    }

    public static boolean sendWebhook(String url, Embed embed) {
        if (url == null || url.equals("#")) return false;

        try {
            JsonObject payload = new JsonObject();
            JsonArray embeds = new JsonArray();
            embeds.add(embed.toJson());
            payload.add("embeds", embeds);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(payload)))
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() < 400;
        } catch (Exception e) {
            return false;
        }
    }

    public static String makeTimestamp() {
        return Instant.now().toString();
    }

    public static String footerText(String botName, String suffix) {
        return botName + " \u2022 " + suffix;
    }
}
