package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.parametro.dto.ParametroReservaDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametros-reserva")
public class ParametroReservaController extends BaseController<ParametroReserva, ParametroReservaDTO, Long, ParametroReservaMapper> {

    public ParametroReservaController(ParametroReservaMapper mapper,
                                       BaseServiceInterface<ParametroReserva, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }

}
