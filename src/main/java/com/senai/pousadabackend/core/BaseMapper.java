package com.senai.pousadabackend.core;

public interface BaseMapper<T, DTO> {

    DTO toDTO(T t);

    T toEntity(DTO dto);
}
