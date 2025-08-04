package com.crudapp.config;
import org.flywaydb.core.Flyway;

public class MigrationRunner {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/crud_app", "root", "password")
                .load();

        flyway.migrate();
    }
}
