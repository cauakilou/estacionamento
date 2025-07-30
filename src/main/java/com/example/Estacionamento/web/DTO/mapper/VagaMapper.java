package com.example.Estacionamento.web.DTO.mapper;


import com.example.Estacionamento.Entity.Vaga;
import com.example.Estacionamento.web.DTO.vaga.VagaCreateDTO;
import com.example.Estacionamento.web.DTO.vaga.VagaResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVagas(VagaCreateDTO dto){
        return new ModelMapper().map(dto,Vaga.class);

    }

    public static VagaResponseDTO vagasToDTO(Vaga vaga){
        return new ModelMapper().map(vaga,VagaResponseDTO.class);
    }

}
