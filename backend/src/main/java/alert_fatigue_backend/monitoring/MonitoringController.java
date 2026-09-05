package alert_fatigue_backend.monitoring;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringMetrics monitoringMetrics;

    public MonitoringController(MonitoringMetrics monitoringMetrics) {
        this.monitoringMetrics = monitoringMetrics;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {

        return Map.of(
                "service", "alert-fatigue-backend",
                "monitoring", "active",
                "alertsProcessed", monitoringMetrics.getAlertsProcessed(),
                "notificationsSent", monitoringMetrics.getNotificationsSent(),
                "notificationsSkipped", monitoringMetrics.getNotificationsSkipped(),
                "notificationsFailed", monitoringMetrics.getNotificationsFailed()
        );
    }
}