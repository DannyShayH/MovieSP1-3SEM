package app.config;

import jakarta.persistence.EntityManagerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public final class TestEmf {
    private TestEmf() {}

    public static EntityManagerFactory create(PostgreSQLContainer<?> pg) {
        Properties props = HibernateBaseProperties.createBase();
        props.put("hibernate.connection.url", pg.getJdbcUrl());
        props.put("hibernate.connection.username", pg.getUsername());
        props.put("hibernate.connection.password", pg.getPassword());
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        props.put("hibernate.show_sql", "false");
        return HibernateEmfBuilder.build(props);
    }
}
