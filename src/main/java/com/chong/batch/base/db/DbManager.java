package com.chong.batch.base.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbManager {
    @Autowired
    private DataSource dataSource;

    public ResultSet getColumns(String tableName) throws SQLException {
        return dataSource.getConnection().getMetaData().getColumns(null, null, tableName, "%");
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<String> getDataTypeList(String tableName) {
        List<String> dataTypeList = new ArrayList<>();
        try (ResultSet rs = getColumns(tableName.toUpperCase())) {
            while (rs.next()) {
                String dataType = rs.getString("TYPE_NAME");
                dataTypeList.add(dataType);
                System.out.println("-----------" + dataType + "-----------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataTypeList;
    }
}
