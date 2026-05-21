package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.ClienteMapper;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import com.senai.pousadabackend.domain.resumo.dto.ResumoReservaDto;
import com.senai.pousadabackend.domain.resumo.item.Item;
import com.senai.pousadabackend.domain.resumo.item.ItemDto;
import com.senai.pousadabackend.domain.resumo.item.ItemMapper;
import com.senai.pousadabackend.domain.resumo.itemnf.ResumoReservaItem;
import com.senai.pousadabackend.domain.resumo.itemnf.ResumoReservaItemReduzidoDto;
import com.senai.pousadabackend.domain.resumo.itemnf.ResumoReservaItemReduzidoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResumoMapeadoresTest {

    private ItemMapper itemMapper;
    private ResumoReservaItemReduzidoMapper itemReduzidoMapper;
    private ResumoReservaMapper resumoMapper;

    @BeforeEach
    void setUp() {
        itemMapper = new ItemMapper();
        itemReduzidoMapper = new ResumoReservaItemReduzidoMapper(itemMapper);
        EnderecoMapper enderecoMapper = new EnderecoMapper();
        ClienteMapper clienteMapper = new ClienteMapper(enderecoMapper);
        resumoMapper = new ResumoReservaMapper(itemReduzidoMapper, clienteMapper);
    }

    @Test
    void itemMapper_toDto_mapeiaCorretamente() {
        Item item = Item.builder()
                .id(1L)
                .tipoItem("QUARTO")
                .itemId(10L)
                .valorUnitario(BigDecimal.valueOf(200))
                .build();

        ItemDto dto = itemMapper.toDto(item);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getTipoItem()).isEqualTo("QUARTO");
        assertThat(dto.getItemId()).isEqualTo(10L);
        assertThat(dto.getValorUnitario()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void itemReduzidoMapper_toDTO_mapeiaCorretamente() {
        Item item = Item.builder()
                .id(2L)
                .tipoItem("COMPLEMENTO")
                .itemId(5L)
                .valorUnitario(BigDecimal.valueOf(35))
                .build();

        ResumoReservaItem reservaItem = ResumoReservaItem.builder()
                .id(100L)
                .item(item)
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(35))
                .valorTotal(BigDecimal.valueOf(70))
                .build();

        ResumoReservaItemReduzidoDto dto = itemReduzidoMapper.toDTO(reservaItem);

        assertThat(dto.getId()).isEqualTo(100L);
        assertThat(dto.getQuantidade()).isEqualTo(2);
        assertThat(dto.getValorUnitario()).isEqualByComparingTo(BigDecimal.valueOf(35));
        assertThat(dto.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(70));
        assertThat(dto.getItem().getTipoItem()).isEqualTo("COMPLEMENTO");
    }

    @Test
    void resumoMapper_toDTO_mapeiaCorretamente() {
        Endereco endereco = Endereco.builder().id(1L).cidade("SP").estado("SP").rua("Rua A").build();
        Cliente cliente = Cliente.builder()
                .id(1L).nome("João").cpf("000.000.000-00").email("joao@email.com")
                .celular("(11) 99999-9999").sexo(Sexo.MASCULINO)
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .endereco(endereco).build();

        Item item = Item.builder()
                .id(1L).tipoItem("QUARTO").itemId(1L).valorUnitario(BigDecimal.valueOf(200)).build();

        ResumoReservaItem reservaItem = ResumoReservaItem.builder()
                .id(1L).item(item).quantidade(3).valorUnitario(BigDecimal.valueOf(200)).valorTotal(BigDecimal.valueOf(600)).build();

        ResumoReserva resumo = ResumoReserva.builder()
                .idNotaFiscal(1L)
                .numero("NF-001")
                .dataCadastro(LocalDate.now())
                .cliente(cliente)
                .valorTotal(BigDecimal.valueOf(600))
                .itens(List.of(reservaItem))
                .build();

        ResumoReservaDto dto = resumoMapper.toDTO(resumo);

        assertThat(dto.getIdNotaFiscal()).isEqualTo(1L);
        assertThat(dto.getNumero()).isEqualTo("NF-001");
        assertThat(dto.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(600));
        assertThat(dto.getItens()).hasSize(1);
        assertThat(dto.getCliente().getId()).isEqualTo(1L);
    }
}
