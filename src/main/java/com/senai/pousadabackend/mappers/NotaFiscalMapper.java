package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.NotaFiscalDto;
import com.senai.pousadabackend.entity.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalMapper {

    private final NotaFiscalItemReduzidoMapper notaFiscalItemReduzidoMapper;
    private final ClienteMapper clienteMapper;

    public NotaFiscalMapper(NotaFiscalItemReduzidoMapper notaFiscalItemReduzidoMapper, ClienteMapper clienteMapper) {
        this.notaFiscalItemReduzidoMapper = notaFiscalItemReduzidoMapper;
        this.clienteMapper = clienteMapper;
    }

    public NotaFiscalDto toDTO(NotaFiscal notaFiscal) {
        return NotaFiscalDto.builder()
                .idNotaFiscal(notaFiscal.getIdNotaFiscal())
                .itens(notaFiscal.getItens().stream().map(notaFiscalItemReduzidoMapper::toDTO).toList())
                .cliente(clienteMapper.toDTO(notaFiscal.getCliente()))
                .dataCadastro(notaFiscal.getDataCadastro())
                .numero(notaFiscal.getNumero())
                .valorTotal(notaFiscal.getValorTotal())
                .build();
    }

}
