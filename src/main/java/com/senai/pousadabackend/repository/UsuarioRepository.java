package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
}
