package com.example.Estacionamento.web.DTO.usuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO  {
    private String senhaAtual;
    private String novaSenha;
    private String confirmarSenha;

}
