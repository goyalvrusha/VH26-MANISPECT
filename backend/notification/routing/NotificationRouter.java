package notification.routing;

public class NotificationRouter {

    public NotificationChannel route(boolean shouldNotify, String priority) {

        if (!shouldNotify) {
            return NotificationChannel.NONE;
        }

        if (priority == null) {
            return NotificationChannel.NONE;
        }

        switch (priority.toUpperCase()) {
            case "CRITICAL":
                return NotificationChannel.PAGERDUTY;

            case "HIGH":
            case "MEDIUM":
                return NotificationChannel.SLACK;

            case "LOW":
                return NotificationChannel.EMAIL;

            default:
                return NotificationChannel.NONE;
        }
    }
}   