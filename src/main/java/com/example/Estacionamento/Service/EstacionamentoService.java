package com.example.Estacionamento.Service;


import com.example.Estacionamento.Entity.Cliente;
import com.example.Estacionamento.Entity.ClienteVagas;
import com.example.Estacionamento.Entity.Vaga;
import com.example.Estacionamento.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVagas chekIn(ClienteVagas clienteVagas){
        Cliente cliente = clienteService.buscarPorCpf(clienteVagas.getCliente().getCpf());
        clienteVagas.setCliente(cliente);
        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVagas.setVaga(vaga);

        clienteVagas.setDataEntrada(LocalDateTime.now());
        clienteVagas.setRecibo(EstacionamentoUtils.gerarRecibo());
       return clienteVagaService.salvar(clienteVagas);
    }

    public ClienteVagas chekOut(String recibo) {
        ClienteVagas clienteVagas = clienteVagaService.buscarPorRecibo(recibo);
        LocalDateTime dataSaida = LocalDateTime.now();
        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVagas.getDataEntrada(),dataSaida);
        clienteVagas.setValor(valor);

        long totalDeVezes =clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVagas.getCliente().getCpf());
        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor,totalDeVezes);
        clienteVagas.setDesconto(desconto);
        clienteVagas.setDataSaida(dataSaida);
        clienteVagas.getVaga().setStatus(Vaga.StatusVaga.LIVRE);
        return clienteVagaService.salvar(clienteVagas);
    }
}
