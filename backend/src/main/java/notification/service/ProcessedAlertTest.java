package notification.service;

public class ProcessedAlertTest {

    public static void main(String[] args) {

        ProcessedAlert alert = new ProcessedAlert(
                "ALT-001",
                "payment-service",
                "HighCPU",
                "HIGH",
                "WARNING",
                "CPU usage above 90%",
                true,
                "NEW_ALERT"
        );

        NotificationService service = new NotificationService();

        NotificationResult result = service.process(alert);

        System.out.println("Alert ID: " + result.getAlertId());
        System.out.println("Channel: " + result.getChannel());
        System.out.println("Status: " + result.getStatus());
        System.out.println("Reason: " + result.getReason());
        System.out.println("Message: " + result.getMessage());
    }
}