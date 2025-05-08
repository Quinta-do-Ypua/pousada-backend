package com.senai.pousadabackend.mappers;

public interface BaseMapper<T, DTO> {

    DTO toDTO(T t);

    T toEntity(DTO dto);
}
