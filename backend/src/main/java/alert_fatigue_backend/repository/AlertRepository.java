package alert_fatigue_backend.repository;

import alert_fatigue_backend.alert.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, String> {

    Optional<Alert> findByFingerprint(String fingerprint);
}