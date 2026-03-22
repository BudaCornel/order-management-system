package com.example.ordermanagement.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Utility to turn a {@code List<T>} into a {@code JTable}.
 */
public class Table {
    /**
     * Utility to turn a {@code List<T>} into a {@code JTable}.
     */
    public static <T> JTable fromList(List<T> data) {
        DefaultTableModel model = new DefaultTableModel();
        if (!data.isEmpty()) {
            Class<?> cls = data.get(0).getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                model.addColumn(f.getName());
            }
            data.forEach(item -> {
                try {
                    Object[] row = new Object[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        row[i] = fields[i].get(item);
                    }
                    model.addRow(row);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return new JTable(model);
    }
}
