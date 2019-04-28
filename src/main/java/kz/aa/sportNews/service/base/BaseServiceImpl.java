package kz.aa.sportNews.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;

    @Override
    public T saveOrUpdate(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(T id) {
        repository.delete(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }
}
