package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    @Query("SELECT u "
            + "FROM Usuario u "
            + "WHERE u.nome = :nome ")
    Usuario buscarPor(String nome);
}
