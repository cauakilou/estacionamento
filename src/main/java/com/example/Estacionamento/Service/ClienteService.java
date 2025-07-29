package com.example.Estacionamento.Service;

import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.Exception.CpfUniqueViolationException;
import com.example.Estacionamento.Repository.ClienteRepository;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(
                    String.format("cpf %s Não pode ser cadastrado, já existe",
                            cliente.getCpf()
                    )
            );

        }

    }

}
