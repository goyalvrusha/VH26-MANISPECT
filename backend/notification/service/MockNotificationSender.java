package notification.service;

import notification.routing.NotificationChannel;

public class MockNotificationSender {

    public NotificationResult send(
            String alertId,
            NotificationChannel channel,
            String message) {

        if (channel == NotificationChannel.NONE) {
            return new NotificationResult(
                    alertId,
                    "NONE",
                    "SKIPPED",
                    "Notification skipped"
            );
        }

        System.out.println(
                "MOCK SEND → " + channel + " | " + message
        );

        return new NotificationResult(
                alertId,
                channel.name(),
                "SENT",
                message
        );
    }
}  