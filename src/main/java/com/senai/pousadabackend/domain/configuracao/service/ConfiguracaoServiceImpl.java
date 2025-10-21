package com.senai.pousadabackend.domain.configuracao.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.configuracao.Configuracao;
import com.senai.pousadabackend.domain.configuracao.ConfiguracaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoServiceImpl extends BaseService<Configuracao, Long, ConfiguracaoRepository> implements ConfiguracaoService {

    public ConfiguracaoServiceImpl(ConfiguracaoRepository repo) {
        super(repo);
    }

}
