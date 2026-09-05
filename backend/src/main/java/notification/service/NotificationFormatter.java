package notification.service;

public class NotificationFormatter {

    public String format(
            String id,
            String service,
            String alertName,
            String priority,
            String severity,
            String message) {

        return String.format(
                "[%s] %s | %s | Priority: %s | Severity: %s | %s",
                priority,
                alertName,
                service,
                priority,
                severity,
                message
        );
    }
}