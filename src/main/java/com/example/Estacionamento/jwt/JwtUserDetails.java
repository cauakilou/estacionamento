package com.example.Estacionamento.jwt;

import com.example.Estacionamento.Entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

public class JwtUserDetails extends User{

    private Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(),
                AuthorityUtils.createAuthorityList(usuario.getRole().name())
        );
        this.usuario = usuario;
    }

    public Long getId(){
        return this.usuario.getId();
    }

    public String getRole(){
        return this.usuario.getRole().name();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JwtUserDetails that = (JwtUserDetails) o;
        return Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usuario);
    }
}
