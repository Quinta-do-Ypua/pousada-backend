package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cupons")
public class CupomController extends BaseController<Cupom, CupomDTO, Long, CupomMapper> {

    private final CupomService cupomService;
    private final CupomMapper cupomMapper;

    public CupomController(CupomMapper mapper,
                           CupomService cupomService) {
        super(mapper, cupomService);
        this.cupomService = cupomService;
        this.cupomMapper = mapper;
    }

    @GetMapping("/disponiveis")
    public List<CupomDTO> listarDisponiveis() {
        return cupomService.listarDisponiveis().stream()
                .map(cupomMapper::toDTO)
                .toList();
    }

    @GetMapping("/validar/{codigo}")
    public CupomDTO validarCupom(@PathVariable String codigo) {
        return cupomMapper.toDTO(cupomService.buscarCupomValido(codigo));
    }

}
