package com.example.ordermanagement.dataModel;

import java.util.Date;

/**
 * view of an order that includes client name, product name, quantity, price total.
 */
public class OrderView {
    private int id;
    private String clientName;
    private String productName;
    private int quantity;
    private double price;
    private double total;
    private Date   orderDate;

    public OrderView(int id, String clientName, String productName,
                     int quantity, double price, Date orderDate) {
        this.id          = id;
        this.clientName  = clientName;
        this.productName = productName;
        this.quantity    = quantity;
        this.price       = price;
        this.total       = quantity * price;
        this.orderDate   = orderDate;
    }


    public int getId()                { return id; }
    public String getClientName()     { return clientName; }
    public String getProductName()    { return productName; }
    public int getQuantity()          { return quantity; }
    public double getPrice()          { return price; }
    public double getTotal()          { return total; }
    public Date getOrderDate()        { return orderDate; }
}
