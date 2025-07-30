package com.example.Estacionamento.Service;

import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.Exception.CpfUniqueViolationException;
import com.example.Estacionamento.Exception.EntityNotFoundException;
import com.example.Estacionamento.Repository.ClienteRepository;
import com.example.Estacionamento.Repository.projection.ClienteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("cliente com id=%s não encontrado",id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);

    }
}
