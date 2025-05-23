package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.mappers.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper implements BaseMapper<Endereco, EnderecoDTO> {

    @Override
    public EnderecoDTO toDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cidade(endereco.getCidade())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .bairro(endereco.getBairro())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .build();
    }

    @Override
    public Endereco toEntity(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .id(enderecoDTO.getId())
                .cidade(enderecoDTO.getCidade())
                .numero(enderecoDTO.getNumero())
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .bairro(enderecoDTO.getBairro())
                .estado(enderecoDTO.getEstado())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }
}
