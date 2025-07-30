package com.example.Estacionamento.web.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class VagaCreateDTO {

    @NotBlank
    @Size(max = 4,min = 4)
    private String codigo;

    @NotBlank
    @Pattern(regexp = "LIVRE|OCUPADA")
    private String status;


}
