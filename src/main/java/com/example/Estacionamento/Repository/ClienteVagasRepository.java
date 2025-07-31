package com.example.Estacionamento.Repository;

import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Repository.projection.ClienteVagaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteVagasRepository extends JpaRepository<ClienteVagas, Long> {
    Optional<ClienteVagas> findByRecibo(String recibo);

    Optional<ClienteVagas> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

    Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);
}