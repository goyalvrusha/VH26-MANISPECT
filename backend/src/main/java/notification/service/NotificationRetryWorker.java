package notification.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationRetryWorker {

    private final NotificationQueue notificationQueue;
    private final NotificationService notificationService;

    public NotificationRetryWorker(
            NotificationQueue notificationQueue,
            NotificationService notificationService) {

        this.notificationQueue = notificationQueue;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 30000)
    public void retryQueuedNotifications() {

        while (!notificationQueue.isEmpty()) {

            NotificationQueue.QueuedNotification queued =
                    notificationQueue.poll();

            if (queued == null) {
                break;
            }

            System.out.println(
                    "Retrying queued notification: "
                            + queued.getAlertId()
            );

            NotificationResult result =
                    notificationService.retryQueuedNotification(queued);

            System.out.println(
                    "Queue retry result: "
                            + result.getStatus()
                            + " via "
                            + result.getChannel()
            );
        }
    }
}
