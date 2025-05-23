package com.senai.pousadabackend.domain.quarto.service;

import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.domain.quarto.QuartoRepository;
import com.senai.pousadabackend.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceImpl extends BaseService<Quarto, Long, QuartoRepository> implements QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoServiceImpl(QuartoRepository repo, QuartoRepository quartoRepository) {
        super(repo);
        this.quartoRepository = quartoRepository;
    }

    @Override
    public Quarto salvar(Quarto quarto) {
        prepararQuarto(quarto);
        return super.salvar(quarto);
    }

    private void prepararQuarto(Quarto quarto) {
        validarViolacaoDeUnique(quarto);
    }

    private void validarViolacaoDeUnique(Quarto quarto) {
        if (quarto.isNovo()) {
            Quarto quartoEncontrado = quartoRepository.findByNome(quarto.getNome());
            if (quartoEncontrado != null && !quartoEncontrado.getId().equals(quarto.getId())) {
                throw new RegistroDuplicadoException("Já existe um quarto com esse nome");
            }
        }
    }
}
