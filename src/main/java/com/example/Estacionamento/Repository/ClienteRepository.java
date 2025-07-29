package com.example.Estacionamento.Repository;

import com.example.Estacionamento.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}