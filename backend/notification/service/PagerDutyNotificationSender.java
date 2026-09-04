package notification.service;

public class PagerDutyNotificationSender {

    public NotificationResult send(
            String alertId,
            String message,
            String reason) {

        // Real PagerDuty API integration will be added later.
        return new NotificationResult(
                alertId,
                "PAGERDUTY",
                "SENT",
                message,
                reason
        );
    }
}