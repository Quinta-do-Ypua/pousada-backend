package com.senai.pousadabackend.core;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.core.entity.Status;
import com.senai.pousadabackend.exceptions.InativoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.core.repository.BaseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@Slf4j
public class BaseService<T extends EntityAudit, ID, R extends BaseRepository<T, ID>> implements BaseServiceInterface<T, ID> {

    private final R repo;

    public BaseService(R repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
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
            field.setAccessible(true);
            ID valorId = (ID) field.get(t);
            buscarPorId(valorId);
            return repo.saveAndFlush(t);
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
        Specification<T> statusAtivoSpec = (root, query, cb) ->
                cb.equal(root.get("status"), Status.ATIVO);
        Specification<T> specFinal;
        if (parametro == null || parametro.isBlank()) {
            specFinal = statusAtivoSpec;
        } else {
            Specification<T> specParametro = toSpecification(parametro);
            specFinal = statusAtivoSpec.and(specParametro);
        }
        return repo.findAll(specFinal, pageable);
    }


    @Override
    public Page<T> listarPaginado(Pageable pageable) {
        return repo.findByStatus(Status.ATIVO, pageable);
    }

    @Override
    public Page<T> listarInativos(Pageable pageable) {
        return repo.findByStatus(Status.INATIVO, pageable);
    }

}