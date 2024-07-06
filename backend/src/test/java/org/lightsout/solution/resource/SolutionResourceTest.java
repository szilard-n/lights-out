package org.lightsout.solution.resource;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lightsout.shared.config.LightsOutTestResource;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@DBRider
@DataSet(value = "database.yml")
@QuarkusTestResource(LightsOutTestResource.class)
class SolutionResourceTest {

    @Test
    @DisplayName("Should return available solutions based on the pagination")
    void getProblems() {
        int pageSize = 3;
        given()
                .queryParam("page", 0)
                .queryParam("size", pageSize)
                .when().get("/solutions")
                .then()
                .statusCode(OK.getStatusCode())
                .body("totalItems", equalTo(4))
                .body("items", hasSize(pageSize));
    }

    @Test
    @DisplayName("Should find problem by ID")
    void getProblemsById() {
        String problemId = "92d002ad-87b1-4241-b260-7d2d8c517869";
        given()
                .when().get("/solutions/problem/{id}", problemId)
                .then()
                .statusCode(OK.getStatusCode())
                .body("solutionSteps", hasSize(31));
    }
}
