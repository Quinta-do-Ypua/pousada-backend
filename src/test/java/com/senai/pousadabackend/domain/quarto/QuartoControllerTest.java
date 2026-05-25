package com.senai.pousadabackend.domain.quarto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.amenidade.AmenidadeMapper;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuartoController.class)
@Import({QuartoMapper.class, AmenidadeMapper.class})
class QuartoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuartoService quartoService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Quarto quarto;
    private QuartoDTO dto;

    @BeforeEach
    void setUp() {
        quarto = Quarto.builder()
                .id(1L)
                .nome("Suite Master")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(200))
                .observacao("Vista para o jardim")
                .build();
        quarto.setDataCriacao(LocalDateTime.now());

        dto = QuartoDTO.builder()
                .id(1L)
                .nome("Suite Master")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(200))
                .observacao("Vista para o jardim")
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/quartos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(quartoService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(quarto)));

        mockMvc.perform(get("/quartos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Suite Master"));
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(quartoService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(quarto)));

        mockMvc.perform(get("/quartos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(quartoService.buscarPorId(1L)).thenReturn(quarto);

        mockMvc.perform(get("/quartos/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Suite Master"))
                .andExpect(jsonPath("$.capacidade").value(2));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(quartoService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/quartos/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvar_comRoleOperacao_retorna200() throws Exception {
        QuartoDTO novoDto = QuartoDTO.builder()
                .nome("Quarto Econômico")
                .capacidade(1)
                .valorDiaria(BigDecimal.valueOf(100))
                .build();
        Quarto novo = Quarto.builder()
                .id(2L)
                .nome("Quarto Econômico")
                .capacidade(1)
                .valorDiaria(BigDecimal.valueOf(100))
                .build();
        novo.setDataCriacao(LocalDateTime.now());

        when(quartoService.salvar(any(Quarto.class))).thenReturn(novo);

        mockMvc.perform(post("/quartos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Quarto Econômico"));
    }

    @Test
    void alterar_comRoleOperacao_retorna200() throws Exception {
        when(quartoService.atualizar(any(Quarto.class))).thenReturn(quarto);

        mockMvc.perform(put("/quartos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(quartoService.excluir(1L)).thenReturn(quarto);

        mockMvc.perform(delete("/quartos/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(quartoService.buscarPorSpecification(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(quarto)));

        mockMvc.perform(get("/quartos")
                        .param("search", "nome==Suite*")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Suite Master"));
    }
}
