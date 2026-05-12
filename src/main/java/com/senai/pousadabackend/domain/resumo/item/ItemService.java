package com.senai.pousadabackend.domain.resumo.item;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends BaseService<Item, Long, ItemRepository> {

    public ItemService(ItemRepository repo) {
        super(repo);
    }

}
