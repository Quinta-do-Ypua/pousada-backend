package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import com.senai.pousadabackend.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("complementos")
public class ComplementoController extends BaseController<Complemento, ComplementoDTO, Long, ComplementoMapper> {

    public ComplementoController(ComplementoMapper mapper,
                                 ComplementoService complementoService) {
        super(mapper, complementoService);
    }
}
