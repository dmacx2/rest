package com.qa.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase base de la que heredan todas las clases de test.
 * Carga la configuración (base.url, etc.) desde config.properties
 * y construye una RequestSpecification reutilizable con logging.
 */
public class BaseTest {

    protected RequestSpecification requestSpec;
    protected static final Properties config = new Properties();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws IOException {
        loadConfig();

        RestAssured.baseURI = config.getProperty("base.url");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    private void loadConfig() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IOException("No se encontró config.properties en src/test/resources");
            }
            config.load(is);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Espacio para limpieza posterior a la clase
    }
}
