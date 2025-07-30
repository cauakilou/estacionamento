package com.example.Estacionamento.web.DTO.mapper;


import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoCreateDTO;
import com.example.Estacionamento.web.DTO.Estacionamento.EstacionamentoResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVagas toClienteVagas(EstacionamentoCreateDTO dto){
        return new ModelMapper().map(dto, ClienteVagas.class);
    }

    public static EstacionamentoResponseDTO toClienteVagasDTO(ClienteVagas clienteVagas){
        return new ModelMapper().map(clienteVagas,EstacionamentoResponseDTO.class);
    }


}
