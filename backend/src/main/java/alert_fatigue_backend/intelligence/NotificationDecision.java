package alert_fatigue_backend.intelligence;

public class NotificationDecision {

    private final boolean shouldNotify;
    private final String reason;

    public NotificationDecision(boolean shouldNotify, String reason) {
        this.shouldNotify = shouldNotify;
        this.reason = reason;
    }

    public boolean isShouldNotify() {
        return shouldNotify;
    }

    public String getReason() {
        return reason;
    }
}