package notification.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

@Component
public class NotificationQueue {

    private final Queue<QueuedNotification> queue =
            new ConcurrentLinkedQueue<>();

    public void enqueue(
            String alertId,
            String message,
            String priority,
            String reason) {

        queue.offer(
                new QueuedNotification(
                        alertId,
                        message,
                        priority,
                        reason
                )
        );
    }

    public QueuedNotification poll() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static class QueuedNotification {

        private final String alertId;
        private final String message;
        private final String priority;
        private final String reason;

        public QueuedNotification(
                String alertId,
                String message,
                String priority,
                String reason) {

            this.alertId = alertId;
            this.message = message;
            this.priority = priority;
            this.reason = reason;
        }

        public String getAlertId() {
            return alertId;
        }

        public String getMessage() {
            return message;
        }

        public String getPriority() {
            return priority;
        }

        public String getReason() {
            return reason;
        }
    }
}
