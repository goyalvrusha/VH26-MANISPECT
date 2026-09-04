package notification.service;

public class EmailNotificationSender {

    public NotificationResult send(
            String alertId,
            String message) {

        // Real Email integration will be added later.
        return new NotificationResult(
                alertId,
                "EMAIL",
                "SENT",
                message
        );
    }
}