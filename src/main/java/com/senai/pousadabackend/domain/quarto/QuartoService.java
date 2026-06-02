package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.domain.reserva.ReservaService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistrosVinculadosException;
import org.springframework.stereotype.Service;

@Service
public class QuartoService extends BaseService<Quarto, Long, QuartoRepository> {

    private final QuartoRepository quartoRepository;
    private final ReservaService reservaService;

    public QuartoService(QuartoRepository repo,
                         ReservaService reservaService) {
        super(repo);
        this.quartoRepository = repo;
        this.reservaService = reservaService;
    }

    @Override
    public Quarto salvar(Quarto quarto) {
        if (quarto.isNovo()) {
            Quarto quartoEncontrado = quartoRepository.findByNome(quarto.getNome());
            if (quartoEncontrado != null && !quartoEncontrado.getId().equals(quarto.getId())) {
                throw new RegistroDuplicadoException("Já existe um quarto com esse nome");
            }
        }
        return super.salvar(quarto);
    }

    @Override
    public Quarto excluir(Long id) {
        Quarto quarto = buscarPorId(id);
        if (!reservaService.buscarPorQuarto(quarto).isEmpty())
            throw new RegistrosVinculadosException("Não é possivel excluir o quarto pois existem reservas vinculadas a ele.");
        return super.excluir(id);
    }

}
