package alert_fatigue_backend.monitoring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import alert_fatigue_backend.alert.Alert;

@Service
public class MonitoringService {

    private final MonitoringLogger monitoringLogger;
    private final MonitoringMetrics monitoringMetrics;

    private final List<MonitoringEvent> recentEvents =
            Collections.synchronizedList(new ArrayList<>());

    private int uniqueAlerts = 0;
    private int duplicateAlerts = 0;

    public MonitoringService(
            MonitoringLogger monitoringLogger,
            MonitoringMetrics monitoringMetrics) {

        this.monitoringLogger = monitoringLogger;
        this.monitoringMetrics = monitoringMetrics;
    }

    public void recordAlertProcessed(Alert alert) {

        String alertId = alert.getId();

        monitoringLogger.logAlertProcessed(alertId);
        monitoringMetrics.recordAlertProcessed();

        boolean duplicate = alert.getOccurrenceCount() > 1;

        if (duplicate) {
            duplicateAlerts++;
        } else {
            uniqueAlerts++;
        }

        addEvent(
                alert,
                "ALERT_PROCESSED",
                "Alert processed",
                "SUCCESS",
                "NONE",
                "PENDING"
        );
    }

    public void recordNotificationSent(
            Alert alert,
            String channel) {

        String alertId = alert.getId();

        monitoringLogger.logNotificationSent(alertId, channel);
        monitoringMetrics.recordNotificationSent();

        addEvent(
                alert,
                "NOTIFICATION_SENT",
                "Notification → " + channel,
                "SENT",
                channel,
                "SENT"
        );
    }

    public void recordNotificationSkipped(
            Alert alert,
            String reason) {

        String alertId = alert.getId();

        monitoringLogger.logNotificationSkipped(alertId, reason);
        monitoringMetrics.recordNotificationSkipped();

        addEvent(
                alert,
                "NOTIFICATION_SKIPPED",
                "Duplicate alert → notification suppressed",
                "SKIPPED",
                "NONE",
                "SUPPRESSED"
        );
    }

    public void recordNotificationFailed(
            Alert alert,
            String channel,
            String reason) {

        String alertId = alert.getId();

        monitoringLogger.logNotificationFailed(alertId, reason);
        monitoringMetrics.recordNotificationFailed();

        addEvent(
                alert,
                "NOTIFICATION_FAILED",
                "Notification failed: " + reason,
                "FAILED",
                channel,
                "FAILED"
        );
    }

    public List<MonitoringEvent> getRecentEvents() {

        synchronized (recentEvents) {
            return new ArrayList<>(recentEvents);
        }
    }

    public int getUniqueAlerts() {
        return uniqueAlerts;
    }

    public int getDuplicateAlerts() {
        return duplicateAlerts;
    }

    private void addEvent(
            Alert alert,
            String type,
            String message,
            String status,
            String channel,
            String decision) {

        synchronized (recentEvents) {

            recentEvents.add(
                    0,
                    new MonitoringEvent(
                            alert.getId(),
                            alert.getAlertName(),
                            alert.getService(),
                            alert.getSeverity(),
                            alert.getPriority(),
                            alert.getStatus(),
                            alert.getOccurrenceCount(),
                            type,
                            message,
                            status,
                            channel,
                            decision,
                            LocalDateTime.now().toString()
                    )
            );

            if (recentEvents.size() > 50) {
                recentEvents.remove(recentEvents.size() - 1);
            }
        }
    }

    public record MonitoringEvent(
            String alertId,
            String alertName,
            String service,
            String severity,
            String priority,
            String alertStatus,
            int occurrenceCount,
            String type,
            String message,
            String deliveryStatus,
            String channel,
            String notificationDecision,
            String timestamp
    ) {}
}