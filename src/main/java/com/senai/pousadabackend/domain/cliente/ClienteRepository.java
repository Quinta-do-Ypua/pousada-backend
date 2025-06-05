package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.repository.BaseRepository;

import java.util.Optional;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCelular(String celular);


}
