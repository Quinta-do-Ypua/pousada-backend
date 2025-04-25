package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.service.BaseService;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, ID> {

    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @PostMapping
    public T salvar(@RequestBody T t) {
        return baseService.salvar(t);
    }

    @GetMapping("/{id}")
    public T buscarPorId(@PathVariable(name = "id") ID id) {
        return baseService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public T deletarPorId(@PathVariable(name = "id") ID id) {
        return baseService.excluir(id);
    }

    @PutMapping
    public T alterarPorId(@RequestBody T t) {
        return baseService.alterar(t);
    }

}