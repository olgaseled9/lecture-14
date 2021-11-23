package by.itacademy.javaenterprise.seledtsova.dao;

import by.itacademy.javaenterprise.seledtsova.dao.impl.CustomerDaoImpl;
import by.itacademy.javaenterprise.seledtsova.entity.Customer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class CustomerDaoTest {

    private static CustomerDao customerDao;
    private static BasicDataSource basicDataSource;
    private static JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void beforeClass() throws Exception {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName("imagine_store")
                .withUsername("postgres")
                .withPassword("postgres");
        postgreSQLContainer.start();
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        basicDataSource.setUsername(postgreSQLContainer.getUsername());
        basicDataSource.setPassword(postgreSQLContainer.getPassword());
        basicDataSource.setDefaultSchema(postgreSQLContainer.getDatabaseName());
        Flyway flyway = Flyway.configure()
                .dataSource(basicDataSource)
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDataSource);
        customerDao = new CustomerDaoImpl(jdbcTemplate);
    }

    @Test
    public void shouldFindCustomerByIdTest() {
        long id = 2L;
        Customer customer = customerDao.findCustomerByCustomerId(id);
        assertEquals(id, customer.getCustomerId());
    }

    @Test
    public void ShouldUpdateCustomerTest() {
        Customer customer = new Customer(1L, "Anna", "Korenina");
        customer.setCustomerId(1L);
        customer.setFirstName("Anna");
        customer.setLastName("Korenina");
        customerDao.updateCustomerByCustomerId(1L, customer);
        customer.setCustomerId(1L);
        assertEquals(1L, customer.getCustomerId());

    }


}



