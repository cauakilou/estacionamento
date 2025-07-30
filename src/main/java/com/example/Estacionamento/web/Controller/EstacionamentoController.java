package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.Service.EstacionamentoService;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoCreateDTO;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoResponseDTO;
import com.example.Estacionamento.web.DTO.mapper.ClienteVagaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Estacionamento",description = "Contém todas as operações relativas aos recursos do estacionamento")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;

    @Operation(summary = "Faz um chek-in",description =  "cria uma nova vaga" +
            "Necessario autenticação de ADMIN client",
            security = @SecurityRequirement(name = "security"),
            responses = {
                     @ApiResponse(responseCode = "201",description = "recurso criado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não encontrada",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "dados de entrada invalidos",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/check-in")
    @PreAuthorize("hasRole ('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> chekIn(@RequestBody @Valid EstacionamentoCreateDTO dto){
        ClienteVagas clienteVagas = ClienteVagaMapper.toClienteVagas(dto);
        estacionamentoService.chekIn(clienteVagas);
        EstacionamentoResponseDTO responseDTO = ClienteVagaMapper.toClienteVagasDTO(clienteVagas);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{recibo}")
                .buildAndExpand(clienteVagas.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }


}
