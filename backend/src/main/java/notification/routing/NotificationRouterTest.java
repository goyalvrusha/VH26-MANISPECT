package notification.routing;

public class NotificationRouterTest {

    public static void main(String[] args) {

        NotificationRouter router = new NotificationRouter();

        System.out.println("CRITICAL → "
                + router.route(true, "CRITICAL"));

        System.out.println("HIGH → "
                + router.route(true, "HIGH"));

        System.out.println("MEDIUM → "
                + router.route(true, "MEDIUM"));

        System.out.println("LOW → "
                + router.route(true, "LOW"));

        System.out.println("COOLDOWN → "
                + router.route(false, "HIGH"));
    }
}