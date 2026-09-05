package notification.service;

import java.util.List;

public class NotificationBatch {

    private final String batchId;
    private final String type;
    private final List<String> alertIds;
    private final int alertCount;
    private final String createdAt;

    public NotificationBatch(
            String batchId,
            String type,
            List<String> alertIds,
            int alertCount,
            String createdAt) {

        this.batchId = batchId;
        this.type = type;
        this.alertIds = alertIds;
        this.alertCount = alertCount;
        this.createdAt = createdAt;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getType() {
        return type;
    }

    public List<String> getAlertIds() {
        return alertIds;
    }

    public int getAlertCount() {
        return alertCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
