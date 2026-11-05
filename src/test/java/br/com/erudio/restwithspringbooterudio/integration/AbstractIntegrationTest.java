package br.com.erudio.restwithspringbooterudio.integration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ActiveProfiles("test")
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource(
                    "testcontainers",
                    createConnectionConfiguration()
            );
            environment.getPropertySources().addFirst(testcontainers);
        }

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private static Map<String, Object> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }
    }
}

/*
 * ==============================================================================
 * MODERN VERSION Spring Boot 3.1 +
 * ==============================================================================
 *
 * package br.com.erudio.restwithspringbooterudio.modules;
 *
 * import org.springframework.boot.test.context.SpringBootTest;
 * import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
 * import org.testcontainers.containers.MySQLContainer;
 * import org.testcontainers.junit.jupiter.Container;
 * import org.testcontainers.junit.jupiter.Testcontainers;
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * @Testcontainers
 * public abstract class AbstractIntegrationTest {
 *
 *     @Container
 *     @ServiceConnection
 *     protected static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");
 * }
 */
