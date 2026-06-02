package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.temaSistema.dto.TemaSistemaDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tema")
public class TemaSistemaController extends BaseController<TemaSistema, TemaSistemaDTO, Long, TemaSistemaMapper> {

    private final TemaSistemaMapper temaSistemaMapper;
    private final TemaSistemaService temaSistemaService;

    public TemaSistemaController(TemaSistemaMapper mapper, TemaSistemaService temaSistemaService) {
        super(mapper, temaSistemaService);
        this.temaSistemaMapper = mapper;
        this.temaSistemaService = temaSistemaService;
    }

    @PutMapping("/{id}")
    public TemaSistemaDTO atualizarPorId(@PathVariable Long id, @RequestBody TemaSistemaDTO dto) {
        TemaSistema tema = temaSistemaService.buscarOuCriarPadrao(id);
        temaSistemaMapper.updateEntityFromDTO(dto, tema);
        return temaSistemaMapper.toDTO(temaSistemaService.atualizar(tema));
    }

}
