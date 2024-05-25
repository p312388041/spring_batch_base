package com.chong.batch.base.db;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chong.batch.base.job.JobManager;

@Component
public class DbDataManager {

    @Autowired
    private JobManager jobManager;

    public void batchImportData(String filePath, String tableName) throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Job job = jobManager.createImportJob(filePath, tableName);
        jobManager.launchJob(job);
    }

    public void batchExportData(String filePath, String tableName) throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Job job = jobManager.createExportJob(filePath, tableName);
        jobManager.launchJob(job);
    }

    private DbDataManager() {

    }
}
