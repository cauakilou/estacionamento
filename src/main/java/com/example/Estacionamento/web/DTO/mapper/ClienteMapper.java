package com.example.Estacionamento.web.DTO.mapper;

import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.web.DTO.ClienteCreateDTO;
import com.example.Estacionamento.web.DTO.ClienteResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDTO dto){
        return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDTO toDto(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDTO.class);
    }


}
