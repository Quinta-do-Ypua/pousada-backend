package com.senai.pousadabackend.service;

import com.senai.pousadabackend.entity.EntityAudit;
import com.senai.pousadabackend.entity.enums.Status;
import com.senai.pousadabackend.exceptions.InativoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@Slf4j
public class BaseService<T extends EntityAudit, ID, R extends BaseRepository<T, ID>> implements BaseServiceInterface<T, ID> {

    private final R repo;

    public BaseService(R repo) {
        this.repo = repo;
    }

    @Override
    public T salvar(T t) {
        if (!t.isNovo()) {
            return alterar(t);
        }
        if (t.getStatus() == null || !t.getStatus().isAtivo()) t.setStatus(Status.ATIVO);
        return repo.save(t);
    }

    @Override
    public T buscarPorId(ID id) {
        if (id == null) throw new IllegalArgumentException("O Id é obrigatório.");
        return repo.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("O registro com o id '" + id + "' não foi encontrado"));
    }

    @Override
    public T excluir(ID id) {
        T entidade = buscarPorId(id);
        if (entidade.getStatus() != null && !entidade.getStatus().isAtivo()) {
            throw new InativoException(entidade.getClass().getSimpleName() + " já está inativo.");
        }
        entidade.setStatus(Status.INATIVO);
        return repo.save(entidade);
    }

    private T alterar(T t) {
        try {
            Field field = t.getClass().getDeclaredField("id");
            ID valorId = (ID) field.get(t);
            buscarPorId(valorId);
            return this.salvar(t);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Erro: {} \n StackTrace: {}", e.getMessage(), e.getStackTrace());
            throw new IllegalArgumentException("O campo id é obrigatório no corpo da requisição");
        }
    }


    @Override
    public void throwIfNotExists(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Para validar a existência o id é obrigatório");
        }

        if (!repo.existsById(id)) {
            throw new RegistroNaoEncontradoException("O registro com o id '" + id + "' não foi encontrado");
        }
    }

    @Override
    public Page<T> buscarPorSpecification(String parametro, Pageable pageable) {
        if (parametro == null || parametro.isBlank()) {
            return repo.findAll(pageable);
        }
        Specification<T> spec = toSpecification(parametro);
        return repo.findAll(spec, pageable);
    }

    @Override
    public Page<T> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<T> listarInativos(Pageable pageable) {
        return repo.findByStatus(Status.INATIVO, pageable);
    }

}