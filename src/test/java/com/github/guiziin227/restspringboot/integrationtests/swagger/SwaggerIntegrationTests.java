package com.github.guiziin227.restspringboot.integrationtests.swagger;

import com.github.guiziin227.restspringboot.config.TestConfigs;
import com.github.guiziin227.restspringboot.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTests extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUIPage() {
       var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
             .when()
                .get()
             .then()
                .statusCode(200)
             .extract()
                .body()
                    .asString();

       assertTrue(content.contains("Swagger UI"));
    }
}
