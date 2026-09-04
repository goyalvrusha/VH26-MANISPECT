package alert_fatigue_backend.intelligence;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.repository.AlertRepository;
import org.springframework.stereotype.Service;

@Service
public class AlertIntelligenceService {

    private final AlertFingerprintService fingerprintService;
    private final AlertRepository alertRepository;

    public AlertIntelligenceService(
            AlertFingerprintService fingerprintService,
            AlertRepository alertRepository) {

        this.fingerprintService = fingerprintService;
        this.alertRepository = alertRepository;
    }

    public Alert process(Alert alert) {

        String fingerprint =
                fingerprintService.generateFingerprint(alert);

        alert.setFingerprint(fingerprint);

        // Check whether this alert already exists.
        var existingAlert =
                alertRepository.findByFingerprint(fingerprint);

        if (existingAlert.isPresent()) {

            Alert duplicate = existingAlert.get();

            duplicate.setOccurrenceCount(
        duplicate.getOccurrenceCount() + 1
);

duplicate.setLastSeen(alert.getLastSeen());
duplicate.setStatus(alert.getStatus());
duplicate.setMessage(alert.getMessage());

            return alertRepository.save(duplicate);
        }

        return alertRepository.save(alert);
    }

    public NotificationDecision decideNotification(Alert alert) {

        if ("CRITICAL".equalsIgnoreCase(alert.getSeverity())) {
            return new NotificationDecision(
                    true,
                    "severity_requires_notification"
            );
        }

        return new NotificationDecision(
                true,
                "severity_requires_notification"
        );
    }
}