package com.chong.batch.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.chong.batch.base.db.DbDataManager;

@ComponentScan({ "com.chong.batch.base" })
@SpringBootApplication
public class Application implements ApplicationRunner {
    @Autowired
    private DbDataManager dbDataManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String importFilePath = "C:\\Users\\31238\\OneDrive\\デスクトップ\\test.csv";

        String exportFilePath = "C:\\Users\\31238\\OneDrive\\デスクトップ\\test2.csv";
        String tableName = "student";
        dbDataManager.batchImportData(importFilePath, tableName);
        System.out.println("---------------------import sucess-------------------");
        dbDataManager.batchExportData(exportFilePath, tableName);
    }
}
