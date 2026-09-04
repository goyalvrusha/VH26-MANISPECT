package alert_fatigue_backend.controller;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.intelligence.AlertIntelligenceService;
import alert_fatigue_backend.intelligence.NotificationDecision;
import notification.service.NotificationResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertIntelligenceService intelligenceService;

    public AlertController(AlertIntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping
    public AlertResponse receiveAlert(@RequestBody Alert alert) {

        Alert processedAlert = intelligenceService.process(alert);

        NotificationDecision decision =
                intelligenceService.decideNotification(processedAlert);

        NotificationResult delivery =
                intelligenceService.sendNotification(
                        processedAlert,
                        decision
                );

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