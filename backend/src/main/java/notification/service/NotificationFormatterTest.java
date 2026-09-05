package notification.service;

public class NotificationFormatterTest {

    public static void main(String[] args) {

        NotificationFormatter formatter = new NotificationFormatter();

        String result = formatter.format(
                "ALT-001",
                "payment-service",
                "HighCPU",
                "HIGH",
                "WARNING",
                "CPU usage above 90%"
        );

        System.out.println(result);
    }
}