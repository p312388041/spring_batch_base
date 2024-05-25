package com.chong.batch.base.reader;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;

import com.chong.batch.base.db.DbManager;

public class OracleItemReader extends JdbcCursorItemReader<List<String>> {
    // TODO sql injection
    public OracleItemReader(DbManager dbManager, String tableName) {
        DataSource dataSource = dbManager.getDataSource();
        String sql = "select * from " + tableName;
        setSql(sql);
        setDataSource(dataSource);
        setVerifyCursorPosition(false);
        List<String> dataTypeList = dbManager.getDataTypeList(tableName);
        setRowMapper((rs, rowNum) -> {
            List<String> lines = new ArrayList<>();
            while (rs.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < dataTypeList.size(); i++) {
                    switch (dataTypeList.get(i)) {
                        case "NUMBER":
                            stringBuilder.append("\"");
                            stringBuilder.append(rs.getInt(i + 1) + "");
                            stringBuilder.append("\"");
                            stringBuilder.append(",");
                            break;
                        case "VARCHAR2":
                            stringBuilder.append("\"");
                            stringBuilder.append(rs.getString(i + 1));
                            stringBuilder.append("\"");
                            stringBuilder.append(",");
                            break;
                        case "FLOAT":
                            stringBuilder.append("\"");
                            stringBuilder.append(rs.getFloat(i + 1) + "");
                            stringBuilder.append("\"");
                            stringBuilder.append(",");
                            break;
                    }
                }
                lines.add(stringBuilder.substring(0, stringBuilder.length() - 1).toString());
            }
            return lines;
        });
    }
}