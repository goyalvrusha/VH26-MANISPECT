package notification.service;

public class EmailNotificationSender {

    public NotificationResult send(
            String alertId,
            String message,
            String reason) {

        return new NotificationResult(
                alertId,
                "EMAIL",
                "SENT",
                message,
                reason
        );
    }
}