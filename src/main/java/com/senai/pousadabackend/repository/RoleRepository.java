package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.domain.role.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
}
