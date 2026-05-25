package com.senai.pousadabackend.domain.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@Import({ClienteMapper.class, EnderecoMapper.class})
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private ClienteDTO dto;

    @BeforeEach
    void setUp() {
        com.senai.pousadabackend.domain.endereco.Endereco endereco =
                com.senai.pousadabackend.domain.endereco.Endereco.builder()
                        .id(1L)
                        .cidade("São Paulo")
                        .estado("SP")
                        .rua("Rua A")
                        .cep("01000-000")
                        .bairro("Centro")
                        .numero("10")
                        .build();

        cliente = Cliente.builder()
                .id(1L)
                .nome("Ana Silva")
                .cpf("529.982.247-25")
                .email("ana@test.com")
                .celular("(11) 91234-5678")
                .sexo(Sexo.FEMININO)
                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                .endereco(endereco)
                .build();
        cliente.setDataCriacao(LocalDateTime.now());

        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua A")
                .cep("01000-000")
                .build();

        dto = ClienteDTO.builder()
                .id(1L)
                .nome("Ana Silva")
                .cpf("529.982.247-25")
                .email("ana@test.com")
                .celular("(11) 91234-5678")
                .sexo(Sexo.FEMININO)
                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                .endereco(enderecoDTO)
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(clienteService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cliente)));

        mockMvc.perform(get("/clientes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cliente-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Ana Silva"));
    }

    @Test
    void listar_comRoleAdmin_retorna200() throws Exception {
        when(clienteService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cliente)));

        mockMvc.perform(get("/clientes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ana Silva"));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(clienteService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(get("/clientes/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_comRoleAdmin_retorna200() throws Exception {
        when(clienteService.excluir(1L)).thenReturn(cliente);

        mockMvc.perform(delete("/clientes/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deletar_naoEncontrado_retorna404() throws Exception {
        when(clienteService.excluir(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        mockMvc.perform(delete("/clientes/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(clienteService.buscarPorSpecification(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cliente)));

        mockMvc.perform(get("/clientes")
                        .param("search", "nome==Ana*")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Ana Silva"));
    }
}
