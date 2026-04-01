package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.meioPagamento.dto.MeioPagamentoDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meios-pagamento")
public class MeioPagamentoController extends BaseController<MeioPagamento, MeioPagamentoDTO, Long, MeioPagamentoMapper> {

    public MeioPagamentoController(MeioPagamentoMapper mapper,
                                   BaseServiceInterface<MeioPagamento, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }
}
