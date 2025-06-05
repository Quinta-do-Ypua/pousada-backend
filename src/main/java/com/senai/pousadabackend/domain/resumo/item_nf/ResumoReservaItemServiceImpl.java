package com.senai.pousadabackend.domain.resumo.item_nf;

import com.senai.pousadabackend.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ResumoReservaItemServiceImpl extends BaseService<ResumoReservaItem, Long, ResumoReservaItemRepository> implements ResumoReservaItemService {

    public ResumoReservaItemServiceImpl(ResumoReservaItemRepository repo) {
        super(repo);
    }

}
