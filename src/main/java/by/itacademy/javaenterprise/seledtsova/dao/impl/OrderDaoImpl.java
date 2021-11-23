package by.itacademy.javaenterprise.seledtsova.dao.impl;

import by.itacademy.javaenterprise.seledtsova.dao.OrderDao;
import by.itacademy.javaenterprise.seledtsova.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("orderDaoBean")
public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    private static final String SELECT_FROM_ORDER_TABLE = "SELECT order_id, customer_id, quantity FROM Orders ORDER BY order_id LIMIT 100 OFFSET 1;";
    private static final String DELETE_ORDER_FROM_CUSTOMER_TABLES = "DELETE FROM Orders WHERE order_id = :order_id";
    private static final String ADD_NEW_ORDER = "INSERT INTO Orders (order_id, customer_id, quantity) VALUES (:order_id, :customer_id, :date_order)";
    private static final String UPDATE_ORDER = "UPDATE Orders SET customer_id=:customer_id, quantity=:date_order WHERE order_id=?";
    private static final String SELECT_FROM_ORDER_TABLE_BY_ORDER_ID = "SELECT order_id, customer_id, quantity FROM Orders WHERE order_id=?";
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;

    @Autowired
    public OrderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveOrder(Order order) {
        try {
            SqlParameterSource namedParameter = new MapSqlParameterSource("order_id", order.getOrderId())
                    .addValue("customer_id", order.getCustomerId())
                    .addValue("date_order", order.getQuantity());
            namedParameterJdbcTemplate.update(ADD_NEW_ORDER, namedParameter);
        } catch (Exception ex) {
            logger.error("Update order is not available", ex);
        }
    }

    @Override
    public void deleteOrderById(Long orderId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("order_id", orderId);
        try {
            namedParameterJdbcTemplate.update(DELETE_ORDER_FROM_CUSTOMER_TABLES, namedParameters);
            logger.info("A row was removed successful from the ORDERS table" + orderId);
        } catch (DataAccessException e) {
            logger.error("No order found with ID " + orderId);
        }
    }

    @Override
    public List<Order> getAll() {
        try {
            return namedParameterJdbcTemplate.query(SELECT_FROM_ORDER_TABLE, new BeanPropertyRowMapper<>(Order.class));
        } catch (DataAccessException e) {
            logger.error("Get all order is not available " + e);
            return new ArrayList<Order>();
        }
    }

    @Override
    public void update(Long orderId, Order order) {
        mapSqlParameterSource.addValue("order_id", orderId);
        mapSqlParameterSource.addValue("customer_id", order.getCustomerId());
        mapSqlParameterSource.addValue("date_order", order.getQuantity());
        try {
            namedParameterJdbcTemplate.update(UPDATE_ORDER, mapSqlParameterSource);
        } catch (DataAccessException e) {
            logger.error("Update order table by orderId is not impossible " + e);
        }
    }

    @Override
    public Order findOrderById(long id) {
        try {
            Map<String, Long> params = Map.of("id", id);
            return namedParameterJdbcTemplate.queryForObject(SELECT_FROM_ORDER_TABLE_BY_ORDER_ID, params, new OrderMapper());
        } catch (Exception ex) {
            logger.error("Get order by id is not impossible");
        }
        return null;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
