package com.example.Estacionamento.web.DTO.mapper;

import com.example.Estacionamento.Entity.Usuario;
import com.example.Estacionamento.web.DTO.UsuarioCreateDTO;
import com.example.Estacionamento.web.DTO.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
 }

