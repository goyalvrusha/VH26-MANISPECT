package notification.service;

import java.util.HashMap;
import java.util.Map;

import notification.client.NotificationHttpClient;
import notification.config.NotificationConfig;

public class PagerDutyNotificationSender {

    private static final String PAGERDUTY_URL =
            "https://events.pagerduty.com/v2/enqueue";

    private final NotificationHttpClient httpClient;
    private final NotificationConfig config;

    public PagerDutyNotificationSender() {
        this.httpClient = new NotificationHttpClient();
        this.config = new NotificationConfig();
    }

    public NotificationResult send(
            String alertId,
            String message,
            String reason) {

        String routingKey = config.getPagerDutyRoutingKey();

        // No PagerDuty account/key configured.
        // Return FAILED so NotificationService can use fallback.
        if (routingKey == null || routingKey.isBlank()) {

            return new NotificationResult(
                    alertId,
                    "PAGERDUTY",
                    "FAILED",
                    "PagerDuty routing key is not configured",
                    reason
            );
        }

        Map<String, Object> payload = new HashMap<>();

        payload.put("routing_key", routingKey);
        payload.put("event_action", "trigger");
        payload.put("dedup_key", alertId);

        Map<String, Object> eventPayload = new HashMap<>();

        eventPayload.put("summary", message);
        eventPayload.put("source", "alert-fatigue-backend");
        eventPayload.put("severity", "critical");

        payload.put("payload", eventPayload);

        boolean success = httpClient.post(
                PAGERDUTY_URL,
                payload
        );

        if (success) {

            return new NotificationResult(
                    alertId,
                    "PAGERDUTY",
                    "SENT",
                    "PagerDuty event triggered successfully",
                    reason
            );
        }

        return new NotificationResult(
                alertId,
                "PAGERDUTY",
                "FAILED",
                "PagerDuty event delivery failed",
                reason
        );
    }
}