package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Cliente;

import java.util.Optional;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCelular(String celular);


}
