package ru.easyjava.spring.data.jdbc.dao.mapping;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import ru.easyjava.spring.data.jdbc.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CustomerMapperTest extends EasyMockSupport {
    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private ResultSet rs;

    @TestSubject
    private CustomerMapper testedObject = new CustomerMapper();

    @Test
    public void testMapping() throws SQLException {
        expect(rs.getString("email")).andStubReturn("TEST");
        expect(rs.getInt("id")).andStubReturn(100);

        replayAll();

        Customer actual = testedObject.mapRow(rs, 0);

        assertThat(actual.getId(), is(100));
        assertThat(actual.getEmail(), is("TEST"));
    }
}