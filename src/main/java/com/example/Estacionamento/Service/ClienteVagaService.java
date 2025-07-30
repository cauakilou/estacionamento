package com.example.Estacionamento.Service;


import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Repository.ClienteVagasRepository;
import lombok.RequiredArgsConstructor;
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
}
