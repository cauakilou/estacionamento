package com.example.Estacionamento.Service;

import com.example.Estacionamento.Entity.Vaga;
import com.example.Estacionamento.Exception.CodigoUniqueException;
import com.example.Estacionamento.Exception.EntityNotFoundException;
import com.example.Estacionamento.Repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga){
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException e) {
            throw new CodigoUniqueException(
                    String.format("Vaga com codigo %s já registrado",vaga.getCodigo())
            );
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo){
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                ()->new EntityNotFoundException(
                        String.format("Codigo %s não encontrado",codigo)
                )
        );

    }
}
