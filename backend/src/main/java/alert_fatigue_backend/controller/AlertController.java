package alert_fatigue_backend.controller;

import alert_fatigue_backend.alert.Alert;
import alert_fatigue_backend.intelligence.AlertIntelligenceService;
import alert_fatigue_backend.intelligence.NotificationDecision;
import notification.service.NotificationResult;
import notification.service.NotificationService;
import notification.service.ProcessedAlert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertIntelligenceService intelligenceService;
    private final NotificationService notificationService;

    public AlertController(
            AlertIntelligenceService intelligenceService,
            NotificationService notificationService) {

        this.intelligenceService = intelligenceService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public NotificationResult receiveAlert(
            @RequestBody AlertRequest request) {

        Alert alert = new Alert();

        alert.setId(request.getId());
        alert.setSource(request.getSource());
        alert.setAlertName(request.getAlertName());
        alert.setService(request.getService());
        alert.setSeverity(request.getSeverity());
        alert.setPriority(request.getPriority());
        alert.setStatus(request.getStatus());
        alert.setMessage(request.getMessage());
        alert.setOccurrenceCount(request.getOccurrenceCount());
        alert.setFlapping(request.isFlapping());
        alert.setGroupId(request.getGroupId());
        alert.setFirstSeen(request.getFirstSeen());
        alert.setLastSeen(request.getLastSeen());

        // Person 1: fingerprint + deduplication
        Alert processedAlert =
                intelligenceService.process(alert);

        // Person 1: notification decision
        NotificationDecision decision =
                intelligenceService.decideNotification(processedAlert);

        // Person 1 ? Person 2
        ProcessedAlert notificationAlert =
                new ProcessedAlert(
                        processedAlert.getId(),
                        processedAlert.getService(),
                        processedAlert.getAlertName(),
                        processedAlert.getPriority(),
                        processedAlert.getSeverity(),
                        processedAlert.getMessage(),
                        decision.isShouldNotify(),
                        decision.getReason()
                );

        // Person 2: routing + notification delivery
        return notificationService.process(notificationAlert);
    }

    public static class AlertRequest {

        private String id;
        private String source;
        private String alertName;
        private String service;
        private String severity;
        private String priority;
        private String status;
        private String message;

        private int occurrenceCount = 1;
        private boolean flapping = false;

        private String groupId;
        private String firstSeen;
        private String lastSeen;

        public AlertRequest() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAlertName() {
            return alertName;
        }

        public void setAlertName(String alertName) {
            this.alertName = alertName;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getOccurrenceCount() {
            return occurrenceCount;
        }

        public void setOccurrenceCount(int occurrenceCount) {
            this.occurrenceCount = occurrenceCount;
        }

        public boolean isFlapping() {
            return flapping;
        }

        public void setFlapping(boolean flapping) {
            this.flapping = flapping;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getFirstSeen() {
            return firstSeen;
        }

        public void setFirstSeen(String firstSeen) {
            this.firstSeen = firstSeen;
        }

        public String getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(String lastSeen) {
            this.lastSeen = lastSeen;
        }
    }
}
