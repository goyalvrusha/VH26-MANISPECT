package alert_fatigue_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.intelligence.AlertIntelligenceService;
import alert_fatigue_backend.intelligence.NotificationDecision;
import alert_fatigue_backend.monitoring.MonitoringService;
import notification.service.NotificationResult;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertIntelligenceService intelligenceService;
    private final MonitoringService monitoringService;

    public AlertController(
            AlertIntelligenceService intelligenceService,
            MonitoringService monitoringService) {

        this.intelligenceService = intelligenceService;
        this.monitoringService = monitoringService;
    }

    @PostMapping
    public AlertResponse receiveAlert(@RequestBody Alert alert) {

        Alert processedAlert =
                intelligenceService.process(alert);

        monitoringService.recordAlertProcessed(
                processedAlert.getId()
        );

        NotificationDecision decision =
                intelligenceService.decideNotification(processedAlert);

        NotificationResult delivery =
                intelligenceService.sendNotification(
                        processedAlert,
                        decision
                );

        if (!decision.isShouldNotify()) {

            monitoringService.recordNotificationSkipped(
                    processedAlert.getId(),
                    decision.getReason()
            );

        } else if ("SENT".equalsIgnoreCase(delivery.getStatus())) {

            monitoringService.recordNotificationSent(
                    processedAlert.getId(),
                    delivery.getChannel()
            );

        } else {

            monitoringService.recordNotificationFailed(
                    processedAlert.getId(),
                    delivery.getReason()
            );
        }

        return new AlertResponse(
                processedAlert,
                decision,
                delivery
        );
    }

    public record AlertResponse(
            Alert alert,
            NotificationDecision notification,
            NotificationResult delivery
    ) {}
}