package alert_fatigue_backend.intelligence;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.repository.AlertRepository;
import notification.service.NotificationResult;
import notification.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class AlertIntelligenceService {

    private final AlertFingerprintService fingerprintService;
    private final AlertRepository alertRepository;
    private final NotificationService notificationService;

    public AlertIntelligenceService(
            AlertFingerprintService fingerprintService,
            AlertRepository alertRepository,
            NotificationService notificationService) {

        this.fingerprintService = fingerprintService;
        this.alertRepository = alertRepository;
        this.notificationService = notificationService;
    }

    public Alert process(Alert alert) {

        String fingerprint =
                fingerprintService.generateFingerprint(alert);

        alert.setFingerprint(fingerprint);

        var existingAlert =
                alertRepository.findByFingerprint(fingerprint);

        if (existingAlert.isPresent()) {

            Alert duplicate = existingAlert.get();

            duplicate.setOccurrenceCount(
                    duplicate.getOccurrenceCount() + 1
            );

            duplicate.setLastSeen(alert.getLastSeen());

            return alertRepository.save(duplicate);
        }

        if (alert.getOccurrenceCount() <= 0) {
            alert.setOccurrenceCount(1);
        }

        return alertRepository.save(alert);
    }

    public NotificationDecision decideNotification(Alert alert) {

        if (alert.getOccurrenceCount() > 1) {
            return new NotificationDecision(
                    false,
                    "duplicate_alert"
            );
        }

        return new NotificationDecision(
                true,
                "severity_requires_notification"
        );
    }

    public NotificationResult sendNotification(
            Alert alert,
            NotificationDecision decision) {

        return notificationService.process(
                alert.getId(),
                decision.isShouldNotify(),
                alert.getPriority(),
                alert.getMessage(),
                decision.getReason()
        );
    }
}