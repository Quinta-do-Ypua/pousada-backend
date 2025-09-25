package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamento;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamentoDTO;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamentoMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meio-pagamento")
public class MeioPagamentoController extends BaseController<MeioPagamento, MeioPagamentoDTO, Long, MeioPagamentoMapper> {

    public MeioPagamentoController(MeioPagamentoMapper mapper, BaseServiceInterface<MeioPagamento, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }
}
