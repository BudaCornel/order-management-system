package com.example.ordermanagement.dataAccess;

import com.example.ordermanagement.connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * A generic DAO using reflection to build basic CRUD
 * for any dataModel class whose fields map directly to table columns.
 *
 * @param <T> the dataModel type
 */
public abstract class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    protected AbstractDAO() {
        this.type = (Class<T>)
                ((java.lang.reflect.ParameterizedType)
                        getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    /** By default, table name = simple class name lower-case. */
    protected String getTableName() {
        return type.getSimpleName().toLowerCase();
    }

    /**
     * insert obj, writing back its generated ID if the id field is not final.
     * @param obj
     */
    public void insert(T obj) {
        Field[] all = type.getDeclaredFields();
        List<Field> fields = Arrays.stream(all)
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .collect(Collectors.toList());

        String colList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
        String valList = fields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                getTableName(), colList, valList);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            AtomicInteger idx = new AtomicInteger(1);
            fields.forEach(f -> {
                try {
                    f.setAccessible(true);
                    bindValue(stmt, idx.getAndIncrement(), f.getType(), f.get(obj));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    Field idF = type.getDeclaredField("id");
                    if (!Modifier.isFinal(idF.getModifiers())) {
                        idF.setAccessible(true);
                        idF.set(obj, keys.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Insert failed for " + getTableName(), e);
        }
    }

    /**
     * Update all non-ID fields of obj in the table
     * @param obj
     */
    public void update(T obj) {
        Field[] all = type.getDeclaredFields();
        List<Field> fields = Arrays.stream(all)
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .collect(Collectors.toList());

        String setList = fields.stream()
                .map(f -> f.getName() + "=?")
                .collect(Collectors.joining(","));

        String sql = String.format("UPDATE %s SET %s WHERE id=?",
                getTableName(), setList);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            AtomicInteger idx = new AtomicInteger(1);
            fields.forEach(f -> {
                try {
                    f.setAccessible(true);
                    bindValue(stmt, idx.getAndIncrement(), f.getType(), f.get(obj));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });


            Field idF = type.getDeclaredField("id");
            idF.setAccessible(true);
            bindValue(stmt, idx.get(), idF.getType(), idF.get(obj));

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Update failed for " + getTableName(), e);
        }
    }

    /**
     * Delete the row with the given ID.
     * @param id
     */
    public void delete(int id) {
        String sql = String.format("DELETE FROM %s WHERE id=?", getTableName());
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete failed for " + getTableName(), e);
        }
    }

    /**
     *  Find a row by ID.
     * @param id
     * @return
     */
    public T findById(int id) {
        String sql = String.format("SELECT * FROM %s WHERE id=?", getTableName());
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (Exception e) {
            throw new RuntimeException("FindById failed for " + getTableName(), e);
        }
    }

    /**
     * Return all rows in the table as a {@code List<T>}.
     * @return
     */
    public List<T> findAll() {
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException("FindAll failed for " + getTableName(), e);
        }
    }

    /**
     * default mapper: BigDecimal->double, Timestamp->Date, else raw.
     * @param rs
     * @return
     * @throws Exception
     */
    protected T mapRow(ResultSet rs) throws Exception {
        T obj = type.getDeclaredConstructor().newInstance();
        Arrays.stream(type.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                Object raw = rs.getObject(f.getName());
                Object val;
                if (raw instanceof Timestamp && f.getType() == Date.class) {
                    val = new Date(((Timestamp) raw).getTime());
                } else if (raw instanceof BigDecimal
                        && (f.getType() == double.class || f.getType() == Double.class)) {
                    val = ((BigDecimal) raw).doubleValue();
                } else {
                    val = raw;
                }
                f.set(obj, val);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return obj;
    }

    /**
     * bind a Java value to a PreparedStatement, using timestamp for Dates.
     * @param stmt
     * @param idx
     * @param fld
     * @param value
     * @throws SQLException
     */
    private void bindValue(PreparedStatement stmt, int idx, Class<?> fld, Object value)
            throws SQLException {
        if (value == null) {
            stmt.setObject(idx, null);
        } else if (Date.class.isAssignableFrom(fld)) {
            stmt.setTimestamp(idx, new Timestamp(((Date) value).getTime()));
        } else {
            stmt.setObject(idx, value);
        }
    }
}
