package com.chong.batch.base.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.chong.batch.base.db.DbDataManager;

@ComponentScan({ "com.chong.batch.base.db" })
@SpringBatchTest
public class JobManagerTest {
    @Autowired
    private DbDataManager dbDataManager;

    @Test
    void createJobTest() throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        String importFilePath = "C:\\Users\\31238\\OneDrive\\デスクトップ\\test.csv";

        String exportFilePath = "C:\\Users\\31238\\OneDrive\\デスクトップ\\test2.csv";
        String tableName = "student";
        dbDataManager.batchImportData(importFilePath, tableName);
        // System.out.println("---------------------import sucess-------------------");
        // dbDataManager.batchExportData(exportFilePath, tableName);
    }
}
