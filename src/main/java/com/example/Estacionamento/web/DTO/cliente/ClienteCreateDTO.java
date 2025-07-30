package com.example.Estacionamento.web.DTO.cliente;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class ClienteCreateDTO {
    @NotNull
    @Size(min = 3,max = 100)
    private String nome;
    @Size(min = 11,max = 11)
    @CPF
    private String cpf;

}
