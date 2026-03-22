package com.example.ordermanagement.businessLogic;

import com.example.ordermanagement.dataAccess.ProductDAO;
import com.example.ordermanagement.dataModel.Product;

import java.util.List;

/**
 * business logic vor product
 */
public class ProductBLL {
    private final ProductDAO dao = new ProductDAO();

    /**
     * return all the products
     * @return
     */
    public List<Product> getAll() {
        return dao.findAll();
    }


    public Product findById(int id) {
        return dao.findById(id);
    }

    /**
     * adds a product
     * @param p
     */
    public void add(Product p) {
        dao.insert(p);
    }

    /**
     * updates a product
     * @param p
     */
    public void update(Product p) {
        dao.update(p);
    }

    /**
     * deletes by id
     * @param id
     */
    public void delete(int id) {
        dao.delete(id);
    }
}
