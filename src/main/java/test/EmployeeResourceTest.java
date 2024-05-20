package test;

import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class EmployeeResourceTest {

    @test
    public void testAddEmployeeEndpoint() {
        String requestBody = "{"
            + "\"gender_id\": 1,"
            + "\"job_id\": 1,"
            + "\"name\": \"Juan\","
            + "\"last_name\": \"Perez\","
            + "\"birthdate\": \"1983-01-01\""
            + "}";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .when()
            .post("/employees")
            .then()
            .statusCode(200)
            .body("success", is(true));
    }
}
