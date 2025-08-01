package com.example.Estacionamento.Service;

import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.Exception.EntityNotFoundException;
import com.example.Estacionamento.Exception.PasswordInvalidException;
import com.example.Estacionamento.Exception.UserNameUniqueViolationException;
import com.example.Estacionamento.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UserNameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }

    }
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario id=%s não encontrado", id))
        );
    }
    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmarSenha) {
        if (!novaSenha.equals(confirmarSenha)){
            throw new PasswordInvalidException("nova senha não confere com confirmação de senha");
        }else {
            Usuario user = buscarPorId(id);
            if(!passwordEncoder.matches(senhaAtual,user.getPassword())){
                throw new PasswordInvalidException("As senhas não coincidem");
            } else {
                user.setPassword(passwordEncoder.encode(novaSenha));
                return user;
            }

        }


    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario %s não encontrado", username))
        );
    }

    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow().getRole();
    }
}
