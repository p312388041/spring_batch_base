package com.chong.batch.base.writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.chong.batch.base.db.DbManager;

public class OracleDbWriter extends JdbcBatchItemWriter<String[]> {

    public OracleDbWriter(DbManager dbManager, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder("insert into " + tableName + " values(");
        List<String> dataTypeList = new ArrayList<>();
        try (ResultSet rs = dbManager.getColumns(tableName.toUpperCase())) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                sqlBuilder.append(":");
                sqlBuilder.append(columnName);
                sqlBuilder.append(", ");
                String dataType = rs.getString("TYPE_NAME");
                dataTypeList.add(dataType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setJdbcTemplate(new NamedParameterJdbcTemplate(dbManager.getDataSource()));
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 2) + ")";
        System.out.println(sql);
        setSql(sql);
        // TODO: add all type
        
        setItemPreparedStatementSetter((item, ps) -> {
            int length = item.length;
            for (int i = 0; i < length; i++) {
                String dataType = dataTypeList.get(i);
                switch (dataType) {
                    case "NUMBER":
                        ps.setInt(i + 1, Integer.parseInt(item[i]));
                        break;
                    case "VARCHAR2":
                        ps.setString(i + 1, item[i]);
                        break;
                    case "FLOAT":
                        ps.setFloat(i + 1, Float.parseFloat(item[i]));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
