package com.senai.pousadabackend.domain.complemento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
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

@WebMvcTest(ComplementoController.class)
@Import(ComplementoMapper.class)
class ComplementoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComplementoService complementoService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Complemento complemento;
    private ComplementoDTO dto;

    @BeforeEach
    void setUp() {
        complemento = Complemento.builder()
                .id(1L)
                .nome("Café da manhã")
                .valor(BigDecimal.valueOf(35))
                .descricao("Café da manhã completo com frutas")
                .build();
        complemento.setDataCriacao(LocalDateTime.now());

        dto = ComplementoDTO.builder()
                .id(1L)
                .nome("Café da manhã")
                .valor(BigDecimal.valueOf(35))
                .descricao("Café da manhã completo com frutas")
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/complementos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(complementoService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(complemento)));

        mockMvc.perform(get("/complementos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_complemento-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Café da manhã"));
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(complementoService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(complemento)));

        mockMvc.perform(get("/complementos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(complementoService.buscarPorId(1L)).thenReturn(complemento);

        mockMvc.perform(get("/complementos/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Café da manhã"));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(complementoService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/complementos/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvar_semNome_retorna400() throws Exception {
        ComplementoDTO semNome = ComplementoDTO.builder()
                .valor(BigDecimal.valueOf(35))
                .descricao("Descrição")
                .build();

        mockMvc.perform(post("/complementos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(semNome)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(complementoService.excluir(1L)).thenReturn(complemento);

        mockMvc.perform(delete("/complementos/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deletar_naoEncontrado_retorna404() throws Exception {
        when(complementoService.excluir(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(delete("/complementos/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(complementoService.buscarPorSpecification(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(complemento)));

        mockMvc.perform(get("/complementos")
                        .param("search", "nome==Café*")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }
}
