package org.lightsout.problem.resource;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lightsout.problem.dto.CreateProblemRequest;
import org.lightsout.shared.config.LightsOutTestResource;
import org.lightsout.shared.util.TestUtils.UnsolvableGrids;

import java.util.UUID;

import static io.netty.handler.codec.http.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.lightsout.shared.util.TestUtils.SolvableGrids;
import static org.lightsout.shared.util.TestUtils.toList;

@QuarkusTest
@DBRider
@DataSet(
        value = {"database.yml"},
        skipCleaningFor = {"flyway_schema_history"},
        cleanAfter = true,
        cleanBefore = true)
@QuarkusTestResource(LightsOutTestResource.class)
class ProblemResourceTest {

    @Test
    @DisplayName("Should return available problems based on the pagination")
    void getProblems() {
        int pageSize = 3;
        given()
                .queryParam("page", 0)
                .queryParam("size", pageSize)
                .when().get("/problems")
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
                .when().get("/problems/{id}", problemId)
                .then()
                .statusCode(OK.getStatusCode())
                .body("id", equalTo(problemId))
                .body("grid", hasSize(7));
    }

    @Test
    @DisplayName("Should return 404 for invalid problem ID")
    void getProblemById_notFound() {
        String problemId = UUID.randomUUID().toString();
        given()
                .when().get("/problems/{id}", problemId)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("Should create a new problem")
    void createProblem() {
        CreateProblemRequest request = new CreateProblemRequest(toList(SolvableGrids.GRID_3_X_3));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when().post("/problems")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    @DisplayName("Should return 400 because of grid size")
    void createProblem_badRequest() {
        CreateProblemRequest request = new CreateProblemRequest(toList(new byte[][]{{1, 2}, {1, 2, 3}, {1}}));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when().post("/problems")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    @DisplayName("Should return 422 for unsolvable problem")
    void createProblem_unsolvableProblem() {
        CreateProblemRequest request = new CreateProblemRequest(toList(UnsolvableGrids.GRID_4_X_4));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when().post("/problems")
                .then()
                .statusCode(UNPROCESSABLE_ENTITY.code());
    }
}
