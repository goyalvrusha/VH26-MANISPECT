package notification.routing;

import java.util.ArrayList;
import java.util.List;

public class NotificationRouter {

    public NotificationChannel route(
            boolean shouldNotify,
            String priority) {

        List<NotificationChannel> channels =
                getFallbackChannels(shouldNotify, priority);

        if (channels.isEmpty()) {
            return NotificationChannel.NONE;
        }

        return channels.get(0);
    }

    public List<NotificationChannel> getFallbackChannels(
            boolean shouldNotify,
            String priority) {

        List<NotificationChannel> channels =
                new ArrayList<>();

        if (!shouldNotify || priority == null) {
            return channels;
        }

        switch (priority.toUpperCase()) {

            case "CRITICAL":
                channels.add(NotificationChannel.PAGERDUTY);
                channels.add(NotificationChannel.SLACK);
                channels.add(NotificationChannel.EMAIL);
                break;

            case "HIGH":
            case "MEDIUM":
                channels.add(NotificationChannel.SLACK);
                channels.add(NotificationChannel.EMAIL);
                break;

            case "LOW":
                channels.add(NotificationChannel.EMAIL);
                break;

            default:
                break;
        }

        return channels;
    }
}
