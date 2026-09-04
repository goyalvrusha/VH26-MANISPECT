package notification.service;

import notification.routing.NotificationChannel;
import notification.routing.NotificationRouter;

public class NotificationService {

    private final NotificationRouter router;
    private final SlackNotificationSender slackSender;
    private final PagerDutyNotificationSender pagerDutySender;
    private final EmailNotificationSender emailSender;

    public NotificationService() {
        this.router = new NotificationRouter();
        this.slackSender = new SlackNotificationSender();
        this.pagerDutySender = new PagerDutyNotificationSender();
        this.emailSender = new EmailNotificationSender();
    }

    public NotificationResult process(
            String alertId,
            boolean shouldNotify,
            String priority,
            String message,
            String reason) {

        NotificationChannel channel =
                router.route(shouldNotify, priority);

        switch (channel) {

            case SLACK:
                return slackSender.send(alertId, message, reason);

            case PAGERDUTY:
                return pagerDutySender.send(alertId, message, reason);

            case EMAIL:
                return emailSender.send(alertId, message, reason);

            case NONE:
            default:
                return new NotificationResult(
                        alertId,
                        "NONE",
                        "SKIPPED",
                        "Notification skipped",
                        reason
                );
        }
    }
}