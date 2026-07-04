import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
    private final Map<String, Map<String, Long>> cooldowns = new ConcurrentHashMap<>();

    public Result check(String userId, String command) { return check(userId, command, 3000); }

    public Result check(String userId, String command, int cooldownMs) {
        long now = System.currentTimeMillis();
        Map<String, Long> map = cooldowns.computeIfAbsent(command, k -> new ConcurrentHashMap<>());
        Long expires = map.get(userId);
        if (expires != null && now < expires) return new Result(true, (int) ((expires - now) / 1000));
        map.put(userId, now + cooldownMs);
        return new Result(false, 0);
    }

    public static class Result {
        public final boolean onCooldown;
        public final int remaining;
        public Result(boolean onCooldown, int remaining) { this.onCooldown = onCooldown; this.remaining = remaining; }
    }
}
