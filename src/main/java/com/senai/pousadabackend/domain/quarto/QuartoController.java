package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quartos")
public class QuartoController extends BaseController<Quarto, QuartoDTO, Long, QuartoMapper> {

    public QuartoController(QuartoMapper mapper,
                            QuartoService quartoService) {
        super(mapper, quartoService);
    }

}
