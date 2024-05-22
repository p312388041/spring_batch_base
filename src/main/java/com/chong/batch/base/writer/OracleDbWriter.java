package com.chong.batch.base.writer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.chong.batch.base.db.DbManager;

public class OracleDbWriter extends JdbcBatchItemWriter<String[]> {

    public OracleDbWriter(DbManager dbManager, String tableName) {
        String sql = "";
        try (ResultSet rs = dbManager.getColumns(tableName)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("TYPE_NAME");
                System.out.println("Column: " + columnName + ", Type: " + dataType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSql(sql);
        setItemPreparedStatementSetter((item, ps) -> {
            int length = item.length;

            // ps.setInt(1, item.getId());
            // ps.setString(2, item.getName());
            // ps.setFloat(3, item.getChinese());
            // ps.setFloat(4, item.getEnglish());
            // ps.setFloat(5, item.getMath());
            // ps.setFloat(6, item.getTotal());
        });
    }
}
