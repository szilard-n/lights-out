# Lights Out Fullstack Challenge

## Project Overview

The purpose of this challenge was to create a backend service and a user interface that allow users to build and solve 'Lights Out' problems.

## Challenge Requirements

- **Java**: Java 11 or above is required.
- **Maven**: Used for managing libraries and dependencies.
- **Quarkus**: Application server used for the backend.
- **Angular or React**: Used for creating the graphical user interface (GUI).

### Backend Requirements

- **REST APIs**:
    - `GET /problems`: Returns all problems.
    - `GET /problems/{id}`: Returns a specific problem by ID.
    - `POST /problems`: Creates a new problem.
    - `GET /solutions`: Returns all optimal solutions.
    - `GET /solutions/problem/{id}`: Returns all optimal solutions for a specific problem.

- **Problem Solving**:
    - The backend must be able to solve the given problems, saving only those that are solvable.
    - If a problem is solvable, it and its solution are saved in the database.
    - If a problem is not solvable, it is not saved, and the user is informed.
    - Logs must show the solver's performance and the number of moves required to reach a solution.

### Database Requirements

- The database should consist of at least three tables: `problem`, `solution`, and `solution_step`.
- The `problem` table should describe the problem.
- The `solution` table should link to the `problem` table.
- The `solution_step` table should include steps to reach the solution and link to the `solution` table.
- Additional tables and fields can be added as needed.

### Solver Requirements

- The solver must determine a solution within a reasonable time or report if no solution exists.
- It should handle fields sized between 3x3 and 8x8.
- The solver must ensure the solution is correct and ends for all possible cases.

### GUI Requirements

- The interface consists of two main parts/pages:
    1. **Problem Listing and Interaction**:
        - Displays all problems from the database, sorted by size.
        - Allows users to select and interactively solve problems.
        - Includes a "Get solution" button to show the solution steps.
        - Provides a reset option to revert to the initial state.
    2. **Problem Creation**:
        - Enables users to create new problems by selecting fields.
        - Sends the problem to the backend for evaluation.
        - Informs the user of the problem's status (solvable or not).
        - Includes a reset option to clear the fields.

## Technology Stack

- **Quarkus**: Framework used for building the backend service.
- **Java 21**: Programming language used for the backend.
- **Docker and Docker Compose**: Used for containerization and running the api.
- **Angular**: Framework used for building the frontend user interface.
- **RestAssured**: Library used for testing the backend APIs.
- **DBRider**: Used for database data injection for testing.
- **Testcontainers**: Used for creating necessary containers for the tests.
- **PostgreSQL**: Database used for storing problems and solutions.
- **Flyway**: Used for database version control.
- **Swagger**: Used for API documentation.

## Installation

1. Clone the project from GitHub.
2. Navigate to the project directory by running `cd lights-out`.
3. The application can be run by running the `./run.sh` script. Docker and NPM must be installed.
4. The UI can then be reached at `http://localhost:4200`. The Swagger UI is also accessible at `http://localhost:8080/q/swagger-ui/`