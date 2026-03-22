package com.example.ordermanagement.presentation;

import com.example.ordermanagement.businessLogic.ClientBLL;
import com.example.ordermanagement.dataModel.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

/**
 * GUI window to add/edit/delete clients.
 */
public class ClientWindow extends JFrame {
    private final ClientBLL bll = new ClientBLL();
    private JTable table;
    private JTextField txtName, txtEmail;

    public ClientWindow() {
        setTitle("Clients");
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
                        txtEmail.setText(table.getValueAt(row, 2).toString());
                    }
                }
            }
        });
        JScrollPane sp = new JScrollPane(table);

        txtName  = new JTextField(15);
        txtEmail = new JTextField(15);
        JButton btnAdd  = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel  = new JButton("Delete");
        JButton btnRef  = new JButton("Refresh");

        btnAdd.addActionListener(e -> {
            Client c = new Client(0, txtName.getText(), txtEmail.getText());
            bll.add(c);
            loadData();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            int id = (int) table.getValueAt(row, 0);
            Client c = new Client(id, txtName.getText(), txtEmail.getText());
            bll.update(c);
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
                        "Cannot delete this client because they have existing orders.",
                        "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRef.addActionListener(e -> loadData());

        JPanel form = new JPanel();
        form.add(new JLabel("Name:"));  form.add(txtName);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(btnAdd); form.add(btnEdit);
        form.add(btnDel); form.add(btnRef);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(form, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<Client> list = bll.getAll();
        table.setModel(Table.fromList(list).getModel());
    }
}
