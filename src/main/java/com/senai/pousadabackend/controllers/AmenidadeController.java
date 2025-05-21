package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.AmenidadeDto;
import com.senai.pousadabackend.entity.Amenidade;
import com.senai.pousadabackend.mappers.AmenidadeMapper;
import com.senai.pousadabackend.service.amenidade.AmenidadeService;
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
