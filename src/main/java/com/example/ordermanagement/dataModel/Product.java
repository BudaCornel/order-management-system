package com.example.ordermanagement.dataModel;

import java.util.Objects;

/**
 * model class for the product
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product() { }

    public Product(int id, String name, double price, int stock) {
        this.id    = id;
        this.name  = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("Product[%d, %s, %.2f, stock=%d]", id, name, price, stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product)o;
        return id == p.id &&
                Double.compare(p.price, price) == 0 &&
                stock == p.stock &&
                Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock);
    }
}
