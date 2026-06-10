package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.imagem.configuracao.service.ImagemConfiguracaoService;
import com.senai.pousadabackend.domain.temaSistema.dto.TemaSistemaDTO;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tema")
public class TemaSistemaController extends BaseController<TemaSistema, TemaSistemaDTO, Long, TemaSistemaMapper> {

    private final TemaSistemaMapper temaSistemaMapper;
    private final TemaSistemaService temaSistemaService;
    private final ImagemConfiguracaoService imagemConfiguracaoService;

    public TemaSistemaController(TemaSistemaMapper mapper, TemaSistemaService temaSistemaService,
                                 ImagemConfiguracaoService imagemConfiguracaoService) {
        super(mapper, temaSistemaService);
        this.temaSistemaMapper = mapper;
        this.temaSistemaService = temaSistemaService;
        this.imagemConfiguracaoService = imagemConfiguracaoService;
    }

    @PutMapping("/{id}")
    public TemaSistemaDTO atualizarPorId(@PathVariable Long id, @RequestBody TemaSistemaDTO dto) {
        TemaSistema tema = temaSistemaService.buscarOuCriarPadrao(id);
        temaSistemaMapper.updateEntityFromDTO(dto, tema);
        return temaSistemaMapper.toDTO(temaSistemaService.atualizar(tema));
    }

    @Override
    @DeleteMapping("/{id}")
    @Transactional
    public TemaSistemaDTO deletarPorId(@PathVariable(name = "id") Long id) {
        imagemConfiguracaoService.listarPor(id).forEach(imagemConfiguracaoService::deletar);
        temaSistemaService.excluirSeExistir(id);
        return null;
    }

}
