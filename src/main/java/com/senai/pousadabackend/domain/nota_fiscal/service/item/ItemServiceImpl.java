package com.senai.pousadabackend.domain.nota_fiscal.service.item;

import com.senai.pousadabackend.domain.nota_fiscal.item.Item;
import com.senai.pousadabackend.domain.nota_fiscal.item.ItemRepository;
import com.senai.pousadabackend.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseService<Item, Long, ItemRepository> implements ItemService {

    public ItemServiceImpl(ItemRepository repo) {
        super(repo);
    }

}
