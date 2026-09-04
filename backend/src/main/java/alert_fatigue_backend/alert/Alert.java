package alert_fatigue_backend.alert;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Alert {

    @Id
    private String id;

    private String fingerprint;
    private String source;
    private String alertName;
    private String service;
    private String severity;
    private String priority;
    private String status;
    private String message;
    private int occurrenceCount;
    private boolean flapping;
    private String groupId;
    private String firstSeen;
    private String lastSeen;

    public Alert() {
    }

    public Alert(String id, String fingerprint, String source, String alertName,
                 String service, String severity, String priority, String status,
                 String message, int occurrenceCount, boolean flapping,
                 String groupId, String firstSeen, String lastSeen) {

        this.id = id;
        this.fingerprint = fingerprint;
        this.source = source;
        this.alertName = alertName;
        this.service = service;
        this.severity = severity;
        this.priority = priority;
        this.status = status;
        this.message = message;
        this.occurrenceCount = occurrenceCount;
        this.flapping = flapping;
        this.groupId = groupId;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
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