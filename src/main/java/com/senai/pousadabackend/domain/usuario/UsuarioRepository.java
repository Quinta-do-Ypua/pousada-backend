package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    @Query("SELECT u "
            + "FROM Usuario u "
            + "WHERE u.nome = :nome "
            + "AND u.status != 'INATIVO' ")
    Usuario buscarPor(String nome);
}
