package com.senai.pousadabackend.domain.meioPagamento.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamento;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class MeioPagamentoServiceImpl extends BaseService<MeioPagamento, Long, MeioPagamentoRepository> implements MeioPagamentoService {

    public MeioPagamentoServiceImpl(MeioPagamentoRepository repo) {
        super(repo);
    }
}
