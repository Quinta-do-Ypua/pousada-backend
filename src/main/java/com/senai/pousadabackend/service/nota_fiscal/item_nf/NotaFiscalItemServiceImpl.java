package com.senai.pousadabackend.service.nota_fiscal.item_nf;

import com.senai.pousadabackend.entity.NotaFiscalItem;
import com.senai.pousadabackend.repository.NotaFiscalItemRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalItemServiceImpl extends BaseService<NotaFiscalItem, Long, NotaFiscalItemRepository> implements NotaFiscalItemService {

    public NotaFiscalItemServiceImpl(NotaFiscalItemRepository repo) {
        super(repo);
    }

}
