package com.senai.pousadabackend.service.quarto;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.repository.QuartoRepository;
import com.senai.pousadabackend.service.BaseService;
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
        if (quartoRepository.existsByNumero(quarto.getNumero()))
                throw new RegistroDuplicadoException("Já existe um quarto com esse numero");
    }
}
