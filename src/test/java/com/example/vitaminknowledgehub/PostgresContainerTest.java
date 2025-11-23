package com.example.vitaminknowledgehub;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class PostgresContainerTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17.1");

    @Test
    void testDatabaseIsRunning() throws Exception {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE demo (id SERIAL PRIMARY KEY, value TEXT)");
        statement.execute("INSERT INTO demo(value) VALUES ('hello-test')");

        ResultSet result = statement.executeQuery("SELECT value FROM demo");

        result.next();
        String fetched = result.getString("value");
        System.out.println(fetched);
        assertEquals("hello-test", fetched);

        connection.close();
    }
}
