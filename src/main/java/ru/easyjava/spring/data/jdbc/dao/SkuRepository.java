package ru.easyjava.spring.data.jdbc.dao;

import ru.easyjava.spring.data.jdbc.entity.Sku;

import java.util.List;

/**
 * SKU database layer.
 */
public interface SkuRepository {
    /**
     * Creates new SKU in the database.
     * @param sku SKU to craete.
     */
    void add(List<Sku> sku);

    /**
     * Returns description of the SKU with specified id.
     * @param id SKU id value.
     * @return SKU description value.
     */
    String getDescription(Integer id);
}
