package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.jwt.JwtToken;
import com.example.Estacionamento.jwt.JwtUserDetailsService;
import com.example.Estacionamento.web.DTO.usuario.UsuarioLoginDTO;
import com.example.Estacionamento.web.DTO.usuario.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Tag(name="Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AutenticacaoController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;


    @Operation(summary = "Autentica um usuario",description = "cria um Token de acesso para a API",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Token gerado com Sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais invalidas",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos Invalidos",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(
            @RequestBody @Valid UsuarioLoginDTO dto, HttpServletRequest request
    ){
        log.info("Processo de autenticação pelo login {}",dto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e){
            log.warn("Credenciais erradas para {}", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                "credenciais invalidas"));
    }
}
