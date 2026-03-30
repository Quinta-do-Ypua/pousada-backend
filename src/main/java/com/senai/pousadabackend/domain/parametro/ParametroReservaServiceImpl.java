package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.core.BaseService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ParametroReservaServiceImpl extends BaseService<ParametroReserva, Long, ParametroReservaRepository> implements ParametroReservaService {

    private final ParametroReservaRepository repository;
    private ParametroReserva cache;

    public ParametroReservaServiceImpl(ParametroReservaRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @PostConstruct
    @Scheduled(fixedDelay = 60000)
    public void recarregarCache() {
        this.cache = repository.findById(1L)
                .orElse(ParametroReserva.comDefaults());
    }

    public ParametroReserva getParametros() {
        if (cache == null) {
            recarregarCache();
        }
        return cache;
    }

    public Boolean isBloquearReservaComPendencia() {
        return getParametros().getBloquearReservaComPendencia();
    }

    public Boolean isMultaCancelamentoAtiva() {
        return getParametros().getMultaCancelamentoAtiva();
    }

    public Integer getMaxReservasAtivasPorUsuario() {
        return getParametros().getMaxReservasAtivasPorUsuario();
    }

    public Integer getTempoEntreReservasDias() {
        return getParametros().getTempoEntreReservasDias();
    }

    public Integer getPrazoMaximoCancelamentoDias() {
        return getParametros().getPrazoMaximoCancelamentoDias();
    }

    public Integer getTempoMinimoParaReservaDias() {
        return getParametros().getTempoMinimoParaReservaDias();
    }

    public Integer getDuracaoMinimaDias() {
        return getParametros().getDuracaoMinimaDias();
    }

    public Integer getDuracaoMaximaDias() {
        return getParametros().getDuracaoMaximaDias();
    }

    public java.time.LocalTime getHorarioCheckIn() {
        return getParametros().getHorarioCheckIn();
    }

    public java.time.LocalTime getHorarioCheckOut() {
        return getParametros().getHorarioCheckOut();
    }

    public java.math.BigDecimal getPercentualMultaCancelamento() {
        return getParametros().getPercentualMultaCancelamento();
    }

}
