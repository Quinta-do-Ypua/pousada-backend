package com.senai.pousadabackend.domain.nota_fiscal.service;

import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.nota_fiscal.item.Item;
import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscal;
import com.senai.pousadabackend.domain.nota_fiscal.item_nf.NotaFiscalItem;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscalRepository;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.complemento.service.ComplementoService;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotaFiscalServiceImpl extends BaseService<NotaFiscal, Long, NotaFiscalRepository> implements NotaFiscalService {

    private final QuartoService quartoService;

    private final ComplementoService complementoService;

    public NotaFiscalServiceImpl(NotaFiscalRepository repo,
                                 @Qualifier("quartoServiceProxy") QuartoService quartoService,
                                 @Qualifier("complementoServiceProxy") ComplementoService complementoService) {
        super(repo);
        this.quartoService = quartoService;
        this.complementoService = complementoService;
    }

    @Override
    public NotaFiscal criarNotaFiscalAPartirDaReserva(Reserva reserva) {
        NotaFiscal notaFiscal = gerarNotafiscalInicial(reserva);

        List<NotaFiscalItem> itens = prepararComplementos(reserva, notaFiscal);
        itens.add(prepararQuarto(reserva, notaFiscal));

        notaFiscal.setItens(itens);
        notaFiscal.setValorTotal(itens.stream()
                .map(NotaFiscalItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return this.salvar(notaFiscal);
    }

    private NotaFiscal gerarNotafiscalInicial(Reserva reserva) {
        return NotaFiscal.builder()
                .numero("NF-" + System.currentTimeMillis())
                .dataCadastro(LocalDate.now())
                .cliente(reserva.getCliente())
                .build();
    }

    private NotaFiscalItem prepararQuarto(Reserva reserva, NotaFiscal notaFiscal) {
        Quarto quarto = quartoService.buscarPorId(reserva.getQuarto().getId());

        long dias = Math.max(1, ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut()));

        return NotaFiscalItem.builder()
                .notaFiscal(notaFiscal)
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

    private List<NotaFiscalItem> prepararComplementos(Reserva reserva, NotaFiscal notaFiscal) {
        List<NotaFiscalItem> itens = new ArrayList<>();

        for (var complementoRef : reserva.getComplementos()) {
            Long id = complementoRef.getId();

            NotaFiscalItem itemExistente = null;
            for (NotaFiscalItem item : itens) {
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
                NotaFiscalItem itemComp = prepararComplemento(complemento, notaFiscal);
                itens.add(itemComp);
            }
        }

        return itens;
    }

    private NotaFiscalItem prepararComplemento(Complemento complemento, NotaFiscal notaFiscal) {
        return NotaFiscalItem.builder()
                .notaFiscal(notaFiscal)
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
