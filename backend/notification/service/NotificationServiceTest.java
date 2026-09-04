package notification.service;

public class NotificationServiceTest {

    public static void main(String[] args) {

        NotificationService service = new NotificationService();
        NotificationFormatter formatter = new NotificationFormatter();

        testAlert(
                service, formatter,
                "ALT-001", "payment-service", "HighCPU",
                "CRITICAL", "CRITICAL",
                "CPU usage above 95%"
        );

        testAlert(
                service, formatter,
                "ALT-002", "payment-service", "HighCPU",
                "HIGH", "WARNING",
                "CPU usage above 90%"
        );

        testAlert(
                service, formatter,
                "ALT-003", "payment-service", "HighCPU",
                "MEDIUM", "WARNING",
                "CPU usage above 80%"
        );

        testAlert(
                service, formatter,
                "ALT-004", "payment-service", "HighCPU",
                "LOW", "INFO",
                "CPU usage returned to normal range"
        );

        testAlert(
                service, formatter,
                "ALT-005", "payment-service", "HighCPU",
                "CRITICAL", "CRITICAL",
                "CPU usage above 95%",
                false
        );
    }

    private static void testAlert(
            NotificationService service,
            NotificationFormatter formatter,
            String id,
            String serviceName,
            String alertName,
            String priority,
            String severity,
            String message) {

        testAlert(
                service,
                formatter,
                id,
                serviceName,
                alertName,
                priority,
                severity,
                message,
                true
        );
    }

    private static void testAlert(
            NotificationService service,
            NotificationFormatter formatter,
            String id,
            String serviceName,
            String alertName,
            String priority,
            String severity,
            String message,
            boolean shouldNotify) {

        String formattedMessage = formatter.format(
                id,
                serviceName,
                alertName,
                priority,
                severity,
                message
        );

        String reason = shouldNotify
                ? "NEW_ALERT"
                : "COOLDOWN";

        NotificationResult result = service.process(
                id,
                shouldNotify,
                priority,
                formattedMessage,
                reason
        );

        System.out.println(
                id
                + " | Priority: " + priority
                + " | ShouldNotify: " + shouldNotify
                + " | Channel: " + result.getChannel()
                + " | Status: " + result.getStatus()
                + " | Reason: " + result.getReason()
        );

        System.out.println(
                "Message: " + result.getMessage()
        );

        System.out.println();
    }
}