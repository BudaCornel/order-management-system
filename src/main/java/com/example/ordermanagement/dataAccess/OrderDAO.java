package com.example.ordermanagement.dataAccess;

import com.example.ordermanagement.dataModel.Order;

/**
 * dao for orders
 */
public class OrderDAO extends AbstractDAO<Order> {
    @Override
    protected String getTableName() {
        return "orders";
    }
}
