package com.example.ordermanagement.dataAccess;

import com.example.ordermanagement.dataModel.Bill;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * bills are immutable and that can be in logs
 */
public class BillDAO extends AbstractDAO<Bill> {
    @Override
    protected String getTableName() {
        return "log";
    }

    @Override
    public void update(Bill b) {
        throw new UnsupportedOperationException("Bills are immutable");
    }

    /**
     * Map a ResultSet row into a Bill record via its canonical constructor.
     * @param rs
     * @return
     * @throws Exception
     */
    @Override
    protected Bill mapRow(ResultSet rs) throws Exception {
        int id       = rs.getInt("id");
        int orderId  = rs.getInt("orderid");
        Timestamp ts = rs.getTimestamp("date");
        Date date    = ts != null ? new Date(ts.getTime()) : null;
        double total = rs.getDouble("total");
        return new Bill(id, orderId, date, total);
    }
}
