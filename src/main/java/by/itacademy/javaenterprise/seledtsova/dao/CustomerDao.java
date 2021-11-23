package by.itacademy.javaenterprise.seledtsova.dao;

import by.itacademy.javaenterprise.seledtsova.entity.Customer;

import java.util.List;

public interface CustomerDao {

    void saveCustomer(Customer customer);

    List<Customer> getAll();

    void deleteCustomerById(Long customerId);

    Customer findCustomerByCustomerId(Long customerID);

    public void updateCustomerByCustomerId(Long customerId, Customer customer);
}
