package com.senai.pousadabackend.service.nota_fiscal.item;

import com.senai.pousadabackend.entity.Item;
import com.senai.pousadabackend.repository.ItemRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseService<Item, Long, ItemRepository> implements ItemService {

    public ItemServiceImpl(ItemRepository repo) {
        super(repo);
    }

}
