package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.BaseMapper;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.EnderecoDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements BaseMapper<Cliente, ClienteDTO> {


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
                .endereco(EnderecoDTO.builder()
                        .id(endereco.getId())
                        .cidade(endereco.getCidade())
                        .estado(endereco.getEstado())
                        .rua(endereco.getRua())
                        .cep(endereco.getCep())
                        .bairro(endereco.getBairro())
                        .numero(endereco.getNumero())
                        .complemento(endereco.getComplemento())
                        .build())
                .build();
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        Endereco endereco = Endereco.builder()
                .id(clienteDTO.getEndereco().getId())
                .cidade(clienteDTO.getEndereco().getCidade())
                .estado(clienteDTO.getEndereco().getEstado())
                .rua(clienteDTO.getEndereco().getRua())
                .cep(clienteDTO.getEndereco().getCep())
                .bairro(clienteDTO.getEndereco().getBairro())
                .numero(clienteDTO.getEndereco().getNumero())
                .complemento(clienteDTO.getEndereco().getComplemento())
                .build();

        return Cliente.builder()
                .id(clienteDTO.getId())
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .sexo(clienteDTO.getSexo())
                .celular(clienteDTO.getCelular())
                .dataDeNascimento(clienteDTO.getDataDeNascimento())
                .endereco(endereco)
                .build();
    }

}
