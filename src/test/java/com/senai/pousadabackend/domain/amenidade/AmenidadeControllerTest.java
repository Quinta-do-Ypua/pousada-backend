package com.senai.pousadabackend.domain.amenidade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AmenidadeController.class)
@Import(AmenidadeMapper.class)
class AmenidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AmenidadeService amenidadeService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Amenidade amenidade;
    private AmenidadeDto dto;

    @BeforeEach
    void setUp() {
        amenidade = Amenidade.builder()
                .id(1L)
                .nome("Piscina")
                .icone("pool")
                .build();
        amenidade.setDataCriacao(LocalDateTime.now());

        dto = AmenidadeDto.builder()
                .id(1L)
                .nome("Piscina")
                .icone("pool")
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/amenidades"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(amenidadeService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(amenidade)));

        mockMvc.perform(get("/amenidades")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Piscina"))
                .andExpect(jsonPath("$.content[0].icone").value("pool"));
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(amenidadeService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(amenidade)));

        mockMvc.perform(get("/amenidades")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(amenidadeService.buscarPorId(1L)).thenReturn(amenidade);

        mockMvc.perform(get("/amenidades/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Piscina"));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(amenidadeService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/amenidades/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Não encontrado"));
    }

    @Test
    void salvar_comRoleOperacao_retorna200() throws Exception {
        AmenidadeDto novaDto = AmenidadeDto.builder().nome("Academia").build();
        Amenidade nova = Amenidade.builder().id(2L).nome("Academia").build();
        nova.setDataCriacao(LocalDateTime.now());

        when(amenidadeService.salvar(any(Amenidade.class))).thenReturn(nova);

        mockMvc.perform(post("/amenidades")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Academia"));
    }

    @Test
    void salvar_semNome_retorna400() throws Exception {
        AmenidadeDto semNome = AmenidadeDto.builder().build();

        mockMvc.perform(post("/amenidades")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(semNome)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void alterar_comRoleOperacao_retorna200() throws Exception {
        when(amenidadeService.salvar(any(Amenidade.class))).thenReturn(amenidade);

        mockMvc.perform(put("/amenidades")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(amenidadeService.excluir(1L)).thenReturn(amenidade);

        mockMvc.perform(delete("/amenidades/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deletar_naoEncontrado_retorna404() throws Exception {
        when(amenidadeService.excluir(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(delete("/amenidades/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(amenidadeService.buscarPorSpecification(eq("nome==Piscina"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(amenidade)));

        mockMvc.perform(get("/amenidades")
                        .param("search", "nome==Piscina")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Piscina"));
    }
}
