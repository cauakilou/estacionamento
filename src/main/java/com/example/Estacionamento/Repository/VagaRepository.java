package com.example.Estacionamento.Repository;

import com.example.Estacionamento.Entity.Vaga;
import io.netty.util.internal.ObjectPool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga LIVRE);
}