package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.repository.BaseRepository;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
    boolean existsByCelular(String celular);

}
