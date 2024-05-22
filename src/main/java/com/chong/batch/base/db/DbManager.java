package com.chong.batch.base.db;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
