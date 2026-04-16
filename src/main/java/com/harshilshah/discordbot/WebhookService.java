package com.harshilshah.discordbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);

    private static final Map<Long, String> cache = new HashMap<>();

    public static String getWebhook(long channelId) {

        if (cache.containsKey(channelId)) {
            return cache.get(channelId);
        }

        String query = "SELECT webhook_url FROM webhooks WHERE channel_id = ? LIMIT 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, channelId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String url = rs.getString("webhook_url");
                cache.put(channelId, url);
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveWebhook(long guildId, long channelId, String webhookUrl, long userId) {
        String query = """
                        INSERT INTO webhooks (guild_id, channel_id, webhook_url, created_by)
                        VALUES (?, ?, ?, ?)
                        ON CONFLICT (channel_id)
                        DO UPDATE SET webhook_url = EXCLUDED.webhook_url
                        """;
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, guildId);
            ps.setLong(2, channelId);
            ps.setString(3, webhookUrl);
            ps.setLong(4, userId);

            ps.executeUpdate();

            cache.put(channelId, webhookUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFromCache(long channelId) {
        cache.remove(channelId);
    }
}
