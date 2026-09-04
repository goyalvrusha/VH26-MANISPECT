package alert_fatigue_backend.controller;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.repository.AlertRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @PostMapping
    public Alert receiveAlert(@RequestBody Alert alert) {
        return alertRepository.save(alert);
    }
    @GetMapping
public List<Alert> getAlerts() {
    return alertRepository.findAll();
}
}