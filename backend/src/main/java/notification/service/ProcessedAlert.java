package notification.service;

public class ProcessedAlert {

    private final String id;
    private final String service;
    private final String alertName;
    private final String priority;
    private final String severity;
    private final String message;
    private final boolean shouldNotify;
    private final String reason;

    public ProcessedAlert(
            String id,
            String service,
            String alertName,
            String priority,
            String severity,
            String message,
            boolean shouldNotify,
            String reason) {

        this.id = id;
        this.service = service;
        this.alertName = alertName;
        this.priority = priority;
        this.severity = severity;
        this.message = message;
        this.shouldNotify = shouldNotify;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public String getAlertName() {
        return alertName;
    }

    public String getPriority() {
        return priority;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShouldNotify() {
        return shouldNotify;
    }

    public String getReason() {
        return reason;
    }
}