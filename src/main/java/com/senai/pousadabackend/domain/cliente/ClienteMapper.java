package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.BaseMapper;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements BaseMapper<Cliente, ClienteDTO> {

    private final EnderecoMapper enderecoMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        var endereco = cliente.getEndereco();
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .sexo(cliente.getSexo())
                .celular(cliente.getCelular())
                .dataDeNascimento(cliente.getDataDeNascimento())
                .endereco(enderecoMapper.toDTO(endereco))
                .build();
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .id(clienteDTO.getId())
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .sexo(clienteDTO.getSexo())
                .celular(clienteDTO.getCelular())
                .dataDeNascimento(clienteDTO.getDataDeNascimento())
                .endereco(clienteDTO.getEndereco() != null ? enderecoMapper.toEntity(clienteDTO.getEndereco()) : null)
                .build();
    }

}
