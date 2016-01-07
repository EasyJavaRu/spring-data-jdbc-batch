package ru.easyjava.spring.data.jdbc.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.easyjava.spring.data.jdbc.entity.Sku;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JDBC based implementation of the SKU repository.
 */
@Repository
public class SkuRepositoryJdbc implements SkuRepository {
    /**
     * Query for description retrieval.
     */
    private static final String DESCRIPTION_QUERY
            = "SELECT DESCRIPTION FROM SKUS WHERE ID = :id";

    /**
     * Query for SKU creation.
     */
    private static final String ADD_QUERY
            = "INSERT INTO SKUS(ID, DESCRIPTION) VALUES(?, ?)";

    /**
     * Database object with named parameters support.
     */
    @Inject
    private NamedParameterJdbcTemplate namedTemplate;

    /**
     * Database object.
     */
    @Inject
    private JdbcTemplate jdbcTemplate;

    /**
     * Sample implementation of BatchPreparedStatementSetter.
     */
    private static class SkuAddBatch implements BatchPreparedStatementSetter {

        /**
         * Our data to process.
         */
        private final List<Sku> skus;

        /**
         * Constructor.
         * @param s Skus data to process.
         */
        public SkuAddBatch(final List<Sku> s) {
            this.skus = s;
        }

        @Override
        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, skus.get(i).getId());
            ps.setString(2, skus.get(i).getDescription());
        }

        @Override
        public int getBatchSize() {
            return skus.size();
        }
    }

    @Override
    public final void add(final List<Sku> skus) {
        jdbcTemplate.batchUpdate(ADD_QUERY, new SkuAddBatch(skus));
    }

    @Override
    public final String getDescription(final Integer id) {
        Map<String, Object> namedParameters
              = Collections.singletonMap("id", id);
        return namedTemplate.queryForObject(
                DESCRIPTION_QUERY,
                namedParameters,
                String.class);
    }
}
