package notification.service;

public class NotificationServiceTest {

    public static void main(String[] args) {

        NotificationService service = new NotificationService();

        ProcessedAlert critical = new ProcessedAlert(
                "ALT-001",
                "payment-service",
                "HighCPU",
                "CRITICAL",
                "CRITICAL",
                "CPU usage above 95%",
                true,
                "NEW_ALERT"
        );

        ProcessedAlert high = new ProcessedAlert(
                "ALT-002",
                "payment-service",
                "HighCPU",
                "HIGH",
                "WARNING",
                "CPU usage above 90%",
                true,
                "NEW_ALERT"
        );

        ProcessedAlert medium = new ProcessedAlert(
                "ALT-003",
                "payment-service",
                "HighCPU",
                "MEDIUM",
                "WARNING",
                "CPU usage above 80%",
                true,
                "NEW_ALERT"
        );

        ProcessedAlert low = new ProcessedAlert(
                "ALT-004",
                "payment-service",
                "HighCPU",
                "LOW",
                "INFO",
                "CPU usage returned to normal range",
                true,
                "NEW_ALERT"
        );

        ProcessedAlert suppressed = new ProcessedAlert(
                "ALT-005",
                "payment-service",
                "HighCPU",
                "CRITICAL",
                "CRITICAL",
                "Repeated alert during cooldown",
                false,
                "COOLDOWN"
        );

        test(service, critical);
        test(service, high);
        test(service, medium);
        test(service, low);
        test(service, suppressed);
    }

    private static void test(
            NotificationService service,
            ProcessedAlert alert) {

        NotificationResult result = service.process(alert);

        System.out.println(
                alert.getId()
                        + " | Priority: " + alert.getPriority()
                        + " | ShouldNotify: " + alert.isShouldNotify()
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