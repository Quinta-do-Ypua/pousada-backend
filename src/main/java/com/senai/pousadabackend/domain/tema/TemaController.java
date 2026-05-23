package com.senai.pousadabackend.domain.tema;

import com.senai.pousadabackend.domain.tema.dto.TemaDTO;
import com.senai.pousadabackend.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tema")
public class TemaController extends BaseController<Tema, TemaDTO, Long, TemaMapper> {

    public TemaController(TemaMapper mapper,
                          TemaService temaService) {
        super(mapper, temaService);
    }

}