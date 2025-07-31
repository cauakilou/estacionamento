package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.Repository.projection.ClienteVagaProjection;
import com.example.Estacionamento.Service.ClienteVagaService;
import com.example.Estacionamento.Service.EstacionamentoService;
import com.example.Estacionamento.jwt.JwtUserDetails;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoCreateDTO;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoResponseDTO;
import com.example.Estacionamento.web.DTO.PageableDTO;
import com.example.Estacionamento.web.DTO.mapper.ClienteVagaMapper;
import com.example.Estacionamento.web.DTO.mapper.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Estacionamento",description = "Contém todas as operações relativas aos recursos do estacionamento")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;
    private final ClienteVagaService clienteVagaService;

    @Operation(summary = "Faz um chek-in",description =  "cria uma nova vaga" +
            "Necessario autenticação de ADMIN",
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

    @Operation(summary = "recupera pelo recibo",description =  "recupera a vaga pelo recibo" +
            "Necessario autenticação de ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole ('ADMIN', 'CLIENT')")
    public ResponseEntity<EstacionamentoResponseDTO> getByRecibo(@PathVariable String recibo){
        ClienteVagas clienteVagas = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponseDTO dto = ClienteVagaMapper.toClienteVagasDTO(clienteVagas);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Faz um chek-out",description =  "retira um carro de uma vaga" +
            "Necessario autenticação de ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "vaga liberada",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "recibo não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasAnyRole ('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkOut(@PathVariable String recibo){
        ClienteVagas clienteVagas = estacionamentoService.chekOut(recibo);
        EstacionamentoResponseDTO dto = ClienteVagaMapper.toClienteVagasDTO(clienteVagas);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "recupera pelo cpf",description =  "recupera todas as vagas do cliente pelo cpf" +
            "Necessario autenticação de ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "403",description = "Sem Permissão",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> getAllEstacionamentoByCpf(@PathVariable String cpf,
                                                                 @PageableDefault(size = 5,sort = "dataEntrada",
                                                                         direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarPorClienteCpf(cpf,pageable);
        PageableDTO dto = PageableMapper.pageableToDTO(projection);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "recupera o proprio cliente",description =  "recupera tudo pelo proprio cliente" +
            "Necessario autenticação de CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "403",description = "Sem Permissão",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PageableDTO> getAllEstacionamentoDoCliente(@AuthenticationPrincipal JwtUserDetails user,
                                                                     @PageableDefault(size = 5,sort = "dataEntrada",
                                                                         direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorUsuarioId(user.getId(),pageable);
        PageableDTO dto = PageableMapper.pageableToDTO(projection);
        return ResponseEntity.ok(dto);
    }



}
