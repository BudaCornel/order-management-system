package com.example.ordermanagement.businessLogic;

import com.example.ordermanagement.dataAccess.BillDAO;
import com.example.ordermanagement.dataModel.Bill;
import java.util.List;

/**
 * bill logic
 */
public class BillBLL {
    private final BillDAO dao = new BillDAO();
    public List<Bill> getAll() { return dao.findAll(); }
    public Bill findById(int id) { return dao.findById(id); }
}
