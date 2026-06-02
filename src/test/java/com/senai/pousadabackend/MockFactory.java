package com.senai.pousadabackend;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.core.enums.TipoPagamento;
import com.senai.pousadabackend.domain.amenidade.Amenidade;
import com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamento;
import com.senai.pousadabackend.domain.meioPagamento.dto.MeioPagamentoDTO;
import com.senai.pousadabackend.domain.parametro.ParametroReserva;
import com.senai.pousadabackend.domain.parametro.dto.ParametroReservaDTO;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import com.senai.pousadabackend.domain.resumo.ResumoReserva;
import com.senai.pousadabackend.domain.resumo.dto.ResumoReservaDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MockFactory {

    public Amenidade novaAmenidade() {
        return Amenidade.builder().nome("Piscina").build();
    }

    public Amenidade amenidadeExistente() {
        Amenidade a = Amenidade.builder().id(1L).nome("Piscina").build();
        a.setDataCriacao(LocalDateTime.now());
        return a;
    }

    public Amenidade amenidadeComIdENome(Long id, String nome) {
        Amenidade a = Amenidade.builder().id(id).nome(nome).build();
        a.setDataCriacao(LocalDateTime.now());
        return a;
    }

    public AmenidadeDto amenidadeDto() {
        return AmenidadeDto.builder()
                .id(1L)
                .nome("Piscina")
                .icone("pool")
                .build();
    }

    public AmenidadeDto amenidadeDtoSemNome() {
        return AmenidadeDto.builder().build();
    }

    public AmenidadeDto amenidadeDtoComNome(String nome) {
        return AmenidadeDto.builder().nome(nome).build();
    }

    public Cliente clientePadrao() {
        Cliente c = Cliente.builder()
                .id(1L)
                .nome("Maria")
                .email("maria@email.com")
                .cpf("123.456.789-09")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(Endereco.builder().id(1L).cidade("SP").estado("SP").rua("Rua A").build())
                .build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public ClienteDTO clienteDTO() {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .cidade("SP")
                .estado("SP")
                .rua("Rua A")
                .build();
        return ClienteDTO.builder()
                .id(1L)
                .nome("Maria")
                .email("maria@email.com")
                .cpf("123.456.789-09")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(enderecoDTO)
                .build();
    }

    public ClienteDTO clienteDTOSemNome() {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .cidade("SP")
                .estado("SP")
                .rua("Rua A")
                .build();
        return ClienteDTO.builder()
                .email("maria@email.com")
                .cpf("123.456.789-09")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(enderecoDTO)
                .build();
    }

    public ClienteDTO clienteDTOComNome(String nome) {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .cidade("SP")
                .estado("SP")
                .rua("Rua A")
                .build();
        return ClienteDTO.builder()
                .nome(nome)
                .email("maria@email.com")
                .cpf("123.456.789-09")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(enderecoDTO)
                .build();
    }

    public Cliente clienteComIdENome(Long id, String nome) {
        Cliente c = Cliente.builder()
                .id(id)
                .nome(nome)
                .email("maria@email.com")
                .cpf("123.456.789-09")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(Endereco.builder().id(id).cidade("SP").estado("SP").rua("Rua A").build())
                .build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public Complemento novoComplemento() {
        return Complemento.builder().nome("Café da manhã").valor(new BigDecimal("50.00")).descricao("Café completo").build();
    }

    public Complemento complementoExistente() {
        Complemento c = Complemento.builder().id(1L).nome("Café da manhã").valor(new BigDecimal("50.00")).descricao("Café completo").build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public Complemento complementoComIdENome(Long id, String nome) {
        Complemento c = Complemento.builder().id(id).nome(nome).valor(new BigDecimal("50.00")).descricao("Descrição").build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public ComplementoDTO complementoDTO() {
        return ComplementoDTO.builder().id(1L).nome("Café da manhã").valor(new BigDecimal("50.00")).descricao("Café completo").build();
    }

    public ComplementoDTO complementoDTOSemNome() {
        return ComplementoDTO.builder().valor(new BigDecimal("50.00")).descricao("Descrição").build();
    }

    public ComplementoDTO complementoDTOComNome(String nome) {
        return ComplementoDTO.builder().nome(nome).valor(new BigDecimal("50.00")).descricao("Descrição").build();
    }

    public Cupom novoCupom() {
        return Cupom.builder()
                .codigo("DESC10")
                .nome("Desconto 10%")
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    public Cupom cupomExistente() {
        Cupom c = Cupom.builder()
                .id(1L)
                .codigo("DESC10")
                .nome("Desconto 10%")
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public Cupom cupomComIdENome(Long id, String nome) {
        Cupom c = Cupom.builder()
                .id(id)
                .codigo("DESC10")
                .nome(nome)
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    public CupomDTO cupomDTO() {
        return CupomDTO.builder()
                .id(1L)
                .codigo("DESC10")
                .nome("Desconto 10%")
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    public CupomDTO cupomDTOSemNome() {
        return CupomDTO.builder()
                .codigo("DESC10")
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    public CupomDTO cupomDTOComNome(String nome) {
        return CupomDTO.builder()
                .nome(nome)
                .codigo("DESC10")
                .dataDeInicio(LocalDate.now())
                .dataDeVencimento(LocalDate.now().plusMonths(1))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    public Endereco novoEndereco() {
        return Endereco.builder()
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
    }

    public Endereco enderecoExistente() {
        Endereco e = Endereco.builder()
                .id(1L)
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
        e.setDataCriacao(LocalDateTime.now());
        return e;
    }

    public Endereco enderecoComIdECidade(Long id, String cidade) {
        Endereco e = Endereco.builder()
                .id(id)
                .cidade(cidade)
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
        e.setDataCriacao(LocalDateTime.now());
        return e;
    }

    public EnderecoDTO enderecoDTO() {
        return EnderecoDTO.builder()
                .id(1L)
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
    }

    public EnderecoDTO enderecoDTOSemCidade() {
        return EnderecoDTO.builder()
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
    }

    public EnderecoDTO enderecoDTOComCidade(String cidade) {
        return EnderecoDTO.builder()
                .cidade(cidade)
                .estado("SP")
                .rua("Rua A")
                .cep("01234-567")
                .bairro("Centro")
                .numero("123")
                .complemento("Apto 1")
                .build();
    }

    public Quarto quartoPadrao() {
        Quarto q = new Quarto();
        q.setId(1L);
        q.setNome("Suite 01");
        return q;
    }

    public ImagemQuarto imagemQuartoPadrao() {
        return ImagemQuarto.builder()
                .id(1L)
                .fileId("file-id-123")
                .url("http://minio/foto.jpg")
                .quarto(quartoPadrao())
                .build();
    }

    public ImagemQuarto imagemQuartoSemFileId() {
        return ImagemQuarto.builder()
                .id(1L)
                .url("http://url")
                .build();
    }

    public ImagemQuarto imagemQuartoSemUrl() {
        return ImagemQuarto.builder()
                .id(1L)
                .fileId("file-id")
                .build();
    }

    public ResultadoUploadDTO resultadoUploadDTO() {
        return ResultadoUploadDTO.builder()
                .objectName("file-id-123")
                .url("http://minio/foto.jpg")
                .build();
    }

    public Quarto novoQuarto() {
        Quarto q = new Quarto();
        q.setNome("Suite 02");
        q.setCapacidade(2);
        q.setQtdCamaSolteiro(0);
        q.setQtdCamaCasal(1);
        q.setValorDiaria(new BigDecimal("200.00"));
        q.setObservacao("Vista para o mar");
        return q;
    }

    public Quarto quartoExistente() {
        Quarto q = new Quarto();
        q.setId(1L);
        q.setNome("Suite 01");
        q.setCapacidade(2);
        q.setQtdCamaSolteiro(0);
        q.setQtdCamaCasal(1);
        q.setValorDiaria(new BigDecimal("200.00"));
        q.setObservacao("Vista para o mar");
        q.setDataCriacao(LocalDateTime.now());
        return q;
    }

    public Quarto quartoComIdENome(Long id, String nome) {
        Quarto q = new Quarto();
        q.setId(id);
        q.setNome(nome);
        q.setCapacidade(2);
        q.setQtdCamaSolteiro(0);
        q.setQtdCamaCasal(1);
        q.setValorDiaria(new BigDecimal("200.00"));
        q.setObservacao("Vista para o mar");
        q.setDataCriacao(LocalDateTime.now());
        return q;
    }

    public QuartoDTO quartoDTO() {
        return QuartoDTO.builder()
                .id(1L)
                .nome("Suite 01")
                .capacidade(2)
                .qtdCamaSolteiro(0)
                .qtdCamaCasal(1)
                .valorDiaria(new BigDecimal("200.00"))
                .observacao("Vista para o mar")
                .build();
    }

    public QuartoDTO quartoDTOSemNome() {
        return QuartoDTO.builder()
                .capacidade(2)
                .qtdCamaSolteiro(0)
                .qtdCamaCasal(1)
                .valorDiaria(new BigDecimal("200.00"))
                .observacao("Vista para o mar")
                .build();
    }

    public QuartoDTO quartoDTOComNome(String nome) {
        return QuartoDTO.builder()
                .nome(nome)
                .capacidade(2)
                .qtdCamaSolteiro(0)
                .qtdCamaCasal(1)
                .valorDiaria(new BigDecimal("200.00"))
                .observacao("Vista para o mar")
                .build();
    }

    public MeioPagamento novoMeioPagamento() {
        return MeioPagamento.builder()
                .nome("Cartão de Crédito")
                .descricao("Pagamento com cartão")
                .tipo(TipoPagamento.CREDITO)
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
    }

    public MeioPagamento meioPagamentoExistente() {
        MeioPagamento m = MeioPagamento.builder()
                .id(1L)
                .nome("Cartão de Crédito")
                .descricao("Pagamento com cartão")
                .tipo(TipoPagamento.CREDITO)
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
        m.setDataCriacao(LocalDateTime.now());
        return m;
    }

    public MeioPagamento meioPagamentoComIdENome(Long id, String nome) {
        MeioPagamento m = MeioPagamento.builder()
                .id(id)
                .nome(nome)
                .descricao("Pagamento com cartão")
                .tipo(TipoPagamento.CREDITO)
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
        m.setDataCriacao(LocalDateTime.now());
        return m;
    }

    public MeioPagamentoDTO meioPagamentoDTO() {
        return MeioPagamentoDTO.builder()
                .id(1L)
                .nome("Cartão de Crédito")
                .descricao("Pagamento com cartão")
                .tipo("CREDITO")
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
    }

    public MeioPagamentoDTO meioPagamentoDTOSemNome() {
        return MeioPagamentoDTO.builder()
                .descricao("Pagamento com cartão")
                .tipo("CREDITO")
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
    }

    public MeioPagamentoDTO meioPagamentoDTOComNome(String nome) {
        return MeioPagamentoDTO.builder()
                .nome(nome)
                .descricao("Pagamento com cartão")
                .tipo("CREDITO")
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(new BigDecimal("0.50"))
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();
    }

    public ParametroReserva novoParametroReserva() {
        return ParametroReserva.builder()
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(7)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("50.00"))
                .build();
    }

    public ParametroReserva parametroReservaExistente() {
        ParametroReserva p = ParametroReserva.builder()
                .id(1L)
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(7)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("50.00"))
                .build();
        p.setDataCriacao(LocalDateTime.now());
        return p;
    }

    public ParametroReserva parametroReservaComId(Long id) {
        ParametroReserva p = ParametroReserva.builder()
                .id(id)
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(7)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("50.00"))
                .build();
        p.setDataCriacao(LocalDateTime.now());
        return p;
    }

    public ParametroReservaDTO parametroReservaDTO() {
        return ParametroReservaDTO.builder()
                .id(1L)
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(7)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("50.00"))
                .build();
    }

    public ParametroReservaDTO parametroReservaDTOSemId() {
        return ParametroReservaDTO.builder()
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(7)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("50.00"))
                .build();
    }

    public Reserva novaReserva() {
        return Reserva.builder()
                .quarto(quartoPadrao())
                .cliente(clientePadrao())
                .valorDaReserva(new BigDecimal("300.00"))
                .statusDaReserva(com.senai.pousadabackend.core.enums.StatusDaReserva.ABERTA)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .observacao("Reserva teste")
                .build();
    }

    public Reserva reservaExistente() {
        Reserva r = Reserva.builder()
                .id(1L)
                .quarto(quartoPadrao())
                .cliente(clientePadrao())
                .valorDaReserva(new BigDecimal("300.00"))
                .statusDaReserva(com.senai.pousadabackend.core.enums.StatusDaReserva.ABERTA)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .observacao("Reserva teste")
                .build();
        r.setDataCriacao(LocalDateTime.now());
        return r;
    }

    public Reserva reservaComId(Long id) {
        Reserva r = Reserva.builder()
                .id(id)
                .quarto(quartoPadrao())
                .cliente(clientePadrao())
                .valorDaReserva(new BigDecimal("300.00"))
                .statusDaReserva(com.senai.pousadabackend.core.enums.StatusDaReserva.ABERTA)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .observacao("Reserva teste")
                .build();
        r.setDataCriacao(LocalDateTime.now());
        return r;
    }

    public ReservaDTO reservaDTO() {
        return ReservaDTO.builder()
                .id(1L)
                .quarto(quartoDTO())
                .cliente(clienteDTO())
                .valorDaReserva(new BigDecimal("300.00"))
                .statusDaReserva(com.senai.pousadabackend.core.enums.StatusDaReserva.ABERTA)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .observacao("Reserva teste")
                .build();
    }

    public ReservaResumidaDto reservaResumidaDto() {
        return ReservaResumidaDto.builder()
                .quartoId(1L)
                .clienteId(1L)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .build();
    }

    public ResumoReserva novoResumoReserva() {
        return ResumoReserva.builder()
                .numero("NF-123456")
                .dataCadastro(LocalDate.now())
                .cliente(clientePadrao())
                .valorTotal(new BigDecimal("500.00"))
                .build();
    }

    public ResumoReserva resumoReservaExistente() {
        ResumoReserva r = ResumoReserva.builder()
                .idNotaFiscal(1L)
                .numero("NF-123456")
                .dataCadastro(LocalDate.now())
                .cliente(clientePadrao())
                .valorTotal(new BigDecimal("500.00"))
                .build();
        r.setDataCriacao(LocalDateTime.now());
        return r;
    }

    public ResumoReserva resumoReservaComId(Long id) {
        ResumoReserva r = ResumoReserva.builder()
                .idNotaFiscal(id)
                .numero("NF-123456")
                .dataCadastro(LocalDate.now())
                .cliente(clientePadrao())
                .valorTotal(new BigDecimal("500.00"))
                .build();
        r.setDataCriacao(LocalDateTime.now());
        return r;
    }

    public ResumoReservaDto resumoReservaDto() {
        return ResumoReservaDto.builder()
                .idNotaFiscal(1L)
                .numero("NF-123456")
                .dataCadastro(LocalDate.now())
                .cliente(clienteDTO())
                .valorTotal(new BigDecimal("500.00"))
                .itens(List.of())
                .build();
    }
}
