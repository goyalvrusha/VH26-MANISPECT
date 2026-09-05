package notification.client;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class NotificationHttpClient {

    private final RestClient restClient;

    public NotificationHttpClient() {
        this.restClient = RestClient.builder().build();
    }

    public boolean post(
            String url,
            Map<String, Object> payload) {

        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}