package com.studydeck.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JpaUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createFactory();

    private JpaUtil() {
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    private static EntityManagerFactory createFactory() {
        Map<String, String> properties = new HashMap<>();
        properties.put(
                "jakarta.persistence.jdbc.url",
                resolveValue("DB_URL", "db.url", "jdbc:mysql://localhost:3306/study_deck_manager")
        );
        properties.put("jakarta.persistence.jdbc.user", resolveValue("DB_USER", "db.user", "root"));
        properties.put("jakarta.persistence.jdbc.password", resolveValue("DB_PASSWORD", "db.password", ""));
        return Persistence.createEntityManagerFactory("studyDeckPU", properties);
    }

    private static String resolveValue(
            String environmentName,
            String systemPropertyName,
            String defaultValue
    ) {
        String value = System.getProperty(systemPropertyName);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentName);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
