package com.senai.pousadabackend.domain.quarto.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoRepository;
import com.senai.pousadabackend.domain.reserva.service.ReservaService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistrosVinculadosException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceImpl extends BaseService<Quarto, Long, QuartoRepository> implements QuartoService {

    private final QuartoRepository quartoRepository;
    private final ReservaService reservaService;

    public QuartoServiceImpl(QuartoRepository repo,
                             QuartoRepository quartoRepository,
                             @Qualifier("reservaServiceProxy") ReservaService reservaService) {
        super(repo);
        this.quartoRepository = quartoRepository;
        this.reservaService = reservaService;
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

    @Override
    public Quarto excluir(Long id) {
        Quarto quarto = buscarPorId(id);
        podeExcluir(quarto);
        return super.excluir(id);
    }

    private void podeExcluir(Quarto quarto) {
        if (!reservaService.buscarPorQuarto(quarto).isEmpty())
            throw new RegistrosVinculadosException("Não é possivel excluir o quarto pois existem reservas vinculadas a ele.");
    }
}
