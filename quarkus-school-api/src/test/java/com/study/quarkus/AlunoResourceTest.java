package com.study.quarkus;

import com.study.quarkus.dto.AlunoRequest;
import com.study.quarkus.dto.AlunoResponse;
import com.study.quarkus.mapper.AlunoMapper;
import com.study.quarkus.model.Aluno;
import com.study.quarkus.repository.AlunoRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class AlunoResourceTest {

    private static final String NAME = "some name";

    @Inject
    AlunoRepository repository;

    @Inject
    AlunoMapper mapper;


    @BeforeEach
    @Transactional
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("List alunos")
    void list() {
        //GIVEN
        var entityOne = Aluno.builder().name("Jose").build();
        var entityTwo = Aluno.builder().name("Maria").build();

        saveToAlunoRepository(entityOne, entityTwo);

        //WHEN
        var response =
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .auth().basic("aluno", "alunopassword")
                        .get("/alunos")
                .then()
                        .extract()
                        .response();

        var dto = response.getBody().as(AlunoResponse[].class);

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(repository.count()).isEqualTo(2);

        assertThat(dto.length).isEqualTo(2);

        assertThat(mapper.toResponse(repository.findById(dto[0].getId())))
                .isEqualTo(dto[0]);

        assertThat(mapper.toResponse(repository.findById(dto[1].getId())))
                .isEqualTo(dto[1]);
    }

    @Transactional
    void saveToAlunoRepository(Aluno ... entities) {
        for(Aluno entity: entities) {
            repository.persist(entity);
        }
    }


    @Test
    @DisplayName("Should create a new aluno")
    void save() {
        //GIVEN
        //WHEN
        var request = AlunoRequest.builder()
                .name(NAME)
                .build();

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                .when()
                        .auth().basic("aluno", "alunopassword")
                        .post("/alunos")
                .then()
                        .extract()
                        .response();

        var dto = response.getBody().as(AlunoResponse.class);

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.CREATED.getStatusCode());

        assertThat(repository.count()).isEqualTo(1);

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Should not create a new aluno")
    void saveInvalidRequest() {
        //GIVEN
        //WHEN
        var request = AlunoRequest.builder()
                .name(" ")
                .build();

        var response =
                    given()
                        .contentType(ContentType.JSON)
                        .body(request)
                    .when()
                        .auth().basic("aluno", "alunopassword")
                        .post("/alunos")
                    .then()
                        .extract()
                        .response();

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Get aluno by id")
    void getById() {
        //GIVEN
        var entityOne = Aluno.builder().name("Jose").build();
        var entityTwo = Aluno.builder().name("Maria").build();

        saveToAlunoRepository(entityOne, entityTwo);

        //WHEN
        var response =
                given()
                .when()
                    .auth().basic("aluno", "alunopassword")
                    .get("/alunos/" + entityOne.getId())
                .then()
                    .extract()
                    .response();

        var dto = response.getBody().as(AlunoResponse.class);

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }

}