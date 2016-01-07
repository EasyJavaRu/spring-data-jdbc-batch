package ru.easyjava.spring.data.jdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.easyjava.spring.data.jdbc.entity.Customer;

import javax.inject.Inject;

import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration("/testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRepositoryJdbcIT {
    @Inject
    private CustomerRepositoryJdbc testedObject;

    @DirtiesContext
    @Test
    public void testAdd() {
        Customer c = new Customer();
        c.setId(101);
        c.setEmail("nextone@example.org");

        testedObject.add(Collections.singletonList(c));

        assertTrue(testedObject.all()
                .stream()
                .filter(customer ->"nextone@example.org".equals(customer.getEmail()))
                .findFirst()
                .isPresent());
    }
}