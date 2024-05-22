package com.chong.batch.base.job;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.chong.batch.base.db.DbManager;
import com.chong.batch.base.reader.CsvItemReader;
import com.chong.batch.base.writer.OracleDbWriter;

@Configuration
public class JobManager {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private DbManager manager;

    public Job createJob(String filePath, String tableNam) {
        CsvItemReader reader = new CsvItemReader(filePath);
        OracleDbWriter writer = new OracleDbWriter(manager, tableNam);
        return createJob(reader, writer);
    }

    private Job createJob(CsvItemReader reader, OracleDbWriter writer) {
        return new JobBuilder("job", jobRepository)
                .start(step(reader, writer))
                .build();
    }

    /**
     * @param reader
     * @param writer
     * @return
     */
    private Step step(CsvItemReader reader, OracleDbWriter writer) {
        return new StepBuilder("step", jobRepository)
                .<String[], String[]>chunk(0, transactionManager)
                .writer(writer)
                .reader(reader)
                .processor(null)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(3)
                .build();
    }

}
