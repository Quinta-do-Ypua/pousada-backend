package com.senai.pousadabackend.domain.tema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.tema.dto.TemaDTO;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TemaController.class)
@Import(TemaMapper.class)
class TemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TemaService temaService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Tema tema;
    private TemaDTO dto;

    @BeforeEach
    void setUp() {
        tema = Tema.builder()
                .id(1L)
                .primaryColor("#8B4513")
                .secondaryColor("#501313")
                .textColor("#555555")
                .grayBg("#EEEEEE")
                .graySecondaryBg("#F1F3F6")
                .logo("http://example.com/logo.png")
                .loginImage("http://example.com/login.png")
                .build();
        tema.setDataCriacao(LocalDateTime.now());

        dto = TemaDTO.builder()
                .id(1L)
                .primaryColor("#8B4513")
                .secondaryColor("#501313")
                .textColor("#555555")
                .grayBg("#EEEEEE")
                .graySecondaryBg("#F1F3F6")
                .logo("http://example.com/logo.png")
                .loginImage("http://example.com/login.png")
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/tema"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(temaService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(tema)));

        mockMvc.perform(get("/tema")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].primaryColor").value("#8B4513"));
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(temaService.buscarPorId(1L)).thenReturn(tema);

        mockMvc.perform(get("/tema/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(temaService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/tema/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvar_comRoleOperacao_retorna200() throws Exception {
        TemaDTO novoDto = TemaDTO.builder()
                .primaryColor("#FF0000")
                .secondaryColor("#00FF00")
                .textColor("#000000")
                .grayBg("#CCCCCC")
                .graySecondaryBg("#DDDDDD")
                .build();
        Tema novo = Tema.builder()
                .id(2L)
                .primaryColor("#FF0000")
                .secondaryColor("#00FF00")
                .textColor("#000000")
                .grayBg("#CCCCCC")
                .graySecondaryBg("#DDDDDD")
                .build();
        novo.setDataCriacao(LocalDateTime.now());

        when(temaService.salvar(any(Tema.class))).thenReturn(novo);

        mockMvc.perform(post("/tema")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_configuracao-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(temaService.excluir(1L)).thenReturn(tema);

        mockMvc.perform(delete("/tema/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void alterar_comRoleOperacao_retorna200() throws Exception {
        when(temaService.atualizar(any(Tema.class))).thenReturn(tema);

        mockMvc.perform(put("/tema")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_configuracao-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
