package notification.service;

public class SlackNotificationSender {

    public NotificationResult send(
            String alertId,
            String message,
            String reason) {

        // Real Slack API integration will be added later.
        return new NotificationResult(
                alertId,
                "SLACK",
                "SENT",
                message,
                reason
        );
    }
}