package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Exception.ErrorMessage;
import com.example.Estacionamento.jwt.JwtToken;
import com.example.Estacionamento.jwt.JwtUserDetails;
import com.example.Estacionamento.jwt.JwtUserDetailsService;
import com.example.Estacionamento.web.DTO.UsuarioLoginDTO;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AutenticacaoControlller {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

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
