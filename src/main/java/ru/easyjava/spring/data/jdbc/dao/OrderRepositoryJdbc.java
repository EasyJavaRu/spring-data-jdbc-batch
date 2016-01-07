package ru.easyjava.spring.data.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import ru.easyjava.spring.data.jdbc.dao.mapping.OrderMapper;
import ru.easyjava.spring.data.jdbc.entity.Order;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * JDBC based implementation of OrderRepository.
 */
@Repository
public class OrderRepositoryJdbc implements OrderRepository {
    /**
     * Size of sub batches.
     */
    private static final Integer BATCH_SIZE = 10;
    /**
     * Query for specific order.
     */
    private static final String ORDER_QUERY
            = "SELECT O.ID, CUSTOMER_ID, EMAIL "
            + "FROM ORDERS AS O, CUSTOMERS AS C "
            + "WHERE C.ID=O.CUSTOMER_ID AND O.ID=?";

    /**
     * Creation query.
     */
    private static final String ADD_QUERY
            = "INSERT INTO ORDERS "
            + "(ID, CUSTOMER_ID) "
            + "VALUES(?,?)";

    /**
     * Database connection.
     */
    @Inject
    private JdbcTemplate jdbcTemplate;

    /**
     * Sample implementation of ParameterizedPreparedStatementSetter.
     */
    private static class OrderSetter
            implements ParameterizedPreparedStatementSetter<Order> {

        @Override
        public final void setValues(
                final PreparedStatement ps,
                final Order o) throws SQLException {
            ps.setInt(1, o.getId());
            ps.setInt(2, o.getCustomer().getId());
        }
    }

    @Override
    public final void add(final Collection<Order> orders) {
        jdbcTemplate.batchUpdate(
                ADD_QUERY,
                orders,
                BATCH_SIZE,
                new OrderSetter());
    }

    @Override
    public final Order get(final Integer id) {
        return jdbcTemplate.queryForObject(ORDER_QUERY, new OrderMapper(), id);
    }
}
