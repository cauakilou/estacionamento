package com.example.Estacionamento.web.Controller;

import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.Repository.ClienteRepository;
import com.example.Estacionamento.Service.ClienteService;
import com.example.Estacionamento.Service.UsuarioService;
import com.example.Estacionamento.jwt.JwtUserDetails;
import com.example.Estacionamento.web.DTO.ClienteCreateDTO;
import com.example.Estacionamento.web.DTO.ClienteResponseDTO;
import com.example.Estacionamento.web.DTO.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clientes",description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> adicionarCliente(@RequestBody @Valid ClienteCreateDTO dto,
                                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }


}
