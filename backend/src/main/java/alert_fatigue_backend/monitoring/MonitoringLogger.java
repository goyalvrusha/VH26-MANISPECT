package alert_fatigue_backend.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MonitoringLogger {

    private static final Logger logger =
            LoggerFactory.getLogger(MonitoringLogger.class);

    public void logAlertProcessed(String alertId) {
        logger.info("Alert processed: alertId={}", alertId);
    }

    public void logNotificationSent(String alertId, String channel) {
        logger.info(
                "Notification sent: alertId={}, channel={}",
                alertId,
                channel
        );
    }

    public void logNotificationSkipped(String alertId, String reason) {
        logger.info(
                "Notification skipped: alertId={}, reason={}",
                alertId,
                reason
        );
    }

    public void logNotificationFailed(String alertId, String reason) {
        logger.error(
                "Notification failed: alertId={}, reason={}",
                alertId,
                reason
        );
    }
}