package com.example.Estacionamento;

import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoCreateDTO;
import com.example.Estacionamento.web.DTO.PageableDTO;
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
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
 class EstacionamentoIT {

    @Autowired
    WebTestClient testClient;

    // Testes da operação de Check-in
    @Test
     void criarChekIN_comDadosValidos_201Elocation(){
        EstacionamentoCreateDTO createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("09191773016")
                .build();
        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()

                .jsonPath("placa").isEqualTo("WER-1111")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("palio 1.0")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("clienteCpf").isEqualTo("09191773016")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();
    }

    @Test
     void criarChekIN_semAcesso_Status403(){
        EstacionamentoCreateDTO createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
     void criarChekIN_comDadosInvalidos_Status422(){
        EstacionamentoCreateDTO createDTO =EstacionamentoCreateDTO.builder()
                .placa("").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

        createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

        createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("")
                .cor("AZUL").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

        createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

        createDTO =EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
     void criarChekIN_comCpfInexistente_Status404() {
        EstacionamentoCreateDTO createDTO = EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("52524279847")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

    }

    @Sql(scripts = "/sql/estacionamentos/estacionamentos-insert-ocupada.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamentos-delete-ocupada.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
     void criarChekIN_comVagasOcupadas_Status404() {
        EstacionamentoCreateDTO createDTO = EstacionamentoCreateDTO.builder()
                .placa("WER-1111").marca("FIAT").modelo("palio 1.0")
                .cor("AZUL").clienteCpf("09191773016")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .bodyValue(createDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");

    }

    // Testes da operação de RecuperarRecibo

    @Test
     void RecuperaRecibo_comReciboExistente_Status200() {
        testClient
                .get().uri("/api/v1/estacionamentos/check-in/20250313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                        .jsonPath("placa").isEqualTo("FIT-1020")
                        .jsonPath("marca").isEqualTo("FIAT")
                        .jsonPath("modelo").isEqualTo("PALIO")
                        .jsonPath("cor").isEqualTo("VERDE")
                        .jsonPath("clienteCpf").isEqualTo("98401203015")
                        .jsonPath("recibo").isEqualTo("20250313-101300")
                        .jsonPath("dataEntrada").isEqualTo("2025-03-13 10:15:00")
                        .jsonPath("vagaCodigo").isEqualTo("A-01");

    }

    @Test
     void RecuperaRecibo_comReciboInexistente_Status404() {
        testClient
                .get().uri("/api/v1/estacionamentos/check-in/20230313-101310")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("method").isEqualTo("GET");

    }

    //Testes da operação de check-out

    @Test
     void CriarcheckOut_comReciboExistente_Status200(){
        testClient.put().uri("/api/v1/estacionamentos/check-out/20250313-101300")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("98401203015")
                .jsonPath("recibo").isEqualTo("20250313-101300")
                .jsonPath("dataEntrada").isEqualTo("2025-03-13 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01")
                .jsonPath("valor").exists()
                .jsonPath("dataSaida").exists()
                .jsonPath("desconto").exists();
    }

    @Test
     void CriarcheckOut_comReciboinexistente_Status404(){
        testClient.put().uri("/api/v1/estacionamentos/check-out/20230313-101300")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br","123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
     void CriarcheckOut_comReciboinexistente_Status403(){
        testClient.put().uri("/api/v1/estacionamentos/check-out/20250313-101300")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br","123456"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("method").isEqualTo("PUT");

    }

    //Testes de recuperar o cliente pelo CPF

    @Test
     void RecuperarVagas_cpfValido_status200(){
        PageableDTO responseBody = testClient
                .get()
                .uri("/api/v1/estacionamentos/cpf/98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br" , "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalElements()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/estacionamentos/cpf/98401203015?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br" , "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).hasSize(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
     void RecuperarVagas_roleCliente_status403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/estacionamentos/cpf/98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br" , "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    //Testes de recuperar pelo Cliente

    @Test
     void RecuperarVagas_cliente_status200(){
        PageableDTO responseBody = testClient
                .get()
                .uri("/api/v1/estacionamentos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br" , "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalElements()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/estacionamentos?size=1&page=0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br" , "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).hasSize(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

    }
}
