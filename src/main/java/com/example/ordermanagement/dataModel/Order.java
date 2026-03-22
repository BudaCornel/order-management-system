package com.example.ordermanagement.dataModel;

import java.util.Date;
import java.util.Objects;

/**
 * model class for order
 */
public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;
    private Date orderDate;

    public Order() { }


    public Order(int id, int clientId, int productId, int quantity, Date orderDate) {
        this.id        = id;
        this.clientId  = clientId;
        this.productId = productId;
        this.quantity  = quantity;
        this.orderDate = orderDate;
    }

    /**
     * gets id
     * @return
     */
    public int getId() { return id; }

    /**
     * sets id
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * retutns client id
     * @return
     */
    public int getClientId() { return clientId; }

    /**
     * sets client id
     * @param clientId
     */
    public void setClientId(int clientId) { this.clientId = clientId; }

    /**
     * gets product id
     * @return
     */
    public int getProductId() { return productId; }

    /**
     * sets product id
     * @param productId
     */

    public void setProductId(int productId) { this.productId = productId; }

    /**
     * gets quality
     * @return
     */
    public int getQuantity() { return quantity; }

    /**
     * sets quality
     * @param quantity
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return String.format("Order[%d, client=%d, product=%d, qty=%d, date=%s]",
                id, clientId, productId, quantity, orderDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order o2 = (Order)o;
        return id == o2.id &&
                clientId == o2.clientId &&
                productId == o2.productId &&
                quantity == o2.quantity &&
                Objects.equals(orderDate, o2.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, productId, quantity, orderDate);
    }
}
