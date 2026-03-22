package com.example.ordermanagement.presentation;

import javax.swing.*;

/**
 * Main application launcher.
 */
public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Clients", "Products", "Orders"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Which module do you want to open?",
                    "Order Management",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            switch (choice) {
                case 0 -> new ClientWindow().setVisible(true);
                case 1 -> new ProductWindow().setVisible(true);
                case 2 -> new OrderWindow().setVisible(true);
                default -> System.exit(0);
            }
        });
    }
}
