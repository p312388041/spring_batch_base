package com.chong.batch.base.writer;

import org.junit.jupiter.api.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.chong.batch.base.Application;
import com.chong.batch.base.db.DbManager;

@SpringBootTest(classes = Application.class)
@SpringBatchTest
public class OracleDbWriterTest {
    @Autowired
    private DbManager dbManager;

    @Test
    void testStudentJob() throws Exception {
        OracleDbWriter writer = new OracleDbWriter(dbManager, "student");
    }
}
