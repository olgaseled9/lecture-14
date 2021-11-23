package by.itacademy.javaenterprise.seledtsova.dao;

import by.itacademy.javaenterprise.seledtsova.entity.Order;

import java.util.List;

public interface OrderDao {

    void saveOrder(Order order);

    List<Order> getAll();

    void deleteOrderById(Long orderId);

    public void update(Long orderId, Order order);

    Order findOrderById(long id);

}
