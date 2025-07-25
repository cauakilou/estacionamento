package com.example.Estacionamento.Repository;

import com.example.Estacionamento.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}