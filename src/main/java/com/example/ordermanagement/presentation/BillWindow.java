package com.example.ordermanagement.presentation;

import com.example.ordermanagement.dataModel.Bill;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GUI window to view all bills.
 */
public class BillWindow extends JFrame {
    private JTable table;

    public BillWindow(List<Bill> bills) {
        setTitle("Bills");
        setSize(500,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table = new JTable(Table.fromList(bills).getModel());
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
