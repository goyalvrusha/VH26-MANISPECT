package notification.service;

import java.util.Arrays;

public class NotificationBatchTest {

    public static void main(String[] args) {

        NotificationBatch batch = new NotificationBatch(
                "BATCH-001",
                "DIGEST",
                Arrays.asList("ALT-001", "ALT-002"),
                2,
                "2026-09-04T10:05:00"
        );

        System.out.println("Batch ID: " + batch.getBatchId());
        System.out.println("Type: " + batch.getType());
        System.out.println("Alerts: " + batch.getAlertIds());
        System.out.println("Alert Count: " + batch.getAlertCount());
        System.out.println("Created At: " + batch.getCreatedAt());
    }
}