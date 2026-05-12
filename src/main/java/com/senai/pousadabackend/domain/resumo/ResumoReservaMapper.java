package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.domain.cliente.ClienteMapper;
import com.senai.pousadabackend.domain.resumo.dto.ResumoReservaDto;
import com.senai.pousadabackend.domain.resumo.itemnf.ResumoReservaItemReduzidoMapper;
import org.springframework.stereotype.Component;

@Component
public class ResumoReservaMapper {

    private final ResumoReservaItemReduzidoMapper resumoReservaItemReduzidoMapper;
    private final ClienteMapper clienteMapper;

    public ResumoReservaMapper(ResumoReservaItemReduzidoMapper resumoReservaItemReduzidoMapper, ClienteMapper clienteMapper) {
        this.resumoReservaItemReduzidoMapper = resumoReservaItemReduzidoMapper;
        this.clienteMapper = clienteMapper;
    }

    public ResumoReservaDto toDTO(ResumoReserva resumoReserva) {
        return ResumoReservaDto.builder()
                .idNotaFiscal(resumoReserva.getIdNotaFiscal())
                .itens(resumoReserva.getItens().stream().map(resumoReservaItemReduzidoMapper::toDTO).toList())
                .cliente(clienteMapper.toDTO(resumoReserva.getCliente()))
                .dataCadastro(resumoReserva.getDataCadastro())
                .numero(resumoReserva.getNumero())
                .valorTotal(resumoReserva.getValorTotal())
                .build();
    }

}
