package com.senai.pousadabackend.core.base;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, DTO, ID, Mapper extends BaseMapper<T, DTO>> {

    private final Mapper mapper;

    private final BaseServiceInterface<T, ID> baseServiceInterface;

    private static final int PAGE_SIZE = 15;

    public BaseController(Mapper mapper, BaseServiceInterface<T, ID> baseServiceInterface) {
        this.mapper = mapper;
        this.baseServiceInterface = baseServiceInterface;
    }

    @PostMapping
    @Transactional
    public DTO salvar(@Validated(GrupoValidacaoInserir.class) @RequestBody DTO dto) {
        return mapper.toDTO(baseServiceInterface.salvar(mapper.toEntity(dto)));
    }

    @GetMapping("/{id}")
    @Transactional
    public DTO buscarPorId(@PathVariable(name = "id") ID id) {
        return mapper.toDTO(baseServiceInterface.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public DTO deletarPorId(@PathVariable(name = "id") ID id) {
        T entidade = baseServiceInterface.buscarPorId(id);
        DTO dto = mapper.toDTO(entidade);
        baseServiceInterface.excluir(id);
        return dto;
    }

    @PutMapping
    @Transactional
    public DTO alterarPorId(@Validated(GrupoValidacaoAlterar.class) @RequestBody DTO dto) {
        return mapper.toDTO(baseServiceInterface.atualizar(mapper.toEntity(dto)));
    }

    @GetMapping(params = "search")
    @Transactional
    public Page<DTO> buscarPorSpecification(@RequestParam(name = "search") String search, Pageable pageable) {
        return baseServiceInterface.buscarPorSpecification(search, pageable).map(mapper::toDTO);
    }

    @GetMapping
    @Transactional
    public Page<DTO> listarPaginado(@PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        return baseServiceInterface.listarPaginado(pageable).map(mapper::toDTO);
    }

}