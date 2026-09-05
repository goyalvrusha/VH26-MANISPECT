package notification.service;

import java.util.Map;

import notification.client.NotificationHttpClient;
import notification.config.NotificationConfig;

public class SlackNotificationSender {

    private final NotificationHttpClient httpClient;
    private final NotificationConfig config;

    public SlackNotificationSender() {
        this.httpClient = new NotificationHttpClient();
        this.config = new NotificationConfig();
    }

    public NotificationResult send(
            String alertId,
            String message,
            String reason) {

        String webhookUrl = config.getSlackWebhookUrl();

        if (webhookUrl == null || webhookUrl.isBlank()) {
            return new NotificationResult(
                    alertId,
                    "SLACK",
                    "FAILED",
                    "Slack webhook is not configured",
                    reason
            );
        }

        boolean success = httpClient.post(
                webhookUrl,
                Map.of("text", message)
        );

        if (success) {
            return new NotificationResult(
                    alertId,
                    "SLACK",
                    "SENT",
                    message,
                    reason
            );
        }

        return new NotificationResult(
                alertId,
                "SLACK",
                "FAILED",
                "Slack notification delivery failed",
                reason
        );
    }
}