package com.fujitsu.app.service.base;

import java.util.List;
import java.util.Optional;

/**
 * Base service interface for basic CRUD operations.
 *
 * @param <TEntity> class to perform operations on
 */
public interface CrudService<TEntity> {

    List<TEntity> getAll();

    Optional<TEntity> getById(Long id);

    Long create(TEntity entity);

    void update(Long id, TEntity entity);

    void delete(Long id);
}
