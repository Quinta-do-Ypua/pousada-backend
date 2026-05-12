package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.resumo.item.Item;
import com.senai.pousadabackend.domain.resumo.itemnf.ResumoReservaItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResumoReservaService extends BaseService<ResumoReserva, Long, ResumoReservaRepository> {

    private final QuartoService quartoService;
    private final ComplementoService complementoService;

    public ResumoReservaService(ResumoReservaRepository repo,
                                QuartoService quartoService,
                                ComplementoService complementoService) {
        super(repo);
        this.quartoService = quartoService;
        this.complementoService = complementoService;
    }

    public void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva) {
        Thread.startVirtualThread(() -> gerarNotaFiscalAPartirDaReserva(reserva));
    }

    public ResumoReserva criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva) {
        return gerarNotaFiscalAPartirDaReserva(reserva);
    }

    private ResumoReserva gerarNotaFiscalAPartirDaReserva(Reserva reserva) {
        ResumoReserva resumoReserva = gerarNotafiscalInicial(reserva);

        List<ResumoReservaItem> itens = prepararComplementos(reserva, resumoReserva);
        itens.add(prepararQuarto(reserva, resumoReserva));

        resumoReserva.setItens(itens);
        resumoReserva.setValorTotal(itens.stream()
                .map(ResumoReservaItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return this.salvar(resumoReserva);
    }

    private ResumoReserva gerarNotafiscalInicial(Reserva reserva) {
        return ResumoReserva.builder()
                .numero("NF-" + System.currentTimeMillis())
                .dataCadastro(LocalDate.now())
                .cliente(reserva.getCliente())
                .build();
    }

    private ResumoReservaItem prepararQuarto(Reserva reserva, ResumoReserva resumoReserva) {
        Quarto quarto = quartoService.buscarPorId(reserva.getQuarto().getId());
        long dias = Math.max(1, ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut()));

        return ResumoReservaItem.builder()
                .resumoReserva(resumoReserva)
                .item(Item.builder()
                        .itemId(quarto.getId())
                        .tipoItem("Quarto")
                        .valorUnitario(quarto.getValorDiaria())
                        .build())
                .quantidade((int) dias)
                .valorUnitario(quarto.getValorDiaria())
                .valorTotal(quarto.getValorDiaria().multiply(BigDecimal.valueOf(dias)))
                .build();
    }

    private List<ResumoReservaItem> prepararComplementos(Reserva reserva, ResumoReserva resumoReserva) {
        List<ResumoReservaItem> itens = new ArrayList<>();

        for (var complementoRef : reserva.getComplementos()) {
            Long id = complementoRef.getId();

            ResumoReservaItem itemExistente = null;
            for (ResumoReservaItem item : itens) {
                if (item.getItem().getItemId().equals(id)
                        && item.getItem().getTipoItem().equalsIgnoreCase("Complemento")) {
                    itemExistente = item;
                    break;
                }
            }

            if (itemExistente != null) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + 1);
                itemExistente.setValorTotal(
                        itemExistente.getValorUnitario().multiply(BigDecimal.valueOf(itemExistente.getQuantidade()))
                );
            } else {
                Complemento complemento = complementoService.buscarPorId(id);
                itens.add(prepararComplemento(complemento, resumoReserva));
            }
        }

        return itens;
    }

    private ResumoReservaItem prepararComplemento(Complemento complemento, ResumoReserva resumoReserva) {
        return ResumoReservaItem.builder()
                .resumoReserva(resumoReserva)
                .item(Item.builder()
                        .tipoItem("Complemento")
                        .itemId(complemento.getId())
                        .valorUnitario(complemento.getValor())
                        .build())
                .quantidade(1)
                .valorUnitario(complemento.getValor())
                .valorTotal(complemento.getValor())
                .build();
    }

}
