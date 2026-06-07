package com.senai.pousadabackend.core.base;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return repo.save(t);
    }

    @Override
    @Transactional
    public List<T> salvarEmLote(List<T> ts) {
        List<T> tsSaved = new ArrayList<>();
        ts.forEach(t -> tsSaved.add(salvar(t)));
        return tsSaved;
    }

    @Override
    public T buscarPorId(ID id) {
        if (id == null) throw new IllegalArgumentException("O Id é obrigatório.");
        return repo.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("O registro com o id '" + id + "' não foi encontrado"));
    }

    @Override
    @Transactional
    public T excluir(ID id) {
        T entidade = buscarPorId(id);
        repo.delete(entidade);
        return entidade;
    }

    public T alterar(T t) {
        try {
            Field field = t.getClass().getDeclaredField("id");
            field.setAccessible(true);
            ID valorId = (ID) field.get(t);
            if (valorId == null) {
                throw new IllegalArgumentException("O campo id é obrigatório no corpo da requisição");
            }
            return repo.save(t);
        } catch (NoSuchFieldException e) {
            log.error("Erro: {} \n StackTrace: {}", e.getMessage(), e.getStackTrace());
            throw new IllegalArgumentException("O campo id é obrigatório no corpo da requisição");
        } catch (IllegalAccessException e) {
            log.error("Erro: {} \n StackTrace: {}", e.getMessage(), e.getStackTrace());
            throw new IllegalArgumentException("Erro ao acessar o campo id");
        }
    }

    @Override
    @Transactional
    public T atualizar(T t) {
        return alterar(t);
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
        Specification<T> spec = (parametro == null || parametro.isBlank())
                ? null
                : toSpecification(parametro);

        return repo.findAll(spec, pageable);
    }

    @Override
    public Page<T> buscarComFiltros(Map<String, String> textFiltros, String rsqlSearch, Pageable pageable) {
        Specification<T> spec = null;

        for (Map.Entry<String, String> entry : textFiltros.entrySet()) {
            final String campo = entry.getKey();
            final String valor = "%" + entry.getValue().toLowerCase()
                    .replace("%", "\\%").replace("_", "\\_") + "%";
            Specification<T> parte = (root, query, cb) ->
                    cb.like(cb.lower(root.get(campo)), valor, '\\');
            spec = (spec == null) ? parte : spec.and(parte);
        }

        if (rsqlSearch != null && !rsqlSearch.isBlank()) {
            Specification<T> rsqlSpec = toSpecification(rsqlSearch);
            spec = (spec == null) ? rsqlSpec : spec.and(rsqlSpec);
        }

        return (spec != null) ? repo.findAll(spec, pageable) : repo.findAll(pageable);
    }

    @Override
    public Page<T> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }

}