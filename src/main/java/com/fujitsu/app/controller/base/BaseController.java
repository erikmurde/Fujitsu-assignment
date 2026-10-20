package com.fujitsu.app.controller.base;

import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.service.base.CrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Abstract base controller class that implements most common controller mappings and methods.
 * Used for controllers that only need basic CRUD implementation.
 *
 * @param <TEntity> domain class used by controller
 * @param <TService> service class used by controller
 */
public abstract class BaseController<TEntity, TService extends CrudService<TEntity>> {

    public static final String API_PATH = "/api/";

    private final TService service;

    protected BaseController(TService service) {
        this.service = service;
    }

    /**
     * Endpoint for retrieving all entities from database.
     *
     * @return list of extra fees
     */
    @GetMapping
    public List<TEntity> getAll() {
        return service.getAll();
    }

    /**
     * Endpoint for retrieving the entity with the given id.
     *
     * @param id unique identifier of the entity
     * @return entity with the given id if it exists
     */
    @GetMapping("/{id}")
    public TEntity getById(@PathVariable Long id) {
        return service
                .getById(id)
                .orElseThrow(() -> new AppException("No entity with given id", HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint for creating a new entity with the provided values.
     *
     * @param entity values to add to new entity
     * @return id of created entity
     */
    @PostMapping
    public Long add(@RequestBody @Valid TEntity entity) {
        return service.create(entity);
    }

    /**
     * Endpoint for updating the entity with the given id.
     *
     * @param id unique identifier of the entity
     * @param entity values to replace in existing entity
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid TEntity entity) {
        service.update(id, entity);
    }

    /**
     * Endpoint for deleting the entity with the given id.
     *
     * @param id unique identifier of the entity
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
