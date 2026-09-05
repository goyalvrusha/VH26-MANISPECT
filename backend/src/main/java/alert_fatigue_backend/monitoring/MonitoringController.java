package alert_fatigue_backend.monitoring;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringMetrics monitoringMetrics;
    private final MonitoringService monitoringService;

    public MonitoringController(
            MonitoringMetrics monitoringMetrics,
            MonitoringService monitoringService) {

        this.monitoringMetrics = monitoringMetrics;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {

        double total = monitoringMetrics.getAlertsProcessed();
        double skipped = monitoringMetrics.getNotificationsSkipped();

        double suppressionRate =
                total > 0 ? (skipped / total) * 100 : 0;

        return Map.of(
                "service", "alert-fatigue-backend",
                "monitoring", "active",

                "alertsProcessed", total,
                "uniqueAlerts", monitoringService.getUniqueAlerts(),
                "duplicateAlerts", monitoringService.getDuplicateAlerts(),

                "notificationsSent",
                monitoringMetrics.getNotificationsSent(),

                "notificationsSkipped",
                skipped,

                "notificationsFailed",
                monitoringMetrics.getNotificationsFailed(),

                "suppressionRate",
                suppressionRate,

                "recentEvents",
                monitoringService.getRecentEvents()
        );
    }
}