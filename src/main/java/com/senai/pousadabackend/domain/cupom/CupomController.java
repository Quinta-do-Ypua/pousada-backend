package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cupons")
public class CupomController extends BaseController<Cupom, CupomDTO, Long, CupomMapper> {

    public CupomController(CupomMapper mapper,
                           CupomService cupomService) {
        super(mapper, cupomService);
    }

}
