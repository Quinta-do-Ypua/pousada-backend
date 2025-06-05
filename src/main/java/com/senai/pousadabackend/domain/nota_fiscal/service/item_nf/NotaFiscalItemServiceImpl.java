package com.senai.pousadabackend.domain.nota_fiscal.service.item_nf;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.nota_fiscal.item_nf.NotaFiscalItem;
import com.senai.pousadabackend.domain.nota_fiscal.item_nf.NotaFiscalItemRepository;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalItemServiceImpl extends BaseService<NotaFiscalItem, Long, NotaFiscalItemRepository> implements NotaFiscalItemService {

    public NotaFiscalItemServiceImpl(NotaFiscalItemRepository repo) {
        super(repo);
    }

}
