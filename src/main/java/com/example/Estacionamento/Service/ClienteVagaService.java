package com.example.Estacionamento.Service;


import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Exception.EntityNotFoundException;
import com.example.Estacionamento.Repository.ClienteVagasRepository;
import com.example.Estacionamento.Repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagasRepository repository;

    @Transactional
    public ClienteVagas salvar(ClienteVagas clienteVagas){
        return repository.save(clienteVagas);
    }

    @Transactional(readOnly = true)
    public ClienteVagas buscarPorRecibo(String recibo) {
        return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                ()->new EntityNotFoundException(String.format("recibo %s não encontrado ou check-out já realizado",recibo))
        );
    }
    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
    return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }
    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClienteCpf(cpf,pageable);
    }
    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return repository.findAllByClienteUsuarioId(id,pageable);
    }
}
