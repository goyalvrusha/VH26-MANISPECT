package notification.config;

public class NotificationConfig {

    private final String slackWebhookUrl;
    private final String pagerDutyRoutingKey;
    private final String emailRecipient;

    public NotificationConfig() {
        this.slackWebhookUrl = System.getenv("SLACK_WEBHOOK_URL");
        this.pagerDutyRoutingKey = System.getenv("PAGERDUTY_ROUTING_KEY");
        this.emailRecipient = System.getenv("NOTIFICATION_EMAIL");
    }

    public String getSlackWebhookUrl() {
        return slackWebhookUrl;
    }

    public String getPagerDutyRoutingKey() {
        return pagerDutyRoutingKey;
    }

    public String getEmailRecipient() {
        return emailRecipient;
    }
}