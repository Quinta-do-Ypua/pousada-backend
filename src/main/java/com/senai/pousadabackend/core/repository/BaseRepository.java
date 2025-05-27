package com.senai.pousadabackend.core.repository;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.core.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface  BaseRepository<T extends EntityAudit, ID>
        extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {

    Page<T> findByStatus(Status status, Pageable pageable);

}
