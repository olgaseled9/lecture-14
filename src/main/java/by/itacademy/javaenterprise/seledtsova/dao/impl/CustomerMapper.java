package by.itacademy.javaenterprise.seledtsova.dao.impl;

import by.itacademy.javaenterprise.seledtsova.entity.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        return customer;
    }
}

