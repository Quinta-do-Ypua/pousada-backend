package com.senai.pousadabackend.core.base;

import com.senai.pousadabackend.core.entity.EntityAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface  BaseRepository<T extends EntityAudit, ID>
        extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {

}
