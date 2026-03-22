package com.example.ordermanagement.presentation;

import com.example.ordermanagement.businessLogic.ProductBLL;
import com.example.ordermanagement.dataModel.Product;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

/**
 * GUI window to add/edit/delete products.
 */
public class ProductWindow extends JFrame {
    private final ProductBLL bll = new ProductBLL();
    private JTable table;
    private JTextField txtName, txtPrice, txtStock;

    public ProductWindow() {
        setTitle("Products");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        txtName.setText(table.getValueAt(row, 1).toString());
                        txtPrice.setText(table.getValueAt(row, 2).toString());
                        txtStock.setText(table.getValueAt(row, 3).toString());
                    }
                }
            }
        });
        JScrollPane sp = new JScrollPane(table);

        txtName  = new JTextField(10);
        txtPrice = new JTextField(5);
        txtStock = new JTextField(5);

        JButton btnAdd  = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel  = new JButton("Delete");
        JButton btnRef  = new JButton("Refresh");

        btnAdd.addActionListener(e -> {
            Product p = new Product(
                    0,
                    txtName.getText(),
                    parseDouble(txtPrice.getText()),
                    parseInt(txtStock.getText())
            );
            bll.add(p);
            loadData();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            int id = (int) table.getValueAt(row, 0);
            Product p = new Product(
                    id,
                    txtName.getText(),
                    parseDouble(txtPrice.getText()),
                    parseInt(txtStock.getText())
            );
            bll.update(p);
            loadData();
        });

        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            int id = (int) table.getValueAt(row, 0);
            try {
                bll.delete(id);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Cannot delete this product because it is referenced in an existing order.",
                        "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRef.addActionListener(e -> loadData());

        JPanel form = new JPanel();
        form.add(new JLabel("Name:"));  form.add(txtName);
        form.add(new JLabel("Price:")); form.add(txtPrice);
        form.add(new JLabel("Stock:")); form.add(txtStock);
        form.add(btnAdd); form.add(btnEdit);
        form.add(btnDel); form.add(btnRef);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(form, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<Product> list = bll.getAll();
        table.setModel(Table.fromList(list).getModel());
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
