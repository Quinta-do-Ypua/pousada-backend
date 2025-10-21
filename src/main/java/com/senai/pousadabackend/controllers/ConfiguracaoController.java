package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.configuracao.Configuracao;
import com.senai.pousadabackend.domain.configuracao.ConfiguracaoDTO;
import com.senai.pousadabackend.domain.configuracao.ConfiguracaoMapper;
import com.senai.pousadabackend.domain.configuracao.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfiguracaoController extends BaseController<Configuracao, ConfiguracaoDTO, Long, ConfiguracaoMapper> {

    @Autowired
    private ConfiguracaoRepository repository;

    public ConfiguracaoController(ConfiguracaoMapper mapper, BaseServiceInterface<Configuracao, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }

}
