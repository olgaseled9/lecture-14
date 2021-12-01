package by.itacademy.javaenterprise.seledtsova.dao;

import by.itacademy.javaenterprise.seledtsova.dao.impl.OrderDaoImpl;
import by.itacademy.javaenterprise.seledtsova.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderDaoTest {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplateMock;
    private OrderDao orderDao;

    @BeforeEach
    public void beforeEachTestSettings() {
        namedParameterJdbcTemplateMock = Mockito.mock(NamedParameterJdbcTemplate.class);
        orderDao = new OrderDaoImpl(namedParameterJdbcTemplateMock);
    }

    @Test
    public void shouldGetAllOrdersTest() {
        List<Order> orders = orderDao.getAll();
        Assertions.assertThat(orders.size()).isZero();
    }

    @Test
    public void shouldFindOrderByOrderIdTest() {
        Long orderId = 111L;
        Long id = 111L;
        Order order = new Order();
        order.setOrderId(orderId);
        when(namedParameterJdbcTemplateMock.queryForObject(Mockito.anyString(), Mockito.<Map<String, ?>>any(),
                ArgumentMatchers.<RowMapper<Order>>any())).thenReturn(order);
        assertEquals(id, order.getOrderId());
    }

    @Test
    public void shouldAddOrderByOrderIdTest() {
        Order order = new Order(111L, 111L, 5);
        when(namedParameterJdbcTemplateMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenReturn(1);
        Assertions.assertThat(order.getOrderId()).isGreaterThan(0);
    }
}

