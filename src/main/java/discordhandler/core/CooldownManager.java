package discordhandler.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
    private final Map<String, Map<String, Long>> cooldowns = new ConcurrentHashMap<>();

    public CooldownResult check(String userId, String command, int cooldownMs) {
        long now = System.currentTimeMillis();

        Map<String, Long> commandMap = cooldowns.computeIfAbsent(command, k -> new ConcurrentHashMap<>());

        Long expires = commandMap.get(userId);
        if (expires != null && now < expires) {
            int remaining = (int) ((expires - now) / 1000);
            return new CooldownResult(true, remaining);
        }

        commandMap.put(userId, now + cooldownMs);
        return new CooldownResult(false, 0);
    }

    public CooldownResult check(String userId, String command) {
        return check(userId, command, 3000);
    }

    public static class CooldownResult {
        public final boolean onCooldown;
        public final int remaining;

        public CooldownResult(boolean onCooldown, int remaining) {
            this.onCooldown = onCooldown;
            this.remaining = remaining;
        }
    }
}
