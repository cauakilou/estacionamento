package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.Service.UsuarioService;
import com.example.Estacionamento.web.DTO.UsuarioCreateDTO;
import com.example.Estacionamento.web.DTO.UsuarioResponseDTO;
import com.example.Estacionamento.web.DTO.UsuarioSenhaDTO;
import com.example.Estacionamento.web.DTO.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.modelmapper.internal.bytebuddy.implementation.Implementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@Tag(name = "Usuarios",description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuario")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "criar um novo usuario",description = "cria um usuario",
    responses = {
            @ApiResponse(responseCode = "201",description = "recurso criado com sucesso",
                    content = @Content(mediaType = "Application/Json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "usuario e-mail já Cadastrado no sistema",
                    content = @Content(mediaType = "Application/Json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "dados de entrada invalidos",
                    content = @Content(mediaType = "Application/Json",
                            schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid  @RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuariodto(user));
    }

    @Operation(summary = "Recuperar um usuario com base no ID", description = "recupera um usuario pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toUsuariodto(user));
    }


    @Operation(summary = "Atualizar a senha de um usuario",
            description = "Atualiza a senha de um usuario ao selecionar seu ID e passar 3 parametros, a senha atual e 2x a nova senha",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Senha alterado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "senha não confere",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id
            , @Valid @RequestBody UsuarioSenhaDTO dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmarSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar todos os usuarios do banco", description = "recupera todos os usuarios registrados",
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class))))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> listaDeUsuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDTO(listaDeUsuarios) );

    }
}

