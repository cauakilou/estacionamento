package com.example.Estacionamento.web.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaResponseDTO {
    private long id;
    private String codigo;
    private String status;

}
