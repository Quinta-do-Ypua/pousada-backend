package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class MeioPagamentoService extends BaseService<MeioPagamento, Long, MeioPagamentoRepository> {

    public MeioPagamentoService(MeioPagamentoRepository repo) {
        super(repo);
    }

}
