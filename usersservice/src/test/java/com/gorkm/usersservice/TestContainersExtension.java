package com.gorkm.usersservice;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Reusable test container extension.
 * To reuse database container add @ExtendWith(TestContainersExtension.class) annotation to your test class.
 * Keep in mind that since test container is reusable all data inserted into DB during unit tests will persist until all tests ends up.
 */
public class TestContainersExtension implements BeforeAllCallback {

    static final MySQLContainer<?> mySQLContainer;

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:8.0-debian"))
                .withDatabaseName("integration-tests-db")
                .withUsername("testUser")
                .withPassword("testPassword")
                .withInitScript("schema.sql");
    }

    private void updateDataSourceProps(MySQLContainer<?> container) {
        System.setProperty("spring.datasource.driver-class-name", container.getDriverClassName());
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        mySQLContainer.start();
        updateDataSourceProps(mySQLContainer);
    }
}
