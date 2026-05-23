package com.senai.pousadabackend.core.base;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
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
    public DTO salvar(@Validated(GrupoValidacaoInserir.class) @RequestBody DTO dto) {
        return mapper.toDTO(baseServiceInterface.salvar(mapper.toEntity(dto)));
    }

    @GetMapping("/{id}")
    public DTO buscarPorId(@PathVariable(name = "id") ID id) {
        return mapper.toDTO(baseServiceInterface.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public DTO deletarPorId(@PathVariable(name = "id") ID id) {
        return mapper.toDTO(baseServiceInterface.excluir(id));
    }

    @PutMapping
    public DTO alterarPorId(@Validated(GrupoValidacaoAlterar.class) @RequestBody DTO dto) {
        return mapper.toDTO(baseServiceInterface.atualizar(mapper.toEntity(dto)));
    }

    @GetMapping(params = "search")
    public Page<DTO> buscarPorSpecification(@RequestParam(name = "search") String search, Pageable pageable) {
        return baseServiceInterface.buscarPorSpecification(search, pageable).map(mapper::toDTO);
    }

    @GetMapping
    public Page<DTO> listarPaginado(@PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        return baseServiceInterface.listarPaginado(pageable).map(mapper::toDTO);
    }

}