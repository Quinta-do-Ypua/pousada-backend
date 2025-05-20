package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Cliente;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
    boolean existsByCelular(String celular);

}
