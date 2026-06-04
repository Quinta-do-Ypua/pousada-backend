package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.parametro.dto.ParametroReservaDTO;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parametros-reserva")
public class ParametroReservaController extends BaseController<ParametroReserva, ParametroReservaDTO, Long, ParametroReservaMapper> {

    private final ParametroReservaMapper parametroMapper;
    private final BaseServiceInterface<ParametroReserva, Long> service;

    public ParametroReservaController(ParametroReservaMapper mapper,
                                       BaseServiceInterface<ParametroReserva, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
        this.parametroMapper = mapper;
        this.service = baseServiceInterface;
    }

    @Override
    @PutMapping
    @Transactional
    public ParametroReservaDTO alterarPorId(@RequestBody ParametroReservaDTO dto) {
        ParametroReserva existente = service.buscarPorId(dto.getId());
        parametroMapper.updateEntityFromDTO(dto, existente);
        return parametroMapper.toDTO(service.atualizar(existente));
    }

}
