package com.chong.batch.base.job;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chong.batch.base.db.DbManager;
import com.chong.batch.base.reader.CsvItemReader;
import com.chong.batch.base.reader.OracleItemReader;
import com.chong.batch.base.writer.CsvItemWriter;
import com.chong.batch.base.writer.OracleDbWriter;

@Component
public class JobManager {

    @Autowired
    private BatchJobManager batchJobManager;

    @Autowired
    DbManager dbManager;

    public Job createImportJob(String filePath, String tableNam) {
        CsvItemReader reader = new CsvItemReader(filePath);
        OracleDbWriter writer = new OracleDbWriter(dbManager, tableNam);
        return createImportJob(reader, writer);
    }

    public Job createExportJob(String filePath, String tableNam) {
        OracleItemReader reader = new OracleItemReader(dbManager, tableNam);
        CsvItemWriter writer = new CsvItemWriter(filePath);
        return createExportJob(reader, writer);
    }

    public void launchJob(Job job) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        batchJobManager.getJobLauncher().run(job,
                new JobParametersBuilder().addLong("unique", System.currentTimeMillis()).toJobParameters());
    }

    private Job createImportJob(CsvItemReader reader, OracleDbWriter writer) {
        return new JobBuilder("import_job", batchJobManager.getJobRepository())
                .start(stepImport(reader, writer))
                .build();
    }

    private Job createExportJob(OracleItemReader reader, CsvItemWriter writer) {
        return new JobBuilder("export_job", batchJobManager.getJobRepository())
                .start(stepExport(reader, writer))
                .build();
    }

    /**
     * @param reader
     * @param writer
     * @return
     */
    private Step stepImport(CsvItemReader reader, OracleDbWriter writer) {
        return new StepBuilder("import_step", batchJobManager.getJobRepository())
                .<String[], String[]>chunk(1000, batchJobManager.getTransactionManager())
                .writer(writer)
                .reader(reader)
                .processor(null)
                .build();
    }

    private Step stepExport(OracleItemReader reader, CsvItemWriter writer) {
        return new StepBuilder("export_step", batchJobManager.getJobRepository())
                .<List<String>, List<String>>chunk(1000, batchJobManager.getTransactionManager())
                .writer(writer)
                .reader(reader)
                .processor(null)
                .build();
    }
}
