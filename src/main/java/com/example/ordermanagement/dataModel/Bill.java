package com.example.ordermanagement.dataModel;

import java.util.Date;

/**
 * immutable bill
 * @param id
 * @param orderId
 * @param date
 * @param total
 */
public record Bill(int id,
                   int orderId,
                   Date date,
                   double total) { }
