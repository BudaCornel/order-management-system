package com.example.ordermanagement.businessLogic;

/**
 * throws an error for when the order is bigger than the stock
 */
public class UnderStockException extends RuntimeException {
    public UnderStockException(String msg) {
        super(msg);
    }

}
