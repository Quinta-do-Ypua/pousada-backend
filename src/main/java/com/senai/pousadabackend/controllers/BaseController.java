package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, ID> {

    private final BaseServiceInterface<T, ID> baseServiceInterface;

    public BaseController(BaseServiceInterface<T, ID> baseServiceInterface) {
        this.baseServiceInterface = baseServiceInterface;
    }

    @PostMapping
    public T salvar(@RequestBody T t) {
        return baseServiceInterface.salvar(t);
    }

    @GetMapping("/{id}")
    public T buscarPorId(@PathVariable(name = "id") ID id) {
        return baseServiceInterface.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public T deletarPorId(@PathVariable(name = "id") ID id) {
        return baseServiceInterface.excluir(id);
    }

    @PutMapping
    public T alterarPorId(@RequestBody T t) {
        return baseServiceInterface.alterar(t);
    }

}