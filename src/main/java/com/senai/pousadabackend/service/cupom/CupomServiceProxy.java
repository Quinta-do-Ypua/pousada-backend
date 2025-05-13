package com.senai.pousadabackend.service.cupom;


import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.repository.CupomRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CupomServiceProxy extends BaseService<Cupom, Long, CupomRepository>  implements CupomService {

    public CupomServiceProxy(CupomRepository repo) {
        super(repo);
    }

}
