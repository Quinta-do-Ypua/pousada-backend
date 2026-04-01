package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto;
import com.senai.pousadabackend.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("amenidades")
public class AmenidadeController extends BaseController<Amenidade, AmenidadeDto, Long, AmenidadeMapper> {

    public AmenidadeController(AmenidadeMapper mapper,
                               AmenidadeService amenidadeService) {
        super(mapper, amenidadeService);
    }

}
