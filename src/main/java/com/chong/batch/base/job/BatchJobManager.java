package com.chong.batch.base.job;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchJobManager {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobLauncher jobLauncher;

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }
}
