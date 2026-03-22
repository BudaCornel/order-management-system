package com.example.ordermanagement.businessLogic;

import com.example.ordermanagement.dataAccess.*;
import com.example.ordermanagement.dataModel.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * business logic for orders
 */
public class OrderBLL {
    private final OrderDAO   orderDao   = new OrderDAO();
    private final ProductDAO prodDao    = new ProductDAO();
    private final BillDAO    billDao    = new BillDAO();

    /**
     * places an order
     * @param clientId
     * @param productId
     * @param qty
     */
    public void placeOrder(int clientId, int productId, int qty) {
        Product p = prodDao.findById(productId);
        if (p.getStock() < qty) {
            throw new UnderStockException(
                    "Only " + p.getStock() + " in stock, but tried to order " + qty);
        }
        Order o = new Order(0, clientId, productId, qty, new Date());
        orderDao.insert(o);

        p.setStock(p.getStock() - qty);
        prodDao.update(p);

        double total = qty * p.getPrice();
        Bill b = new Bill(0, o.getId(), new Date(), total);
        billDao.insert(b);
    }

    /**
     * retrurns all the orders
     * @return
     */
    public List<OrderView> getAllViews() {
        OrderDAO od = orderDao;
        ClientDAO cd = new ClientDAO();
        for (Order o : od.findAll()) ;
        return orderDao.findAll().stream().map(o -> {
            Client c = cd.findById(o.getClientId());
            Product p = prodDao.findById(o.getProductId());
            return new OrderView(
                    o.getId(),
                    c.getName(),
                    p.getName(),
                    o.getQuantity(),
                    p.getPrice(),
                    o.getOrderDate()
            );
        }).collect(Collectors.toList());
    }
}
