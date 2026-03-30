package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class MeioPagamentoServiceImpl extends BaseService<MeioPagamento, Long, MeioPagamentoRepository> implements MeioPagamentoService {

    public MeioPagamentoServiceImpl(MeioPagamentoRepository repo) {
        super(repo);
    }
}
