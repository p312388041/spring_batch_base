package com.chong.batch.base.writer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chong.batch.base.db.DbManager;
import com.chong.study.StudyApplication;

@SpringBatchTest 
@SpringBootTest(classes = StudyApplication.class)
public class OracleDbWriterTest {
    @Autowired
    private DbManager dbManager;

    @Test
    void testStudentJob() throws Exception {
        OracleDbWriter writer = new OracleDbWriter(dbManager, "student".toUpperCase());
    }
}
