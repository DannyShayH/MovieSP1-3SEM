package app.dao;

import app.config.TestEmf;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class DaoTestBase {

    @Container
    protected static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    protected EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        emf = TestEmf.create(POSTGRES);
    }

    @AfterEach
    void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }
}
