package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    Usuario findByNome(String nome);
}
