# rest-assured-base

Proyecto base para automatización de pruebas de API con **REST Assured + TestNG + Allure**, listo para importarse en IntelliJ IDEA como proyecto Maven.

## Stack

- Java 17
- Maven
- REST Assured 5.4.0
- TestNG 7.9.0
- Allure 2.27.0 (reporte + integración automática con REST Assured vía `AllureRestAssured` filter)

## Estructura

```
rest-assured-base/
├── pom.xml
├── src/test/java/com/qa/base/BaseTest.java      <- clase base (config + RequestSpecification)
├── src/test/java/com/qa/tests/SampleApiTest.java <- tests de ejemplo (reqres.in)
├── src/test/resources/config.properties          <- base.url y otras props de entorno
├── src/test/resources/testng.xml                 <- suite de TestNG
└── src/test/resources/allure.properties           <- carpeta de resultados de Allure
```

## Cómo importarlo en IntelliJ

1. Descomprime el `.zip`.
2. En IntelliJ: **File → Open** y selecciona la carpeta `rest-assured-base` (con el `pom.xml` dentro).
3. IntelliJ detectará que es un proyecto Maven y descargará las dependencias automáticamente. Si no lo hace, haz clic derecho sobre `pom.xml` → **Maven → Reload project**.
4. Asegúrate de tener configurado un JDK 17 en **File → Project Structure → SDK**.

## Cómo correr los tests

Desde IntelliJ:
- Clic derecho sobre `testng.xml` → **Run**.
- O clic derecho sobre `SampleApiTest.java` → **Run**.

Desde terminal:

```bash
mvn clean test
```

## Cómo generar el reporte de Allure

```bash
mvn allure:serve
```

Esto levanta un servidor local y abre el reporte en el navegador automáticamente.

## Adaptarlo a tu proyecto real

1. **Cambia la URL base** en `src/test/resources/config.properties` (`base.url=...`).
2. **Reemplaza `SampleApiTest`** por tus propias clases de test bajo `com.qa.tests`, heredando de `BaseTest` para reutilizar el `requestSpec` ya configurado con logging y Allure.
3. Si necesitas **múltiples entornos** (QA, UAT, etc.), puedes duplicar `config.properties` (p. ej. `config-qa.properties`, `config-uat.properties`) y cargar el archivo según un parámetro del sistema (`-Denv=qa`), o usar perfiles de Maven.
4. Si vas a probar endpoints con autenticación (token, Basic Auth, etc.), agrega esa lógica en `BaseTest.setUp()` antes de construir el `RequestSpecBuilder`.

## Notas

- El `argLine` del plugin de Surefire activa el *AspectJ weaver*, necesario para que las anotaciones de Allure (`@Step`, `@Severity`, etc.) y la captura automática de request/response funcionen correctamente.
- Los tests de ejemplo apuntan a la API pública `https://reqres.in`, solo para validar que el proyecto compila y corre correctamente nada más importarlo.
