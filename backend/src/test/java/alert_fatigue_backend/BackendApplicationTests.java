package alert_fatigue_backend;

import alert_fatigue_backend.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
        properties = {
                "spring.autoconfigure.exclude=" +
                "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
        }
)
class BackendApplicationTests {

    @MockitoBean
    AlertRepository alertRepository;

    @Test
    void contextLoads() {
    }
}