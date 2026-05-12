package com.senai.pousadabackend.domain.resumo.itemnf;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ResumoReservaItemService extends BaseService<ResumoReservaItem, Long, ResumoReservaItemRepository> {

    public ResumoReservaItemService(ResumoReservaItemRepository repo) {
        super(repo);
    }

}
