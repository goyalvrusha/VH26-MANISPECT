package alert_fatigue_backend.controller;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.intelligence.AlertIntelligenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertIntelligenceService intelligenceService;

    public AlertController(AlertIntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping
    public Alert receiveAlert(@RequestBody Alert alert) {
        return intelligenceService.process(alert);
    }
}