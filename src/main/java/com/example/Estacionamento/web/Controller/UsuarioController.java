package com.example.Estacionamento.web.Controller;


import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.Service.UsuarioService;
import com.example.Estacionamento.web.DTO.UsuarioCreateDTO;
import com.example.Estacionamento.web.DTO.UsuarioResponseDTO;
import com.example.Estacionamento.web.DTO.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuariodto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id
            , @RequestBody Usuario usuario) {
        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> listaDeUsuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(listaDeUsuarios);

    }
}

