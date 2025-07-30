package com.example.Estacionamento.web.DTO.mapper;

import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.web.DTO.usuario.UsuarioCreateDTO;
import com.example.Estacionamento.web.DTO.usuario.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    private UsuarioMapper(){}

    public static Usuario toUsuario(UsuarioCreateDTO createDTO){
        return new ModelMapper().map(createDTO,Usuario.class);

    }


    public static UsuarioResponseDTO toUsuariodto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDTO> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public static List<UsuarioResponseDTO> toListDTO(List<Usuario> usuarios){
        return usuarios.stream().map(UsuarioMapper::toUsuariodto).collect(Collectors.toList());

    }
 }

