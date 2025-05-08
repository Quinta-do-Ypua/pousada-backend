package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.ClienteDTO;
import com.senai.pousadabackend.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements BaseMapper<Cliente, ClienteDTO> {


    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .sexo(cliente.getSexo())
                .celular(cliente.getCelular())
                .endereco(cliente.getEndereco())
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
                .endereco(clienteDTO.getEndereco())
                .build();
    }
}
