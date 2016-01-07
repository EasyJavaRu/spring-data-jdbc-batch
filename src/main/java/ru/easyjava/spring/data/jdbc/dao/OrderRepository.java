package ru.easyjava.spring.data.jdbc.dao;

import ru.easyjava.spring.data.jdbc.entity.Order;

import java.util.Collection;

/**
 * Order database layer.
 */
public interface OrderRepository {
    /**
     * Adds new orders.
     * @param orders data to add.
     */
    void add(Collection<Order> orders);

    /**
     * Loads order from the database by it's id.
     * @param id id of order to load.
     * @return Loaded object or null.
     */
    Order get(Integer id);
}
