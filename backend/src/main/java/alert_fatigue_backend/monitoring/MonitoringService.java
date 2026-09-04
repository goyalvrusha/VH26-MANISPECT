package alert_fatigue_backend.monitoring;

import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    private final MonitoringLogger monitoringLogger;
    private final MonitoringMetrics monitoringMetrics;

    public MonitoringService(
            MonitoringLogger monitoringLogger,
            MonitoringMetrics monitoringMetrics) {

        this.monitoringLogger = monitoringLogger;
        this.monitoringMetrics = monitoringMetrics;
    }

    public void recordAlertProcessed(String alertId) {

        monitoringLogger.logAlertProcessed(alertId);
        monitoringMetrics.recordAlertProcessed();
    }

    public void recordNotificationSent(
            String alertId,
            String channel) {

        monitoringLogger.logNotificationSent(
                alertId,
                channel
        );

        monitoringMetrics.recordNotificationSent();
    }

    public void recordNotificationSkipped(
            String alertId,
            String reason) {

        monitoringLogger.logNotificationSkipped(
                alertId,
                reason
        );

        monitoringMetrics.recordNotificationSkipped();
    }

    public void recordNotificationFailed(
            String alertId,
            String reason) {

        monitoringLogger.logNotificationFailed(
                alertId,
                reason
        );

        monitoringMetrics.recordNotificationFailed();
    }
}