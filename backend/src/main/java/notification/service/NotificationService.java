package notification.service;

import java.util.List;

import notification.routing.NotificationChannel;
import notification.routing.NotificationRouter;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final int MAX_RETRIES = 2;

    private final NotificationRouter router;
    private final SlackNotificationSender slackSender;
    private final PagerDutyNotificationSender pagerDutySender;
    private final EmailNotificationSender emailSender;
    private final NotificationFormatter formatter;
    private final NotificationQueue notificationQueue;

    // Used by existing tests and manual Java usage.
    public NotificationService() {
        this(new NotificationQueue());
    }

    // Used by Spring so the shared queue is injected.
    public NotificationService(NotificationQueue notificationQueue) {
        this.router = new NotificationRouter();
        this.slackSender = new SlackNotificationSender();
        this.pagerDutySender = new PagerDutyNotificationSender();
        this.emailSender = new EmailNotificationSender();
        this.formatter = new NotificationFormatter();
        this.notificationQueue = notificationQueue;
    }

    public NotificationResult process(ProcessedAlert alert) {

        if (!alert.isShouldNotify()) {
            return new NotificationResult(
                    alert.getId(),
                    "NONE",
                    "SKIPPED",
                    "Notification skipped",
                    alert.getReason()
            );
        }

        String formattedMessage = formatter.format(
                alert.getId(),
                alert.getService(),
                alert.getAlertName(),
                alert.getPriority(),
                alert.getSeverity(),
                alert.getMessage()
        );

        List<NotificationChannel> channels =
                router.getFallbackChannels(
                        true,
                        alert.getPriority()
                );

        if (channels.isEmpty()) {
            return new NotificationResult(
                    alert.getId(),
                    "NONE",
                    "SKIPPED",
                    "No notification channel available",
                    alert.getReason()
            );
        }

        for (NotificationChannel channel : channels) {

            for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

                NotificationResult result = send(
                        channel,
                        alert.getId(),
                        formattedMessage,
                        alert.getReason()
                );

                if ("SENT".equalsIgnoreCase(result.getStatus())) {
                    return result;
                }
            }
        }

        notificationQueue.enqueue(
                alert.getId(),
                formattedMessage,
                alert.getPriority(),
                alert.getReason()
        );

        return new NotificationResult(
                alert.getId(),
                "QUEUE",
                "FAILED",
                "All notification channels failed. Alert queued for retry.",
                alert.getReason()
        );
    }

    public NotificationResult retryQueuedNotification(
            NotificationQueue.QueuedNotification queued) {

        List<NotificationChannel> channels =
                router.getFallbackChannels(
                        true,
                        queued.getPriority()
                );

        for (NotificationChannel channel : channels) {

            for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

                NotificationResult result = send(
                        channel,
                        queued.getAlertId(),
                        queued.getMessage(),
                        queued.getReason()
                );

                if ("SENT".equalsIgnoreCase(result.getStatus())) {
                    return result;
                }
            }
        }

        notificationQueue.enqueue(
                queued.getAlertId(),
                queued.getMessage(),
                queued.getPriority(),
                queued.getReason()
        );

        return new NotificationResult(
                queued.getAlertId(),
                "QUEUE",
                "FAILED",
                "Queued notification retry failed. Kept in queue.",
                queued.getReason()
        );
    }

    private NotificationResult send(
            NotificationChannel channel,
            String alertId,
            String message,
            String reason) {

        switch (channel) {

            case PAGERDUTY:
                return pagerDutySender.send(
                        alertId,
                        message,
                        reason
                );

            case SLACK:
                return slackSender.send(
                        alertId,
                        message,
                        reason
                );

            case EMAIL:
                return emailSender.send(
                        alertId,
                        message,
                        reason
                );

            case NONE:
            default:
                return new NotificationResult(
                        alertId,
                        "NONE",
                        "FAILED",
                        "Invalid notification channel",
                        reason
                );
        }
    }

    public int getQueuedNotificationCount() {
        return notificationQueue.size();
    }
}
