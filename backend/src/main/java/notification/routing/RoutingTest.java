package notification.routing;

public class RoutingTest {

    public static void main(String[] args) {

        NotificationRouter router = new NotificationRouter();

        test(router, true, "CRITICAL");
        test(router, true, "HIGH");
        test(router, true, "MEDIUM");
        test(router, true, "LOW");
        test(router, false, "CRITICAL");
    }

    private static void test(
            NotificationRouter router,
            boolean shouldNotify,
            String priority) {

        NotificationChannel channel =
                router.route(shouldNotify, priority);

        System.out.println(
                "shouldNotify=" + shouldNotify
                + " | priority=" + priority
                + " → " + channel
        );
    }
}