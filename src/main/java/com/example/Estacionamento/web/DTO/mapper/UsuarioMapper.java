package com.example.Estacionamento.web.DTO.mapper;

import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.web.DTO.UsuarioCreateDTO;
import com.example.Estacionamento.web.DTO.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO createDTO){
        return new ModelMapper().map(createDTO,Usuario.class);

    }

    public static UsuarioResponseDTO toUsuariodto(Usuario usuario){
        String role = usuario.getRole().name().substring("role_ ".length());
        return new ModelMapper().map(usuario, UsuarioResponseDTO.class);

    }
}
