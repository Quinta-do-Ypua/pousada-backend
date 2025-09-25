package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.BaseMapper;
import com.senai.pousadabackend.domain.meioPagamento.enums.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class MeioPagamentoMapper implements BaseMapper<MeioPagamento, MeioPagamentoDTO> {

    @Override
    public MeioPagamentoDTO toDTO(MeioPagamento meioPagamento) {
        return MeioPagamentoDTO.builder()
                .id(meioPagamento.getId())
                .nome(meioPagamento.getNome())
                .descricao(meioPagamento.getDescricao())
                .tipo(meioPagamento.getTipo().toString())
                .ativo(meioPagamento.getAtivo())
                .taxaPercentual(meioPagamento.getTaxaPercentual())
                .taxaFixa(meioPagamento.getTaxaFixa())
                .prazoCompensacao(meioPagamento.getPrazoCompensacao())
                .permiteParcelamento(meioPagamento.getPermiteParcelamento())
                .maxParcelas(meioPagamento.getMaxParcelas())
                .build();
    }

    @Override
    public MeioPagamento toEntity(MeioPagamentoDTO meioPagamentoDTO) {
        return MeioPagamento.builder()
                .id(meioPagamentoDTO.getId())
                .nome(meioPagamentoDTO.getNome())
                .descricao(meioPagamentoDTO.getDescricao())
                .tipo(TipoPagamento.valueOf(meioPagamentoDTO.getTipo()))
                .ativo(meioPagamentoDTO.getAtivo())
                .taxaPercentual(meioPagamentoDTO.getTaxaPercentual())
                .taxaFixa(meioPagamentoDTO.getTaxaFixa())
                .prazoCompensacao(meioPagamentoDTO.getPrazoCompensacao())
                .permiteParcelamento(meioPagamentoDTO.getPermiteParcelamento())
                .maxParcelas(meioPagamentoDTO.getMaxParcelas())
                .build();
    }
}
