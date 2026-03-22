package com.example.ordermanagement.presentation;

import com.example.ordermanagement.businessLogic.*;
import com.example.ordermanagement.dataModel.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GUI window to create new orders, view all orders, and show bills.
 */
public class OrderWindow extends JFrame {
    private final ClientBLL  clientBLL  = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();
    private final OrderBLL   orderBLL   = new OrderBLL();
    private final BillBLL    billBLL    = new BillBLL();

    private JComboBox<Client>  cmbClient;
    private JComboBox<Product> cmbProduct;
    private JTextField         txtQty;
    private JTable             tblOrders;

    public OrderWindow() {
        setTitle("Orders");
        setSize(750,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadClients();
        loadProducts();
        loadOrders();
    }

    private void initComponents() {
        cmbClient  = new JComboBox<>();
        cmbProduct = new JComboBox<>();
        txtQty     = new JTextField(3);
        JButton btnOrder    = new JButton("Place Order");
        JButton btnShowBills = new JButton("Show Bills");

        btnOrder.addActionListener(e -> {
            try {
                Client  c = (Client)cmbClient.getSelectedItem();
                Product p = (Product)cmbProduct.getSelectedItem();
                int qty = Integer.parseInt(txtQty.getText());
                orderBLL.placeOrder(c.getId(), p.getId(), qty);
                loadProducts();
                loadOrders();
            } catch (UnderStockException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Stock Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnShowBills.addActionListener(e -> {
            BillWindow bw = new BillWindow(billBLL.getAll());
            bw.setVisible(true);
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Client:"));  form.add(cmbClient);
        form.add(new JLabel("Product:")); form.add(cmbProduct);
        form.add(new JLabel("Qty:"));     form.add(txtQty);
        form.add(btnOrder);                form.add(btnShowBills);

        tblOrders = new JTable();
        JScrollPane sp = new JScrollPane(tblOrders);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.NORTH);
        getContentPane().add(sp, BorderLayout.CENTER);
    }

    private void loadClients() {
        cmbClient.removeAllItems();
        clientBLL.getAll().forEach(cmbClient::addItem);
    }

    private void loadProducts() {
        cmbProduct.removeAllItems();
        productBLL.getAll().forEach(cmbProduct::addItem);
    }

    private void loadOrders() {
        List<OrderView> vs = orderBLL.getAllViews();
        tblOrders.setModel(Table.fromList(vs).getModel());
    }
}
