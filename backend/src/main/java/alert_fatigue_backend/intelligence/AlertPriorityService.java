package alert_fatigue_backend.intelligence;

import alert_fatigue_backend.alert.Alert;
import org.springframework.stereotype.Service;

@Service
public class AlertPriorityService {

    public Alert calculatePriority(Alert alert) {

        int score = 0;

        // Severity score
        switch (alert.getSeverity() == null
                ? ""
                : alert.getSeverity().toUpperCase()) {

            case "CRITICAL":
                score += 40;
                break;

            case "HIGH":
                score += 30;
                break;

            case "MEDIUM":
                score += 20;
                break;

            case "LOW":
                score += 10;
                break;
        }

        // Business impact score
        String service = alert.getService() == null
                ? ""
                : alert.getService().toLowerCase();

        if (service.contains("payment")) {
            score += 40;
        } else if (service.contains("auth")
                || service.contains("order")) {
            score += 25;
        } else {
            score += 10;
        }

        // Convert score into priority
        if (score >= 70) {
            alert.setPriority("CRITICAL");
        } else if (score >= 50) {
            alert.setPriority("HIGH");
        } else if (score >= 30) {
            alert.setPriority("MEDIUM");
        } else {
            alert.setPriority("LOW");
        }

        return alert;
    }
}