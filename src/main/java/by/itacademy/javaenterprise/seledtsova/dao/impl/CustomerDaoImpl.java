package by.itacademy.javaenterprise.seledtsova.dao.impl;

import by.itacademy.javaenterprise.seledtsova.dao.CustomerDao;
import by.itacademy.javaenterprise.seledtsova.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("customerDaoBean")
public class CustomerDaoImpl implements CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
    private static final String SELECT_FROM_CUSTOMER_TABLE = "SELECT customer_id, first_name, last_name FROM Customers ORDER BY last_name LIMIT 100 OFFSET 3";
    private static final String DELETE_CUSTOMER_FROM_CUSTOMER_TABLES = "DELETE FROM Customers WHERE customer_id = ?";
    private static final String SELECT_FROM_CUSTOMER_TABLE_CUSTOMER_ID = "SELECT customer_id, first_name, last_name FROM Customers WHERE customer_id=?";
    private static final String ADD_NEW_CUSTOMER = "INSERT INTO Customers (customer_id, first_name, last_name) VALUES (?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE Customers SET first_name=?, last_name=? WHERE customer_id=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveCustomer(Customer customer) {
        try {
            jdbcTemplate.update(ADD_NEW_CUSTOMER, customer.getCustomerId(), customer.getFirstName(),
                    customer.getLastName());
        } catch (Exception e) {
            logger.error("Add customer is not available" + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            return jdbcTemplate.query(SELECT_FROM_CUSTOMER_TABLE, new CustomerMapper());
        } catch (Exception e) {
            logger.error("Get all customers is not available" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        try {
            jdbcTemplate.update(DELETE_CUSTOMER_FROM_CUSTOMER_TABLES, customerId);
        } catch (Exception e) {
            logger.error("Delete customer is not available" + e.getMessage(), e);
        }
    }

    @Override
    public Customer findCustomerByCustomerId(Long customerID) {
        try {
            return jdbcTemplate.query(SELECT_FROM_CUSTOMER_TABLE_CUSTOMER_ID, new Object[]{customerID},
                            new CustomerMapper())
                    .stream().findAny().orElse(null);
        } catch (Exception e) {
            logger.error("Find customer is not available" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void updateCustomerByCustomerId(Long customerId, Customer customer) {
        try {
            jdbcTemplate.update(UPDATE_CUSTOMER, customer.getFirstName(), customer.getLastName(), customerId);
        } catch (Exception e) {
            logger.error("Update customer is not available" + e.getMessage(), e);
        }
    }
}
