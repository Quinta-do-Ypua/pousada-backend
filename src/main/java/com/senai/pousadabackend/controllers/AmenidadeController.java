package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.amenidade.Amenidade;
import com.senai.pousadabackend.domain.amenidade.AmenidadeDto;
import com.senai.pousadabackend.domain.amenidade.AmenidadeMapper;
import com.senai.pousadabackend.domain.amenidade.service.AmenidadeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("amenidades")
public class AmenidadeController extends BaseController<Amenidade, AmenidadeDto, Long, AmenidadeMapper> {
    public AmenidadeController(AmenidadeMapper mapper, @Qualifier("amenidadeServiceImpl") AmenidadeService amenidadeService) {
        super(mapper, amenidadeService);
    }
}
