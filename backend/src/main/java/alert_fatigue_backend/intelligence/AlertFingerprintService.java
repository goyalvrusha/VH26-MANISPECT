package alert_fatigue_backend.intelligence;

import alert_fatigue_backend.alert.Alert;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AlertFingerprintService {

    public String generateFingerprint(Alert alert) {

        if (alert.getFingerprint() != null &&
                !alert.getFingerprint().isBlank()) {
            return alert.getFingerprint();
        }

        String rawData =
                normalize(alert.getSource()) + "|" +
                normalize(alert.getService()) + "|" +
                normalize(alert.getAlertName());

        return sha256(rawData);
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().toLowerCase();
    }

    private String sha256(String input) {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm not available", e);
        }
    }
}