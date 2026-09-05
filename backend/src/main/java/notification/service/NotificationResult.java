package notification.service;

public class NotificationResult {

    private final String alertId;
    private final String channel;
    private final String status;
    private final String message;
    private final String reason;

    public NotificationResult(
            String alertId,
            String channel,
            String status,
            String message,
            String reason) {

        this.alertId = alertId;
        this.channel = channel;
        this.status = status;
        this.message = message;
        this.reason = reason;
    }

    public String getAlertId() {
        return alertId;
    }

    public String getChannel() {
        return channel;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }
}