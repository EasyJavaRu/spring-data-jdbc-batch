package ru.easyjava.spring.data.jdbc.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import ru.easyjava.spring.data.jdbc.dao.mapping.CustomerMapper;
import ru.easyjava.spring.data.jdbc.entity.Customer;

import javax.inject.Inject;
import java.util.List;

/**
 * JDBC implementation of CustomerRepository.
 */
@Repository
public class CustomerRepositoryJdbc implements CustomerRepository {
    /**
     * All customers retrieval query.
     */
    private static final String ALL_QUERY
            = "SELECT * FROM CUSTOMERS ORDER BY ID DESC";

    /**
     * Customer creation query.
     */
    private static final String CREATE_QUERY
            = "INSERT INTO CUSTOMERS (ID,EMAIL) VALUES(:id, :email)";

    /**
     * Database connection.
     */
    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public final void add(final List<Customer> customers) {
        SqlParameterSource[] batch = SqlParameterSourceUtils
                .createBatch(customers.toArray());
        jdbcTemplate.batchUpdate(CREATE_QUERY, batch);
    }

    @Override
    public final List<Customer> all() {
        return jdbcTemplate.query(ALL_QUERY, new CustomerMapper());
    }
}
