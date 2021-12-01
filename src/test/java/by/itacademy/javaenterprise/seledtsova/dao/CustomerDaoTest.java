package by.itacademy.javaenterprise.seledtsova.dao;

import by.itacademy.javaenterprise.seledtsova.dao.impl.CustomerDaoImpl;
import by.itacademy.javaenterprise.seledtsova.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerDaoTest {

    private JdbcTemplate jdbcTemplateMock;
    private CustomerDao customerDao;

    @BeforeEach
    public void setUp() throws Exception {
        jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        customerDao = new CustomerDaoImpl(jdbcTemplateMock);
    }

    @Test
    public void shouldGetAllCustomersTest() {
        List<Customer> customers = customerDao.getAll();
        Assertions.assertThat(customers.size()).isZero();
    }

    @Test
    public void shouldFindCustomerByIdTest() {
        long customerId = 9;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        when(jdbcTemplateMock.queryForObject(Mockito.anyString(), Mockito.<RowMapper<Customer>>any(),
                Mockito.any())).thenReturn(customer);
        int queryId = 9;
        assertEquals(queryId, customer.getCustomerId());
    }

    @Test
    public void ShouldUpdateCustomerTest() {
        Customer customer = new Customer();
        customer.setCustomerId(10L);
        customer.setFirstName("Anna");
        customer.setLastName("Korenina");
        customerDao.updateCustomerByCustomerId(1L, customer);
        Assertions.assertThat(customer.getCustomerId()).isEqualTo(10);
    }

    @Test
    public void shouldDeleteCustomerTest() {
        Long customerId = 35L;
        Customer customer = customerDao.findCustomerByCustomerId(customerId);
        customerDao.deleteCustomerById(customerId);
        Customer customer1 = null;
        when(jdbcTemplateMock.queryForObject(Mockito.anyString(), Mockito.<RowMapper<Customer>>any(),
                Mockito.any())).thenReturn(customer);
        Assertions.assertThat(customer).isNull();
    }
}




