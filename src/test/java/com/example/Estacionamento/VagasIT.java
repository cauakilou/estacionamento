package com.example.Estacionamento;


import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.web.DTO.vaga.VagaCreateDTO;
import com.example.Estacionamento.web.DTO.vaga.VagaResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vagas/vagas-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vagas/vagas-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagasIT {

    @Autowired
    WebTestClient testClient;

    //Testes referentes ao metodo post
    @Test
    public void createVaga_parametrosValidos_CriadoComStatus201(){
         testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-10","LIVRE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);

        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-11","OCUPADA"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createVaga_parametrosInvalidos_ERROR422(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-101","LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);



        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-11","OCUPADAs"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();


                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


    }

    @Test
    public void createVaga_codigoRepetido_ERROR409(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-04","LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);



        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com" , "123456"))
                .bodyValue(new VagaCreateDTO("A-03","OCUPADA"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);


    }

    @Test
    public void createVaga_client_ERROR403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .bodyValue(new VagaCreateDTO("A-22", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    //Testes para encontrar vagas

    @Test
    public void searchVaga_parametrosValidos_Status200() {
        VagaResponseDTO responseBody = testClient
                .get()
                .uri("/api/v1/vagas/A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(VagaResponseDTO.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo("LIVRE");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCodigo()).isEqualTo("A-01");

        responseBody = testClient
                .get()
                .uri("/api/v1/vagas/A-03")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(VagaResponseDTO.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo("OCUPADA");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCodigo()).isEqualTo("A-03");

    }

    @Test
    public void searchVaga_parametrosInValidos_Status404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/vagas/A-11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void searchVaga_SemRole_Status403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/vagas/A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

}
