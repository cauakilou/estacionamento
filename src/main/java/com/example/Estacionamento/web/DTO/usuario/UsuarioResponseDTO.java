package com.example.Estacionamento.web.DTO.usuario;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDTO {
    private long id;

    private String username;

    private String role;
}
