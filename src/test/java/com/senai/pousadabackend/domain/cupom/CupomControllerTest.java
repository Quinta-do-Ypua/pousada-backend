package com.senai.pousadabackend.domain.cupom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CupomController.class)
@Import(CupomMapper.class)
class CupomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CupomService cupomService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Cupom cupom;
    private CupomDTO dto;

    @BeforeEach
    void setUp() {
        cupom = Cupom.builder()
                .id(1L)
                .codigo("PROMO10")
                .nome("Promoção 10%")
                .dataDeInicio(LocalDate.now().plusDays(1))
                .dataDeVencimento(LocalDate.now().plusDays(30))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
        cupom.setDataCriacao(LocalDateTime.now());

        dto = CupomDTO.builder()
                .id(1L)
                .codigo("PROMO10")
                .nome("Promoção 10%")
                .dataDeInicio(LocalDate.now().plusDays(1))
                .dataDeVencimento(LocalDate.now().plusDays(30))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/cupons"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(cupomService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cupom)));

        mockMvc.perform(get("/cupons")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cupom-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].codigo").value("PROMO10"));
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(cupomService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cupom)));

        mockMvc.perform(get("/cupons")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(cupomService.buscarPorId(1L)).thenReturn(cupom);

        mockMvc.perform(get("/cupons/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("PROMO10"))
                .andExpect(jsonPath("$.nome").value("Promoção 10%"));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(cupomService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/cupons/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(cupomService.excluir(1L)).thenReturn(cupom);

        mockMvc.perform(delete("/cupons/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deletar_naoEncontrado_retorna404() throws Exception {
        when(cupomService.excluir(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(delete("/cupons/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(cupomService.buscarPorSpecification(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cupom)));

        mockMvc.perform(get("/cupons")
                        .param("search", "codigo==PROMO10")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }
}
