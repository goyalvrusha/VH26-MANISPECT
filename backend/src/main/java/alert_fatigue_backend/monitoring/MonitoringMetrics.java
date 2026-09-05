package alert_fatigue_backend.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringMetrics {

    private final Counter alertsProcessed;
    private final Counter notificationsSent;
    private final Counter notificationsSkipped;
    private final Counter notificationsFailed;

    public MonitoringMetrics(MeterRegistry meterRegistry) {

        alertsProcessed = Counter.builder("alert_fatigue_alerts_processed")
                .description("Total number of alerts processed")
                .register(meterRegistry);

        notificationsSent = Counter.builder("alert_fatigue_notifications_sent")
                .description("Total number of notifications sent")
                .register(meterRegistry);

        notificationsSkipped = Counter.builder("alert_fatigue_notifications_skipped")
                .description("Total number of notifications skipped")
                .register(meterRegistry);

        notificationsFailed = Counter.builder("alert_fatigue_notifications_failed")
                .description("Total number of notification failures")
                .register(meterRegistry);
    }

    public void recordAlertProcessed() {
        alertsProcessed.increment();
    }

    public void recordNotificationSent() {
        notificationsSent.increment();
    }

    public void recordNotificationSkipped() {
        notificationsSkipped.increment();
    }

    public void recordNotificationFailed() {
        notificationsFailed.increment();
    }

    public double getAlertsProcessed() {
        return alertsProcessed.count();
    }

    public double getNotificationsSent() {
        return notificationsSent.count();
    }

    public double getNotificationsSkipped() {
        return notificationsSkipped.count();
    }

    public double getNotificationsFailed() {
        return notificationsFailed.count();
    }
}