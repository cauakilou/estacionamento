package com.example.Estacionamento.Repository;

import com.example.Estacionamento.Entity.ClienteVagas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteVagasRepository extends JpaRepository<ClienteVagas, Long> {
}