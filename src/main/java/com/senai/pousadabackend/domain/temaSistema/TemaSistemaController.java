package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.temaSistema.dto.TemaSistemaDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tema")
public class TemaSistemaController extends BaseController<TemaSistema, TemaSistemaDTO, Long, TemaSistemaMapper> {

    public TemaSistemaController(TemaSistemaMapper mapper,
                                  BaseServiceInterface<TemaSistema, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }

}
